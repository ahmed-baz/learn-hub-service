package com.management.system.repo;

import com.management.system.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepo<UserEntity> {

    Optional<UserEntity> findByEmail(String email);
}
