package com.learn.hub.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.sql.Timestamp;

@MappedSuperclass
@JsonIgnoreProperties(
        value = {"createdBy", "lastModifiedBy", "deletedBy"}
)
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
    @CreationTimestamp
    private Timestamp createdAt;
    @UpdateTimestamp
    @Column(name = "last_modified_at")
    private Timestamp lastModifiedAt;
    @CreatedBy
    @Column(name = "created_by")
    private Long createdBy;
    @LastModifiedBy
    @Column(name = "last_modified_by")
    private Long lastModifiedBy;
    @Column(name = "deleted_at")
    private Timestamp deletedAt;
    @Column(name = "deleted_by")
    private Long deletedBy;

}
