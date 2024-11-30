package com.learn.hub.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private Date startAt;
    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private UserEntity instructor;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private CourseImageEntity courseImage;
    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseRateEntity> courseRates = new ArrayList<>();
    @Transient
    private Double rate;
}
