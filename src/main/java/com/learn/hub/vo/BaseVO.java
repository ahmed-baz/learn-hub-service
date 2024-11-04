package com.learn.hub.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseVO {

    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private Long createdBy;
    private Long lastModifiedBy;
}
