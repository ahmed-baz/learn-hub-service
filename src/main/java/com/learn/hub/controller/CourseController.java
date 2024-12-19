package com.learn.hub.controller;


import com.learn.hub.payload.AppResponse;
import com.learn.hub.payload.PageResponse;
import com.learn.hub.service.CourseService;
import com.learn.hub.service.FileService;
import com.learn.hub.vo.Course;
import com.learn.hub.vo.FilterCourseRequest;
import com.learn.hub.vo.ImageResponse;
import com.learn.hub.vo.RegisterCourse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
@Tag(name = "Courses",
        description = "manage courses with operations like registering, unregistering,get All, creating, updating, and deleting")
public class CourseController {

    private final CourseService courseService;
    private final FileService fileService;
    private final Environment environment;

    @Operation(
            summary = "Get All Courses",
            responses = {
                    @ApiResponse(responseCode = "200", description = "AppResponse of type Course",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Course.class)))
            }
    )
    @PostMapping("/filter")
    public AppResponse<PageResponse<Course>> getCoursePage(@Valid @RequestBody FilterCourseRequest request) {
        AppResponse<PageResponse<Course>> response = new AppResponse<>(courseService.getCoursePage(request));
        updateAppResponse(response);
        return response;
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
        AppResponse<Course> response = new AppResponse<>(courseService.findCourse(id));
        updateAppResponse(response);
        return response;
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
    public AppResponse<Course> registerCourse(@Valid @RequestBody RegisterCourse request) {
        AppResponse<Course> response = new AppResponse<>(courseService.registerCourse(request));
        updateAppResponse(response);
        return response;
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
    public AppResponse<Long> unregisterCourse(@Valid @RequestBody RegisterCourse request) {
        AppResponse<Long> response = new AppResponse<>(courseService.unregisterCourse(request));
        updateAppResponse(response);
        return response;
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
    public AppResponse<Course> createCourse(@Valid @RequestBody Course course) {
        AppResponse<Course> response = new AppResponse<>(courseService.addCourse(course), HttpStatus.CREATED);
        updateAppResponse(response);
        return response;
    }

    @PostMapping("/{id}/upload-cover-image")
    public AppResponse<Void> uploadCourseCoverImage(@RequestParam("image") MultipartFile file, @PathVariable("id") Long courseId) {
        fileService.uploadCourseCoverImage(file, courseId);
        AppResponse<Void> response = new AppResponse<>(HttpStatus.CREATED);
        updateAppResponse(response);
        return response;
    }

    @GetMapping("/cover-image/{id}")
    public ResponseEntity<?> getImageById(@PathVariable Long id) {
        ImageResponse image = fileService.getImageById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getName() + "\"")
                .contentType(MediaType.valueOf(image.getType()))
                .body(image.getData());
    }

    @GetMapping("/{id}/cover")
    public ResponseEntity<?> getImageByCourseId(@PathVariable Long id) {
        ImageResponse image = fileService.getImageByCourseId(id);
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getName() + "\"")
                .contentType(MediaType.valueOf(image.getType()))
                .body(image.getData());
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
    public AppResponse<Course> updateCourse(@PathVariable Long id, @Valid @RequestBody Course course) {
        AppResponse<Course> response = new AppResponse<>(courseService.updateCourse(id, course));
        updateAppResponse(response);
        return response;
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
    public AppResponse<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        AppResponse<Void> response = new AppResponse<>(HttpStatus.NO_CONTENT);
        updateAppResponse(response);
        return response;
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
    public ResponseEntity<byte[]> exportCourseSchedule() {
        return courseService.exportCourseSchedule();
    }

    private void updateAppResponse(AppResponse<?> response) {
        response.getMetaData().put("port", environment.getProperty("local.server.port"));
    }
}
