package com.management.system.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "courses")
public class CourseEntity extends EntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "courses_seq")
    @SequenceGenerator(name = "courses_seq", sequenceName = "courses_seq", allocationSize = 1)
    private Long id;
    @Column(nullable = false)
    private String title;
    private String description;
    @Column(nullable = false)
    private int numberOfHours;
    private LocalDate startAt;
    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private UserEntity instructor;
}
