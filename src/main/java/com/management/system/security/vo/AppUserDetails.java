package com.management.system.security.vo;


import com.management.system.enums.UserRoleEnum;
import com.management.system.vo.UserRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDetails implements UserDetails {

    private Long id;
    private String email;
    private String password;
    private UserRoleEnum role;

    public AppUserDetails(UserRequest userRequest) {
        this.id = userRequest.getId();
        this.email = userRequest.getEmail();
        this.password = userRequest.getPassword();
        this.role = userRequest.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
