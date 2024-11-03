package com.learn.hub.vo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class BaseVO {

    private Timestamp createdAt;
    private Timestamp lastModifiedAt;
    private Long createdBy;
    private Long lastModifiedBy;
}
