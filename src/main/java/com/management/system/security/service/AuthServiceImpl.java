package com.management.system.security.service;


import com.management.system.exception.InvalidCredentialsException;
import com.management.system.exception.UserNotFoundException;
import com.management.system.security.util.JwtTokenUtil;
import com.management.system.security.vo.AppUserDetails;
import com.management.system.security.vo.LoginRequest;
import com.management.system.security.vo.LoginResponse;
import com.management.system.service.UserService;
import com.management.system.vo.UserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationProvider authenticationProvider;

    @Override
    public LoginResponse login(LoginRequest requestVO) {
        Authentication authentication = authenticateUser(requestVO);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();
        return jwtTokenUtil.prepareLoginResponse(appUserDetails);
    }

    @Override
    public UserRequest register(UserRequest userRequest) {
        return userRequest;
    }

    private Authentication authenticateUser(LoginRequest requestVO) {
        try {
            return authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(requestVO.getEmail(), requestVO.getPassword()));
        } catch (InternalAuthenticationServiceException | UserNotFoundException ex) {
            log.error(ex);
            throw new InvalidCredentialsException("invalid username or password");
        } catch (Exception ex) {
            log.error(ex);
            throw ex;
        }
    }

}

