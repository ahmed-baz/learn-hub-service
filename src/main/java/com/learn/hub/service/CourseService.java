package com.learn.hub.service;

import com.learn.hub.payload.PageResponse;
import com.learn.hub.vo.Course;
import com.learn.hub.vo.FilterCourseRequest;
import com.learn.hub.vo.RegisterCourse;
import org.springframework.http.ResponseEntity;

public interface CourseService {

    PageResponse<Course> getCoursePage(FilterCourseRequest request);

    Course registerCourse(RegisterCourse registerCourse);

    Long unregisterCourse(RegisterCourse registerCourse);

    Course addCourse(Course course);

    Course updateCourse(Long id, Course course);

    Course findCourse(Long id);

    void deleteCourse(Long id);

    ResponseEntity<byte[]> exportCourseSchedule();
}
