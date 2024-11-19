package com.learn.hub.repo;

import com.learn.hub.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepo<UserEntity> {

    Optional<UserEntity> findByEmail(String email);
}
