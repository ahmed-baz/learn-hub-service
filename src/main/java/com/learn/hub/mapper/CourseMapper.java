package com.learn.hub.mapper;

import com.learn.hub.entity.CourseEntity;
import com.learn.hub.vo.Course;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface CourseMapper {

    Course toCourse(CourseEntity course);

    CourseEntity toCourseEntity(Course course);

    List<Course> toCourse(List<CourseEntity> entities);
}
