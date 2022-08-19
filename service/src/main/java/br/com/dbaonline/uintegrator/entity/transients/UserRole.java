package br.com.dbaonline.uintegrator.entity.transients;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum UserRole implements GrantedAuthority {
    ADMIN(UserRoles.Fields.ADMIN),
    COMMON(UserRoles.Fields.COMMON);

    private final String authority;

    @Override
    public String getAuthority() {
        return "ROLE_" + authority;
    }
}
