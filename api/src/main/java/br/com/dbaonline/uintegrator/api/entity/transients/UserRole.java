package br.com.dbaonline.uintegrator.api.entity.transients;

import br.com.dbaonline.uintegrator.api.security.UserRoles;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.expression.SecurityExpressionRoot;
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
