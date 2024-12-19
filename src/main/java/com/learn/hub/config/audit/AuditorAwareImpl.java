package com.learn.hub.config.audit;

import com.learn.hub.interceptor.UserContext;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(UserContext.getEmail());
    }
}
