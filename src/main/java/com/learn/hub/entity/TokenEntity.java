package com.learn.hub.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tokens")
public class TokenEntity extends EntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tokens_seq")
    @SequenceGenerator(name = "tokens_seq", sequenceName = "tokens_seq", allocationSize = 1)
    private Long id;
    @Column(nullable = false)
    private String code;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    private LocalDateTime expiresAt;
    private LocalDateTime validatedAt;


}
