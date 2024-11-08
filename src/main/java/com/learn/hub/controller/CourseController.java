package com.learn.hub.controller;


import com.learn.hub.payload.AppResponse;
import com.learn.hub.service.CourseService;
import com.learn.hub.vo.Course;
import com.learn.hub.vo.RegisterCourse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(
            summary = "Get All Courses",
            responses = {
                    @ApiResponse(responseCode = "200", description = "AppResponse of type Course",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Course.class)))
            }
    )
    @GetMapping
    public AppResponse<List<Course>> getCourses() {
        return new AppResponse<>(courseService.getAllCourses());
    }

    @Operation(
            summary = "Get Course By Id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "AppResponse of type Course",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Course.class)))
            }
    )
    @GetMapping("/{id}")
    public AppResponse<Course> findCourse(@PathVariable Long id) {
        return new AppResponse<>(courseService.findCourse(id));
    }

    @Operation(
            summary = "Register a course",
            responses = {
                    @ApiResponse(responseCode = "200", description = "AppResponse of type Course",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Course.class)))
            }
    )
    @PostMapping("/register")
    @PreAuthorize("hasAuthority('STUDENT')")
    public AppResponse<Course> registerCourse(@Valid @RequestBody RegisterCourse request) {
        return new AppResponse<>(courseService.registerCourse(request));
    }

    @Operation(
            summary = "Unregister a course",
            responses = {
                    @ApiResponse(responseCode = "200", description = "AppResponse of type Long",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AppResponse.class)))
            }
    )
    @PostMapping("/unregister")
    @PreAuthorize("hasAuthority('STUDENT')")
    public AppResponse<Long> unregisterCourse(@Valid @RequestBody RegisterCourse request) {
        return new AppResponse<>(courseService.unregisterCourse(request));
    }

    @Operation(
            summary = "Create a new course",
            responses = {
                    @ApiResponse(responseCode = "201", description = "AppResponse of type Course",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Course.class)))
            }
    )
    @PostMapping
    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    public AppResponse<Course> createCourse(@Valid @RequestBody Course course) {
        return new AppResponse<>(courseService.addCourse(course), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update a course",
            responses = {
                    @ApiResponse(responseCode = "200", description = "AppResponse of type Course",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Course.class)))
            }
    )
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    public AppResponse<Course> updateCourse(@PathVariable Long id, @Valid @RequestBody Course course) {
        return new AppResponse<>(courseService.updateCourse(id, course));
    }

    @Operation(
            summary = "Delete a course",
            responses = {
                    @ApiResponse(responseCode = "204", description = "AppResponse of type Void",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AppResponse.class)))
            }
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    public AppResponse<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return new AppResponse<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Get a schedule course report",
            responses = {
                    @ApiResponse(responseCode = "200", description = "ResponseEntity of type byte[]",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AppResponse.class)))
            }
    )
    @GetMapping("/schedule")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<byte[]> exportCourseSchedule() {
        return courseService.exportCourseSchedule();
    }

}
