package br.com.dbaonline.uintegrator.service.impl;

import br.com.dbaonline.uintegrator.entity.ApplicationEntity;
import br.com.dbaonline.uintegrator.repository.ApplicationRepository;
import br.com.dbaonline.uintegrator.repository.SecretKeyRepository;
import br.com.dbaonline.uintegrator.service.ApplicationSecurityService;
import lombok.NonNull;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApplicationSecurityServiceImpl implements ApplicationSecurityService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecretKeyRepository secretKeyRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Override
    public boolean isValidSecretKeyAccess( String secretKey, UUID targetApplicationUUID) {

        if (secretKey == null || targetApplicationUUID == null) {
            return false;
        }

        val encodedSecretKey = passwordEncoder.encode(secretKey);
        val savedSecretKey = secretKeyRepository.getSecretKeyEntityByEncryptedKey(encodedSecretKey);

        if (savedSecretKey == null) {
            return false;
        }

        val application = applicationRepository.findById(targetApplicationUUID);
        val authorizedApplications = savedSecretKey.getAuthorizedApplications();

        return application.map(authorizedApplications::contains)
                .orElse(false);
    }
}
