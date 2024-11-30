package com.learn.hub.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "course_rates")
public class CourseRateEntity extends EntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_rates_seq")
    @SequenceGenerator(name = "course_rates_seq", sequenceName = "course_rates_seq", allocationSize = 1)
    private Long id;
    @Column(nullable = false)
    private int rate;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseEntity course;
}
