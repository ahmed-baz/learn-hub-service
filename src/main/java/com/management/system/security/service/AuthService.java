package com.management.system.security.service;

import com.management.system.security.vo.LoginRequest;
import com.management.system.security.vo.LoginResponse;
import com.management.system.vo.UserRequest;

public interface AuthService {

    LoginResponse login(LoginRequest requestVO);

    UserRequest register(UserRequest userRequest);
}
