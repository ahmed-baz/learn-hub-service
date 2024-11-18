package com.learn.hub.service.impl;

import com.learn.hub.entity.UserEntity;
import com.learn.hub.repo.UserRepository;
import com.learn.hub.service.UserService;
import com.learn.hub.vo.KeycloakUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;

    public UserEntity getUser() {
        Optional<UserEntity> userEntity = userRepo.findByUserName(getUserName());
        if (userEntity.isPresent()) {
            return userEntity.get();
        }
        KeycloakUser user = getKeycloakUser();
        UserEntity newUser = new UserEntity();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setUserName(user.getUserName());
        return userRepo.save(newUser);
    }

    private String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    private KeycloakUser getKeycloakUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        return KeycloakUser.builder()
                .firstName(jwt.getClaim("given_name").toString())
                .lastName(jwt.getClaim("family_name").toString())
                .email(jwt.getClaim("email").toString())
                .userName(jwt.getClaim("preferred_username").toString())
                .build();
    }
}
