package br.com.dbaonline.uintegrator.api.controller;

import br.com.dbaonline.uintegrator.api.entity.dto.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public final class AuthenticationContext {

    @SuppressWarnings("unchecked")
    public final User currentUser() {
        return (User) ((Optional<User>) getAuthentication().getPrincipal())
                .orElse(null);
    }

    public final Authentication getAuthentication() {
        return getContext().getAuthentication();
    }

    public final SecurityContext getContext() {
        return SecurityContextHolder.getContext();
    }
}
