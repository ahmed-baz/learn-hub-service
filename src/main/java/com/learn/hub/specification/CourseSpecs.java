package com.learn.hub.specification;

import com.learn.hub.entity.CourseEntity;
import com.learn.hub.vo.FilterCourseRequest;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

@UtilityClass
public class CourseSpecs {

    public Specification<CourseEntity> createCourseSpec(FilterCourseRequest request) {
        Specification<CourseEntity> propertySpec = LearHubSpecs.propertySpec(request.getKeyword(), "title", "description");
        return Specification.where(propertySpec)
                .and(startAtSpec(request))
                .and(LearHubSpecs.distinct());
    }

    private static Specification<CourseEntity> startAtSpec(FilterCourseRequest request) {
        return (root, query, criteriaBuilder) -> {
            if (request.getFrom() != null && request.getTo() != null) {
                return criteriaBuilder.between(root.get("startAt"), request.getFrom(), request.getTo());
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }
}
