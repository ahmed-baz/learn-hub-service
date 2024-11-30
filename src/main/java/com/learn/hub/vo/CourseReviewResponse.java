package com.learn.hub.vo;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CourseReviewResponse {

    private String review;
    private String user;

}
