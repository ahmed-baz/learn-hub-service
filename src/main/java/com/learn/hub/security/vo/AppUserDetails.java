package com.learn.hub.security.vo;


import com.learn.hub.entity.UserEntity;
import com.learn.hub.enums.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDetails implements UserDetails, Principal {

    private Long id;
    private String email;
    private String password;
    private UserRoleEnum role;
    private boolean accountLocked;
    private boolean enabled;

    public AppUserDetails(UserEntity user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.accountLocked = user.isAccountLocked();
        this.enabled = user.isEnabled();
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

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String getName() {
        return email;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }
}
