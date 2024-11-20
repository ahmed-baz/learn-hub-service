package com.learn.hub.repo;

import com.learn.hub.entity.CourseEntity;
import com.learn.hub.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends BaseRepo<CourseEntity> {

    List<CourseEntity> findByInstructor(UserEntity user);
}
