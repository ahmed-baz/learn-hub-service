package com.learn.hub.vo;


import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import static com.learn.hub.handler.ErrorCode.COURSE_RATE_REQUIRED;
import static com.learn.hub.handler.ErrorCode.INVALID_RATE;

@Getter
@Setter
public class CourseRateRequest {

    @NotNull(message = COURSE_RATE_REQUIRED)
    @DecimalMin(value = "1", message = INVALID_RATE)
    @DecimalMax(value = "5", message = INVALID_RATE)
    private Double rate;
}
