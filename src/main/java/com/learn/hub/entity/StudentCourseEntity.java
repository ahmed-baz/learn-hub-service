package com.learn.hub.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student_courses")
public class StudentCourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_courses_seq")
    @SequenceGenerator(name = "student_courses_seq", sequenceName = "student_courses_seq", allocationSize = 1)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseEntity course;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private UserEntity student;
}
