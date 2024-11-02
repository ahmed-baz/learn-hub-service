package com.learn.hub.security.service;

import com.learn.hub.security.vo.LoginRequest;
import com.learn.hub.security.vo.LoginResponse;
import com.learn.hub.vo.UserRequest;

public interface AuthService {

    LoginResponse login(LoginRequest requestVO);

    UserRequest register(UserRequest userRequest);
}
