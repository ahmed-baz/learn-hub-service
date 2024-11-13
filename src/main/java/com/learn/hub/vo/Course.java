package com.learn.hub.vo;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

import static com.learn.hub.handler.ErrorCode.*;


@Data
public class Course extends BaseVO {
    private Long id;
    @NotNull(message = COURSE_TITLE_REQUIRED)
    @NotEmpty(message = COURSE_TITLE_REQUIRED)
    @Size(min = 3, max = 200, message = COURSE_TITLE_LENGTH_EXCEED)
    private String title;
    @NotNull(message = COURSE_DESCRIPTION_REQUIRED)
    @NotEmpty(message = COURSE_DESCRIPTION_REQUIRED)
    @Size(min = 3, max = 900, message = COURSE_DESCRIPTION_LENGTH_EXCEED)
    private String description;
    @NotNull(message = COURSE_HOURS_REQUIRED)
    @Positive(message = COURSE_HOURS_INVALID)
    private int numberOfHours;
    @NotNull(message = COURSE_START_DATE_REQUIRED)
    @Future(message = COURSE_START_DATE_INVALID)
    private LocalDate startAt;
    private User instructor;
    private ImageResponse coverImage;
}
