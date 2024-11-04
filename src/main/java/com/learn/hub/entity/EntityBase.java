package com.learn.hub.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public abstract class EntityBase implements Serializable {

    @Column(
            nullable = false,
            updatable = false,
            name = "created_at"
    )
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "last_modified_at")
    private LocalDateTime lastModifiedAt;
    @CreatedBy
    @Column(name = "created_by")
    private Long createdBy;
    @LastModifiedBy
    @Column(name = "last_modified_by")
    private Long lastModifiedBy;

}
