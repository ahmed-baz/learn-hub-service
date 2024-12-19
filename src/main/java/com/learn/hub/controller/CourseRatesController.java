package com.learn.hub.controller;


import com.learn.hub.payload.AppResponse;
import com.learn.hub.service.CourseRateService;
import com.learn.hub.vo.CourseRateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/course/rates")
@RequiredArgsConstructor
public class CourseRatesController {

    private final CourseRateService courseRateService;

    @PostMapping("/{id}")
    public AppResponse<Void> createRate(@PathVariable Long id, @Valid @RequestBody CourseRateRequest request) {
        courseRateService.createRate(id, request);
        return new AppResponse<>();
    }

    @GetMapping("/{id}")
    public AppResponse<Double> getCourseRate(@PathVariable Long id) {
        return new AppResponse<>(courseRateService.getCourseRate(id));
    }

}
