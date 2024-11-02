package com.learn.hub.config.audit;

import com.learn.hub.security.vo.AppUserDetails;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        AppUserDetails principal = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = principal.getId() != null ? principal.getId() : 0;
        return Optional.of(id);
    }
}
