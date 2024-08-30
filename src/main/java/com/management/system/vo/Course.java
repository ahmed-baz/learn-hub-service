package com.management.system.vo;

import lombok.Data;

import java.sql.Timestamp;


@Data
public class Course {
    private Long id;
    private String title;
    private String description;
    private int numberOfHours;
    private Timestamp startAt;
    private User instructor;
}
