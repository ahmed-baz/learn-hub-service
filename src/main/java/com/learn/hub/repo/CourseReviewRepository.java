package com.learn.hub.repo;

import com.learn.hub.entity.CourseReviewEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseReviewRepository extends BaseRepo<CourseReviewEntity> {

    Optional<CourseReviewEntity> findByCourseIdAndCreatedBy(Long courseId, String email);

    List<CourseReviewEntity> findByCourseId(Long courseId);
}
