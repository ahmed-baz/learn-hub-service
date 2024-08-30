package com.management.system.service.impl;

import com.management.system.entity.CourseEntity;
import com.management.system.entity.StudentCourseEntity;
import com.management.system.entity.UserEntity;
import com.management.system.exception.CourseNotFoundException;
import com.management.system.mapper.CourseMapper;
import com.management.system.repo.CourseRepository;
import com.management.system.repo.StudentCourseRepository;
import com.management.system.repo.UserRepository;
import com.management.system.security.vo.AppUserDetails;
import com.management.system.service.CacheManagerService;
import com.management.system.service.CourseService;
import com.management.system.vo.Course;
import com.management.system.vo.RegisterCourse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepo;
    private final StudentCourseRepository studentCourseRepo;
    private final UserRepository userRepo;
    private final CourseMapper courseMapper;
    private final CacheManagerService cacheManagerService;

    @Override
    @Cacheable(value = "courses")
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
            throw new CourseNotFoundException(courseId);
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
            throw new CourseNotFoundException(courseId);
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
        cacheManagerService.clearCacheByName("courses");
        return courseMapper.toCourse(courseEntity);
    }

    @Override
    public Course updateCourse(Long id, Course course) {
        AppUserDetails user = getUser();
        Optional<CourseEntity> oldCourseEntity = courseRepo.findById(id);
        if (!oldCourseEntity.isPresent() || user.getId() != oldCourseEntity.get().getInstructor().getId()) {
            throw new CourseNotFoundException(id);
        }
        CourseEntity newCourseEntity = courseMapper.toCourseEntity(course);
        newCourseEntity.setId(id);
        newCourseEntity.setInstructor(oldCourseEntity.get().getInstructor());
        courseRepo.save(newCourseEntity);
        cacheManagerService.clearCacheByName("courses");
        return courseMapper.toCourse(newCourseEntity);
    }

    @Override
    public Course findCourse(Long id) {
        return courseRepo.findById(id).map(courseMapper::toCourse).orElseThrow(() -> new CourseNotFoundException(id));
    }

    @Override
    public void deleteCourse(Long id) {
        AppUserDetails user = getUser();
        Optional<CourseEntity> oldCourseEntity = courseRepo.findById(id);
        if (!oldCourseEntity.isPresent() || user.getId() != oldCourseEntity.get().getInstructor().getId()) {
            throw new CourseNotFoundException(id);
        }
        courseRepo.deleteById(id);
        cacheManagerService.clearCacheByName("courses");
    }

    private AppUserDetails getUser() {
        return (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
