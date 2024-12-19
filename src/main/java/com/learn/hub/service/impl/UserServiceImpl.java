package com.learn.hub.service.impl;

import com.learn.hub.entity.UserEntity;
import com.learn.hub.interceptor.UserContext;
import com.learn.hub.repo.UserRepository;
import com.learn.hub.service.KeycloakAdminClientService;
import com.learn.hub.service.UserService;
import com.learn.hub.vo.KeycloakUser;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final KeycloakAdminClientService keycloakService;

    public UserEntity getUser() {
        Optional<UserEntity> userEntity = userRepo.findByEmail(getUserName());
        if (userEntity.isPresent()) {
            return userEntity.get();
        }
        KeycloakUser user = getKeycloakUser();
        UserEntity newUser = new UserEntity();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getUserName());
        newUser.setRoles(user.getRoles());
        return userRepo.save(newUser);
    }

    private String getUserName() {
        return UserContext.getEmail();
    }

    private KeycloakUser getKeycloakUser() {
        UserRepresentation user = keycloakService.getUserByEmail(getUserName());
        return KeycloakUser.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userName(user.getUsername())
                .build();
    }

}
