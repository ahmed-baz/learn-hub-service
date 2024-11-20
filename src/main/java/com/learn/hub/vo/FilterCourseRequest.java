package com.learn.hub.vo;

import lombok.Data;

import java.util.Date;


@Data
public class FilterCourseRequest extends FilterRequest {

    private Date from;
    private Date to;
}
