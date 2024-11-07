package com.learn.hub.controller;


import com.learn.hub.payload.AppResponse;
import com.learn.hub.service.CourseService;
import com.learn.hub.vo.Course;
import com.learn.hub.vo.RegisterCourse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
@Tag(name = "Courses",
        description = "manage courses with operations like registering, unregistering,get All, creating, updating, and deleting")
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public AppResponse<List<Course>> getCourses() {
        return new AppResponse<>(courseService.getAllCourses());
    }

    @GetMapping("/{id}")
    public AppResponse<Course> findCourse(@PathVariable Long id) {
        return new AppResponse<>(courseService.findCourse(id));
    }

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('STUDENT')")
    public AppResponse<Course> registerCourse(@Valid @RequestBody RegisterCourse request) {
        return new AppResponse<>(courseService.registerCourse(request));
    }

    @PostMapping("/unregister")
    @PreAuthorize("hasAuthority('STUDENT')")
    public AppResponse<Long> unregisterCourse(@Valid @RequestBody RegisterCourse request) {
        return new AppResponse<>(courseService.unregisterCourse(request));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    public AppResponse<Course> createCourse(@Valid @RequestBody Course course) {
        return new AppResponse<>(courseService.addCourse(course));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    public AppResponse<Course> updateCourse(@PathVariable Long id, @Valid @RequestBody Course course) {
        return new AppResponse<>(courseService.updateCourse(id, course));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    public AppResponse<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return new AppResponse<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/schedule")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<byte[]> exportCourseSchedule() {
        return courseService.exportCourseSchedule();
    }

}
