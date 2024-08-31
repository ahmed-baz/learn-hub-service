package com.management.system.controller;


import com.management.system.payload.AppResponse;
import com.management.system.service.CourseService;
import com.management.system.vo.Course;
import com.management.system.vo.RegisterCourse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
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
    public AppResponse<Course> registerCourse(@Valid @RequestBody RegisterCourse request) {
        return new AppResponse<>(courseService.registerCourse(request));
    }

    @PostMapping("/unregister")
    public AppResponse<Long> unregisterCourse(@Valid @RequestBody RegisterCourse request) {
        return new AppResponse<>(courseService.unregisterCourse(request));
    }

    @PostMapping
    public AppResponse<Course> createCourse(@Valid @RequestBody Course course) {
        return new AppResponse<>(courseService.addCourse(course));
    }

    @PutMapping("/{id}")
    public AppResponse<Course> updateCourse(@PathVariable Long id, @Valid @RequestBody Course course) {
        return new AppResponse<>(courseService.updateCourse(id, course));
    }

    @DeleteMapping("/{id}")
    public AppResponse<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return new AppResponse<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/schedule")
    public ResponseEntity<byte[]> exportCourseSchedule() {
        return courseService.exportCourseSchedule();
    }

}
