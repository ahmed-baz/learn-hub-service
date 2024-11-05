package com.learn.hub.security.service;

import com.learn.hub.security.vo.LoginRequest;
import com.learn.hub.security.vo.LoginResponse;
import com.learn.hub.security.vo.UserRegisterRequest;
import com.learn.hub.security.vo.UserRegisterResponse;

public interface AuthService {

    LoginResponse login(LoginRequest requestVO);

    UserRegisterResponse register(UserRegisterRequest userRequest);

    void activateAccount(String code, String email);

}
