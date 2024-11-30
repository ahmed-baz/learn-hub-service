package com.learn.hub.service;

import com.learn.hub.vo.CourseRateRequest;

public interface CourseRateService {

    void createRate(Long courseId, CourseRateRequest request);

    double getCourseRate(Long courseId);
}
