package com.management.system.service;

import com.management.system.vo.Course;
import com.management.system.vo.RegisterCourse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CourseService {

    List<Course> getAllCourses();

    Course registerCourse(RegisterCourse registerCourse);

    Long unregisterCourse(RegisterCourse registerCourse);

    Course addCourse(Course course);

    Course updateCourse(Long id, Course course);

    Course findCourse(Long id);

    void deleteCourse(Long id);

    ResponseEntity<byte[]> exportCourseSchedule();
}
