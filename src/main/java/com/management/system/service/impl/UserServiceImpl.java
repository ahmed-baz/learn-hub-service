package com.management.system.service.impl;

import com.management.system.exception.UserNotFoundException;
import com.management.system.mapper.UserMapper;
import com.management.system.repo.UserRepository;
import com.management.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByEmail(username)
                .map(userMapper::toUserDetails)
                .orElseThrow(() -> new UserNotFoundException(username));
    }
}
