package com.learn.hub.vo;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import static com.learn.hub.handler.ErrorCode.COURSE_REVIEW_LENGTH_EXCEED;
import static com.learn.hub.handler.ErrorCode.COURSE_REVIEW_REQUIRED;

@Setter
@Getter
public class CourseReviewRequest {

    @NotNull(message = COURSE_REVIEW_REQUIRED)
    @NotEmpty(message = COURSE_REVIEW_REQUIRED)
    @Size(min = 5, max = 255, message = COURSE_REVIEW_LENGTH_EXCEED)
    private String review;

}
