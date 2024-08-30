package com.management.system.mapper;

import com.management.system.entity.CourseEntity;
import com.management.system.vo.Course;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface CourseMapper {

    Course toCourse(CourseEntity course);

    CourseEntity toCourseEntity(Course course);

    List<Course> toCourse(List<CourseEntity> entities);
}
