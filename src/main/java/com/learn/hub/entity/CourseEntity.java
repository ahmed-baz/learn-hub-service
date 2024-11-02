package com.learn.hub.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "courses")
public class CourseEntity extends EntityBase {

    @Column(nullable = false)
    private String title;
    private String description;
    @Column(nullable = false)
    private int numberOfHours;
    private Date startAt;
    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private UserEntity instructor;
}
