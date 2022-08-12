package br.com.dbaonline.uintegrator.api.controller;

import br.com.dbaonline.uintegrator.api.entity.dto.User;
import br.com.dbaonline.uintegrator.api.entity.transients.CompanyRole;
import br.com.dbaonline.uintegrator.api.service.UserCompanyService;
import lombok.NonNull;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public final class AuthenticationContext {

    @Autowired
    private UserCompanyService userCompanyService;

    @SuppressWarnings("unchecked")
    public User currentUser() {
        return ((Optional<User>) getAuthentication().getPrincipal())
                .orElse(null);
    }

    public Authentication getAuthentication() {
        return getContext().getAuthentication();
    }

    public SecurityContext getContext() {
        return SecurityContextHolder.getContext();
    }

    public boolean currentUserIsNotMemberInCompany(@NonNull Long companyId) {
        val currentUser = currentUser();

        if (currentUser == null) {
            return true;
        }

        return userCompanyService.userBelongsCompany(companyId, currentUser.getId()).isEmpty();
    }

    public boolean currentUserNotHaveAdminRoleInCompany(@NonNull Long companyId) {

        val currentUser = currentUser();

        if (currentUser == null) {
            return true;
        }

        return userCompanyService.userBelongsCompany(companyId, currentUser.getId()).filter(userCompany -> CompanyRole.ADMIN.equals(userCompany.getRole())).isEmpty();
    }
}
