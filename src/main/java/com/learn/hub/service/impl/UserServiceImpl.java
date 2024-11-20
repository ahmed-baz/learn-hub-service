package com.learn.hub.service.impl;

import com.learn.hub.entity.UserEntity;
import com.learn.hub.enums.UserRoleEnum;
import com.learn.hub.repo.UserRepository;
import com.learn.hub.service.UserService;
import com.learn.hub.vo.KeycloakUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    private KeycloakUser getKeycloakUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        return KeycloakUser.builder()
                .firstName(jwt.getClaim("given_name").toString())
                .lastName(jwt.getClaim("family_name").toString())
                .userName(jwt.getClaim("email").toString())
                .roles(getRoles())
                .build();
    }

    private static List<UserRoleEnum> getRoles() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .filter(authority -> authority.getAuthority().startsWith("ROLE_")).map(
                        authority -> UserRoleEnum.valueOf(authority.getAuthority().substring("ROLE_".length()))
                )
                .toList();
    }
}
