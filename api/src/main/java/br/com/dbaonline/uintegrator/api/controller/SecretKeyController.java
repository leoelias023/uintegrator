package br.com.dbaonline.uintegrator.api.controller;

import br.com.dbaonline.uintegrator.api.entity.dto.AuthorizeApplicationKey;
import br.com.dbaonline.uintegrator.api.entity.dto.CreateApplicationKey;
import br.com.dbaonline.uintegrator.api.entity.dto.CreatedApplicationKey;
import br.com.dbaonline.uintegrator.api.entity.dto.SecretKey;
import br.com.dbaonline.uintegrator.api.exception.AccessDeniedException;
import br.com.dbaonline.uintegrator.api.service.SecretKeyService;
import lombok.NonNull;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
public class SecretKeyController {

    @Autowired
    private AuthenticationContext authenticationContext;

    @Autowired
    private SecretKeyService secretKeyService;

    @GetMapping("/secret_key/{id}")
    public SecretKey getSecretKeyDetails(@PathVariable UUID id) {
        return getSecretKeyValidatingAccessOfCurrentUser(id);
    }

    @GetMapping("/company/{companyId}/secret_key/")
    public List<SecretKey> getRegisteredSecretKeysByCompany(@PathVariable Long companyId) {

        if (authenticationContext.currentUserIsNotMemberInCompany(companyId)) {
            throw new AccessDeniedException();
        }

        return secretKeyService.getSecretKeysByCompanyId(companyId);
    }

    @PutMapping("/secret_key/{id}/authorize")
    public void authorizeApplication(@PathVariable UUID id, @RequestBody AuthorizeApplicationKey body) {
        val secretKey = getSecretKeyValidatingAccessOfCurrentUser(id);
        val companyIdTargetApplication = body.getApplicationRegisterCode();

        secretKeyService.authorizeApplication(secretKey.getId(), companyIdTargetApplication);
    }

    @PostMapping("/company/{companyId}/secret_key")
    public CreatedApplicationKey createSecretKey(@PathVariable Long companyId, @RequestBody CreateApplicationKey createApplicationKey) {

        if (authenticationContext.currentUserNotHaveAdminRoleInCompany(companyId)) {
            throw new AccessDeniedException();
        }

        return secretKeyService.create(createApplicationKey, companyId);
    }

    private @NonNull SecretKey getSecretKeyValidatingAccessOfCurrentUser(UUID secretKeyId) {
        val secretKey = secretKeyService.getById(secretKeyId);

        if (Objects.isNull(secretKey)) {
            throw new IllegalArgumentException("Secret key not found");
        }

        if (authenticationContext.currentUserNotHaveAdminRoleInCompany(secretKey.getCompanyId())) {
            throw new AccessDeniedException();
        }

        return secretKey;
    }
}
