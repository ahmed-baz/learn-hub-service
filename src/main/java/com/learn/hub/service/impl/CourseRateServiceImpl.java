package com.learn.hub.service.impl;

import com.learn.hub.entity.CourseEntity;
import com.learn.hub.entity.CourseRateEntity;
import com.learn.hub.exception.LearnHubException;
import com.learn.hub.handler.ErrorCode;
import com.learn.hub.interceptor.UserContext;
import com.learn.hub.repo.CourseRateRepository;
import com.learn.hub.repo.CourseRepository;
import com.learn.hub.service.CourseRateService;
import com.learn.hub.vo.CourseRateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseRateServiceImpl implements CourseRateService {

    private final CourseRateRepository courseRateRepo;
    private final CourseRepository courseRepo;

    @Override
    public void createRate(Long courseId, CourseRateRequest request) {
        Optional<CourseEntity> courseEntity = courseRepo.findById(courseId);
        if (courseEntity.isEmpty()) {
            throw new LearnHubException(ErrorCode.COURSE_NOT_FOUND, HttpStatus.BAD_REQUEST);
        }
        Optional<CourseRateEntity> courseRate = courseRateRepo.findByCourseIdAndCreatedBy(courseId, getUserName());
        if (courseRate.isPresent()) {
            courseRate.get().setRate(request.getRate());
            courseRateRepo.save(courseRate.get());
        } else {
            CourseRateEntity entity = CourseRateEntity.builder()
                    .course(courseEntity.get())
                    .rate(request.getRate())
                    .build();
            courseRateRepo.save(entity);
        }
    }

    @Override
    public double getCourseRate(Long courseId) {
        List<CourseRateEntity> courseRates = courseRateRepo.findByCourseId(courseId);
        if (courseRates.isEmpty()) return 0;
        double sum = courseRates.stream().mapToDouble(CourseRateEntity::getRate).sum();
        return sum / courseRates.size();
    }

    private String getUserName() {
        return UserContext.getEmail();
    }
}
