package com.learn.hub.security.service;

import com.learn.hub.security.vo.*;

public interface AuthService {

    LoginResponse login(LoginRequest requestVO);

    UserRegisterResponse register(UserRegisterRequest userRequest);

    void activateAccount(ActivateAccountRequest request);

}
