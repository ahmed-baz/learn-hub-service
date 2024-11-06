package com.learn.hub.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import static com.learn.hub.handler.ErrorCode.COURSE_ID_REQUIRED;


@Data
public class RegisterCourse {

    @NotNull(message = COURSE_ID_REQUIRED)
    private Long id;
}
