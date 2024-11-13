package com.learn.hub.repo;

import com.learn.hub.entity.TokenEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends BaseRepo<TokenEntity> {

    Optional<TokenEntity> findByCodeAndUserId(String code, Long id);
}
