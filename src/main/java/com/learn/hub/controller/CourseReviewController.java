package com.learn.hub.controller;


import com.learn.hub.payload.AppResponse;
import com.learn.hub.service.CourseReviewService;
import com.learn.hub.vo.CourseReviewRequest;
import com.learn.hub.vo.CourseReviewResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/course/reviews")
@RequiredArgsConstructor
public class CourseReviewController {

    private final CourseReviewService courseReviewService;

    @PostMapping("/{id}")
    @PreAuthorize("hasAnyRole('STUDENT')")
    public AppResponse<Void> createReview(@PathVariable Long id, @Valid @RequestBody CourseReviewRequest request) {
        courseReviewService.createReview(id, request);
        return new AppResponse<>();
    }

    @GetMapping("/{id}")
    public AppResponse<List<CourseReviewResponse>> getCourseReviews(@PathVariable Long id) {
        return new AppResponse<>(courseReviewService.getCourseReviews(id));
    }

}
