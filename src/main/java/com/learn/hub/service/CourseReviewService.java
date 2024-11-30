package com.learn.hub.service;

import com.learn.hub.vo.CourseReviewRequest;
import com.learn.hub.vo.CourseReviewResponse;

import java.util.List;

public interface CourseReviewService {

    void createReview(Long courseId, CourseReviewRequest request);

    List<CourseReviewResponse> getCourseReviews(Long courseId);
}
