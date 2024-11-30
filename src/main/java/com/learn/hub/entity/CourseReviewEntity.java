package com.learn.hub.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "course_reviews")
public class CourseReviewEntity extends EntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_reviews_seq")
    @SequenceGenerator(name = "course_reviews_seq", sequenceName = "course_reviews_seq", allocationSize = 1)
    private Long id;
    @Column(nullable = false)
    private String review;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseEntity course;
}
