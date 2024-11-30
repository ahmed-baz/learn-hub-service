package com.learn.hub.mapper;

import com.learn.hub.entity.CourseEntity;
import com.learn.hub.entity.CourseRateEntity;
import com.learn.hub.payload.PageResponse;
import com.learn.hub.vo.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(uses = ImageMapper.class, componentModel = "spring")
public interface CourseMapper {

    @Mapping(target = "rate", source = "courseRates", qualifiedByName = "getCourseRate")
    Course toCourse(CourseEntity course);

    @Named("getCourseRate")
    default double getCourseRate(List<CourseRateEntity> courseRates) {
        if (courseRates.isEmpty()) return 0;
        double sum = courseRates.stream().mapToDouble(CourseRateEntity::getRate).sum();
        return sum / courseRates.size();
    }

    CourseEntity toCourseEntity(Course course);

    List<Course> toCourse(List<CourseEntity> entities);

    default PageResponse<Course> toCourse(Page<CourseEntity> page) {
        return PageResponse.<Course>builder()
                .list(toCourse(page.getContent()))
                .isLast(page.isLast())
                .number(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }
}
