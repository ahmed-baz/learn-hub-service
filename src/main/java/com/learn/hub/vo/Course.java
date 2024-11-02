package com.learn.hub.vo;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;


@Data
public class Course {
    private Long id;
    @NotNull(message = "The course title is required")
    @NotEmpty(message = "The course title is required")
    @Size(min = 3, max = 200, message = "The course title length should between 3 and 200")
    private String title;
    @NotNull(message = "The course description is required")
    @NotEmpty(message = "The course description is required")
    @Size(min = 3, max = 900, message = "The course description length should between 3 and 200")
    private String description;
    @Positive(message = "The course number of hours is invalid")
    private int numberOfHours;
    @NotNull(message = "The course start date is invalid")
    @Future(message = "The course start date is invalid")
    private LocalDate startAt;
    private User instructor;
}
