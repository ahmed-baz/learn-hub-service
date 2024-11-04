package com.learn.hub.service.impl;

import com.learn.hub.entity.UserEntity;
import com.learn.hub.exception.UserNotFoundException;
import com.learn.hub.repo.UserRepository;
import com.learn.hub.security.vo.AppUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepo;

    @Override
    public AppUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepo.findByEmail(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        return new AppUserDetails(user);
    }
}
