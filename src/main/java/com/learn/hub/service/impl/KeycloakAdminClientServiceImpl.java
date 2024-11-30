package com.learn.hub.service.impl;

import com.learn.hub.enums.UserRoleEnum;
import com.learn.hub.service.KeycloakAdminClientService;
import com.learn.hub.vo.KeycloakUser;
import com.learn.hub.vo.UserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
@RequiredArgsConstructor
@Log4j2
public class KeycloakAdminClientServiceImpl implements KeycloakAdminClientService {

    @Value("${tesla.oauth2.claim.id}")
    private String realmName;
    private final Keycloak keycloak;

    @Override
    public UserRepresentation getUserByEmail(String email) {
        return keycloak.realm(realmName)
                .users()
                .searchByEmail(email, true)
                .get(0);
    }

    @Override
    public List<UserRepresentation> getUsersByRole(UserRoleEnum role) {
        return keycloak.realm(realmName)
                .roles()
                .get(role.name())
                .getUserMembers();
    }

    @Override
    public List<KeycloakUser> searchUsers(String keyword, int page, int size) {
        List<UserRepresentation> users = keycloak.realm(realmName)
                .users()
                .search(keyword, page, size);

        return users.stream()
                .map(
                        user -> KeycloakUser.builder()
                                .id(user.getId())
                                .firstName(user.getFirstName())
                                .lastName(user.getLastName())
                                .email(user.getEmail())
                                .userName(user.getUsername())
                                .createdAt(new Date(user.getCreatedTimestamp()))
                                .build())
                .toList();

    }

    @Override
    public void createNewUser(UserRequest user) {
        createUser(user, user.getRole());
    }

    private void createUser(UserRequest user, UserRoleEnum role) {

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setFirstName(user.getFirstName());
        userRepresentation.setLastName(user.getLastName());
        userRepresentation.setUsername(user.getEmail());
        userRepresentation.setEmail(user.getEmail());

        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(true);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType("password");
        credential.setTemporary(false);
        credential.setValue(user.getPassword());
        userRepresentation.setCredentials(List.of(credential));

        keycloak.realm(realmName)
                .users()
                .create(userRepresentation);
    }
}
