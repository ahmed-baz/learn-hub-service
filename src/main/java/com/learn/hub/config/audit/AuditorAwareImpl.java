package com.learn.hub.config.audit;

import com.learn.hub.security.vo.AppUserDetails;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        long id = 0L;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof AppUserDetails principal) {
            id = principal.getId() != null ? principal.getId() : 0;
        }
        return Optional.of(id);
    }
}
