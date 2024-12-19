package com.learn.hub.controller;


import com.learn.hub.enums.UserRoleEnum;
import com.learn.hub.payload.AppResponse;
import com.learn.hub.service.KeycloakAdminClientService;
import com.learn.hub.vo.KeycloakUser;
import com.learn.hub.vo.UserRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UsersController {

    private final KeycloakAdminClientService keycloakAdminClientService;

    @GetMapping("/search")
    public AppResponse<List<KeycloakUser>> searchUsers(@RequestParam String keyword, @RequestParam int page, @RequestParam int size) {
        return new AppResponse<>(keycloakAdminClientService.searchUsers(keyword, page, size));
    }

    @GetMapping("/find")
    public AppResponse<UserRepresentation> getUserByEmail(@RequestParam String email) {
        return new AppResponse<>(keycloakAdminClientService.getUserByEmail(email));
    }

    @PostMapping
    public AppResponse<Void> createNewUser(@Valid @RequestBody UserRequest request) {
        keycloakAdminClientService.createNewUser(request);
        return new AppResponse<>(HttpStatus.CREATED);
    }

    @GetMapping("/filter")
    public AppResponse<List<UserRepresentation>> getUsersByRole(@RequestParam UserRoleEnum role) {
        return new AppResponse<>(keycloakAdminClientService.getUsersByRole(role));
    }


}
