package com.management.system.service;

import com.management.system.vo.UserRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserRequest loadByEmail(String email);
}
