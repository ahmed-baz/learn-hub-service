package com.learn.hub.service;

import com.learn.hub.enums.UserRoleEnum;
import com.learn.hub.vo.KeycloakUser;
import com.learn.hub.vo.UserRequest;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public interface KeycloakAdminClientService {

    UserRepresentation getUserByEmail(String email);

    List<UserRepresentation> getUsersByRole(UserRoleEnum role);

    List<KeycloakUser> searchUsers(String keyword, int page, int size);

    void createNewUser(UserRequest user);
}
