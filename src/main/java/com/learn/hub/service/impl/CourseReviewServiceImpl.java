package com.learn.hub.service.impl;

import com.learn.hub.entity.CourseEntity;
import com.learn.hub.entity.CourseReviewEntity;
import com.learn.hub.exception.LearnHubException;
import com.learn.hub.handler.ErrorCode;
import com.learn.hub.interceptor.UserContext;
import com.learn.hub.repo.CourseRepository;
import com.learn.hub.repo.CourseReviewRepository;
import com.learn.hub.service.CourseReviewService;
import com.learn.hub.vo.CourseReviewRequest;
import com.learn.hub.vo.CourseReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CourseReviewServiceImpl implements CourseReviewService {

    private final CourseReviewRepository reviewRepo;
    private final CourseRepository courseRepo;

    @Override
    public void createReview(Long courseId, CourseReviewRequest request) {
        Optional<CourseEntity> courseEntity = courseRepo.findById(courseId);
        if (courseEntity.isEmpty()) {
            throw new LearnHubException(ErrorCode.COURSE_NOT_FOUND, HttpStatus.BAD_REQUEST);
        }
        Optional<CourseReviewEntity> courseReviewEntity = reviewRepo.findByCourseIdAndCreatedBy(courseId, getUserName());
        if (courseReviewEntity.isPresent()) {
            courseReviewEntity.get().setReview(request.getReview());
            reviewRepo.save(courseReviewEntity.get());
        } else {
            CourseReviewEntity courseReview = CourseReviewEntity.builder()
                    .review(request.getReview())
                    .course(courseEntity.get())
                    .build();
            reviewRepo.save(courseReview);
        }
    }

    @Override
    public List<CourseReviewResponse> getCourseReviews(Long courseId) {
        return reviewRepo.findByCourseId(courseId)
                .stream()
                .map(
                        entity -> CourseReviewResponse.builder()
                                .review(entity.getReview())
                                .user(entity.getCreatedBy())
                                .build())
                .toList();
    }

    private String getUserName() {
        return UserContext.getEmail();
    }
}
