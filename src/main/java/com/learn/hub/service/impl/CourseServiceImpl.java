package com.learn.hub.service.impl;

import com.learn.hub.entity.CourseEntity;
import com.learn.hub.entity.CourseImageEntity;
import com.learn.hub.entity.StudentCourseEntity;
import com.learn.hub.entity.UserEntity;
import com.learn.hub.exception.LearnHubException;
import com.learn.hub.handler.ErrorCode;
import com.learn.hub.mapper.CourseMapper;
import com.learn.hub.repo.CourseImageRepository;
import com.learn.hub.repo.CourseRepository;
import com.learn.hub.repo.StudentCourseRepository;
import com.learn.hub.repo.UserRepository;
import com.learn.hub.security.vo.AppUserDetails;
import com.learn.hub.service.CourseService;
import com.learn.hub.utils.ImageUtil;
import com.learn.hub.utils.PdfUtils;
import com.learn.hub.vo.Course;
import com.learn.hub.vo.ImageResponse;
import com.learn.hub.vo.RegisterCourse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepo;
    private final CourseImageRepository courseImageRepo;
    private final StudentCourseRepository studentCourseRepo;
    private final UserRepository userRepo;
    private final CourseMapper courseMapper;

    @Override
    public List<Course> getAllCourses() {
        return courseRepo.findAll().stream()
                .map(courseMapper::toCourse)
                .toList();
    }

    @Override
    @Transactional
    public Course registerCourse(RegisterCourse registerCourse) {
        Long courseId = registerCourse.getId();
        Optional<CourseEntity> optionalCourse = courseRepo.findById(courseId);
        if (!optionalCourse.isPresent()) {
            throw new LearnHubException(ErrorCode.COURSE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        AppUserDetails user = getUser();
        UserEntity userEntity = userRepo.findById(user.getId()).get();
        StudentCourseEntity studentCourse = StudentCourseEntity
                .builder()
                .course(optionalCourse.get())
                .student(userEntity)
                .build();
        studentCourseRepo.save(studentCourse);
        return courseMapper.toCourse(optionalCourse.get());
    }

    @Override
    @Transactional
    public Long unregisterCourse(RegisterCourse registerCourse) {
        AppUserDetails user = getUser();
        Long courseId = registerCourse.getId();
        List<StudentCourseEntity> courses = studentCourseRepo.findByStudentId(user.getId());
        Optional<StudentCourseEntity> course = courses.stream().filter(studentCourse -> studentCourse.getCourse().getId().equals(registerCourse.getId())).findFirst();
        if (!course.isPresent()) {
            throw new LearnHubException(ErrorCode.COURSE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        studentCourseRepo.deleteByCourseId(courseId);
        return courseId;
    }

    @Override
    public Course addCourse(Course course) {
        AppUserDetails user = getUser();
        CourseEntity courseEntity = courseMapper.toCourseEntity(course);
        UserEntity userEntity = userRepo.findById(user.getId()).get();
        courseEntity.setInstructor(userEntity);
        courseRepo.save(courseEntity);
        return courseMapper.toCourse(courseEntity);
    }

    @Override
    @Transactional
    public void uploadCourseCoverImage(MultipartFile file, Long courseId) {
        AppUserDetails user = getUser();
        Optional<CourseEntity> optionalCourseEntity = courseRepo.findById(courseId);
        if (!optionalCourseEntity.isPresent() || user.getId() != optionalCourseEntity.get().getInstructor().getId()) {
            throw new LearnHubException(ErrorCode.COURSE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        try {
            CourseEntity course = optionalCourseEntity.get();
            CourseImageEntity courseImage = course.getCourseImage();
            if (null != courseImage) {
                courseImageRepo.delete(courseImage);
            }
            CourseImageEntity newCourseImage = CourseImageEntity.builder()
                    .code(UUID.randomUUID().toString())
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .data(ImageUtil.compressImage(file.getBytes()))
                    .build();
            course.setCourseImage(newCourseImage);
            courseRepo.save(course);
        } catch (Exception ex) {
            log.error("failed to upload course cover image", ex);
            throw new LearnHubException(ErrorCode.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ImageResponse getImageById(Long imageId) {
        CourseImageEntity courseImage = courseImageRepo.findById(imageId).orElseThrow(() -> new LearnHubException(ErrorCode.IMAGE_NOT_FOUND, HttpStatus.NOT_FOUND));
        return ImageResponse.builder()
                .id(imageId)
                .data(ImageUtil.decompressImage(courseImage.getData()))
                .name(courseImage.getName())
                .code(courseImage.getCode())
                .type(courseImage.getType())
                .build();
    }

    @Override
    public ImageResponse getImageByCourseId(Long courseId) {
        Course course = findCourse(courseId);
        ImageResponse coverImage = course.getCoverImage();
        if (null != coverImage) {
            return getImageById(coverImage.getId());
        }
        throw new LearnHubException(ErrorCode.IMAGE_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    @Override
    public Course updateCourse(Long id, Course course) {
        AppUserDetails user = getUser();
        Optional<CourseEntity> oldCourseEntity = courseRepo.findById(id);
        if (!oldCourseEntity.isPresent() || user.getId() != oldCourseEntity.get().getInstructor().getId()) {
            throw new LearnHubException(ErrorCode.COURSE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        CourseEntity newCourseEntity = courseMapper.toCourseEntity(course);
        newCourseEntity.setId(id);
        newCourseEntity.setInstructor(oldCourseEntity.get().getInstructor());
        courseRepo.save(newCourseEntity);
        return courseMapper.toCourse(newCourseEntity);
    }

    @Override
    public Course findCourse(Long id) {
        return courseRepo.findById(id).map(courseMapper::toCourse).orElseThrow(() -> new LearnHubException(ErrorCode.COURSE_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    @Override
    public void deleteCourse(Long id) {
        AppUserDetails user = getUser();
        Optional<CourseEntity> oldCourseEntity = courseRepo.findById(id);
        if (oldCourseEntity.isEmpty() || !user.getId().equals(oldCourseEntity.get().getInstructor().getId())) {
            throw new LearnHubException(ErrorCode.COURSE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        courseRepo.deleteById(id);
    }

    @Override
    public ResponseEntity<byte[]> exportCourseSchedule() {
        ResponseEntity<byte[]> responseEntity = null;
        try {
            AppUserDetails user = getUser();
            List<StudentCourseEntity> studentCourses = studentCourseRepo.findByStudentId(user.getId());
            List<CourseEntity> courses = studentCourses.stream().map(StudentCourseEntity::getCourse).toList();
            ByteArrayOutputStream pdfStream = PdfUtils.generateCoursePdfStream(courses);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Course Schedule.pdf");
            headers.setContentLength(pdfStream.size());
            responseEntity = new ResponseEntity<>(pdfStream.toByteArray(), headers, HttpStatus.OK);
        } catch (Exception ex) {
            LearnHubException hubException = new LearnHubException(ErrorCode.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
            log.error("failed to export the course PDF", hubException);
            throw hubException;
        }
        return responseEntity;
    }

    private AppUserDetails getUser() {
        return (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
