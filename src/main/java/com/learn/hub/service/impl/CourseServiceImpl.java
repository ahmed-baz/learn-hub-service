package com.learn.hub.service.impl;

import com.learn.hub.entity.CourseEntity;
import com.learn.hub.entity.CourseRateEntity;
import com.learn.hub.entity.StudentCourseEntity;
import com.learn.hub.entity.UserEntity;
import com.learn.hub.exception.LearnHubException;
import com.learn.hub.handler.ErrorCode;
import com.learn.hub.mapper.CourseMapper;
import com.learn.hub.payload.PageResponse;
import com.learn.hub.repo.CourseRepository;
import com.learn.hub.repo.StudentCourseRepository;
import com.learn.hub.service.CourseService;
import com.learn.hub.service.UserService;
import com.learn.hub.specification.CourseSpecs;
import com.learn.hub.utils.PdfUtils;
import com.learn.hub.vo.Course;
import com.learn.hub.vo.FilterCourseRequest;
import com.learn.hub.vo.RegisterCourse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepo;
    private final StudentCourseRepository studentCourseRepo;
    private final UserService userService;
    private final CourseMapper courseMapper;

    @Override
    public PageResponse<Course> getCoursePage(FilterCourseRequest request) {
        Specification<CourseEntity> courseSpec = CourseSpecs.createCourseSpec(request);
        PageRequest pageRequest = PageRequest.of(request.getIndex(), request.getSize(), Sort.by(request.getSort(), request.getSortBy()));
        Page<CourseEntity> all = courseRepo.findAll(courseSpec, pageRequest);
        all.getContent().forEach(course -> course.setRate(getCourseRate(course.getCourseRates())));
        return courseMapper.toCourse(all);
    }

    public Double getCourseRate(List<CourseRateEntity> rates) {
        if (rates.isEmpty()) return 0.0;
        Double sum = rates.stream().mapToDouble(CourseRateEntity::getRate).sum();
        return sum / rates.size();
    }

    @Override
    @Transactional
    public Course registerCourse(RegisterCourse registerCourse) {
        Long courseId = registerCourse.getId();
        Optional<CourseEntity> optionalCourse = courseRepo.findById(courseId);
        if (!optionalCourse.isPresent()) {
            throw new LearnHubException(ErrorCode.COURSE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        UserEntity userEntity = userService.getUser();
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
        UserEntity user = userService.getUser();
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
        CourseEntity courseEntity = courseMapper.toCourseEntity(course);
        UserEntity userEntity = userService.getUser();
        courseEntity.setInstructor(userEntity);
        courseRepo.save(courseEntity);
        return courseMapper.toCourse(courseEntity);
    }


    @Override
    public Course updateCourse(Long id, Course course) {
        Optional<CourseEntity> oldCourseEntity = courseRepo.findById(id);
        if (!oldCourseEntity.isPresent() || !getUserName().equals(oldCourseEntity.get().getCreatedBy())) {
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
        Optional<CourseEntity> oldCourseEntity = courseRepo.findById(id);
        if (oldCourseEntity.isEmpty() || !getUserName().equals(oldCourseEntity.get().getCreatedBy())) {
            throw new LearnHubException(ErrorCode.COURSE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        courseRepo.deleteById(id);
    }

    @Override
    public ResponseEntity<byte[]> exportCourseSchedule() {
        ResponseEntity<byte[]> responseEntity = null;
        try {
            UserEntity user = userService.getUser();
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

    @Override
    public List<Course> findByInstructor(UserEntity user) {
        List<CourseEntity> courses = courseRepo.findByInstructor(user);
        return courseMapper.toCourse(courses);
    }

    private String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
