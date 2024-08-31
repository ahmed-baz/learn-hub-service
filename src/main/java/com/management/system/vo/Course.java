package com.management.system.vo;

import lombok.Data;

import java.time.LocalDate;


@Data
public class Course {
    private Long id;
    private String title;
    private String description;
    private int numberOfHours;
    private LocalDate startAt;
    private User instructor;
}
