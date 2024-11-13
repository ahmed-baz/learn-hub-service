package com.learn.hub.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "course_images")
public class CourseImageEntity extends EntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_images_seq")
    @SequenceGenerator(name = "course_images_seq", sequenceName = "course_images_seq", allocationSize = 1)
    private Long id;
    private String name;
    private String code;
    private String path;
    private String type;
    @Lob
    @Column(length = 2000)
    private byte[] data;
    @OneToOne(mappedBy = "courseImage")
    private CourseEntity course;
}
