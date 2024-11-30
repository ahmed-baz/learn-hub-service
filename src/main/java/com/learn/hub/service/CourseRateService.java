package com.learn.hub.service;

import com.learn.hub.vo.CourseRateRequest;

public interface CourseRateService {

    void createRate(Long courseId, CourseRateRequest request);

    int getCourseRate(Long courseId);
}
