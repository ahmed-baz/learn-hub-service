package com.learn.hub.repo;

import com.learn.hub.entity.CourseRateEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRateRepository extends BaseRepo<CourseRateEntity> {

    Optional<CourseRateEntity> findByCourseIdAndCreatedBy(Long courseId, String email);

    List<CourseRateEntity> findByCourseId(Long courseId);
}
