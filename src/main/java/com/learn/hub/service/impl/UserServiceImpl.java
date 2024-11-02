package com.learn.hub.service.impl;

import com.learn.hub.exception.UserNotFoundException;
import com.learn.hub.mapper.UserMapper;
import com.learn.hub.repo.UserRepository;
import com.learn.hub.service.UserService;
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
