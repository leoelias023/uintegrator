package br.com.dbaonline.uintegrator.service.impl;

import br.com.dbaonline.uintegrator.entity.ApplicationEntity;
import br.com.dbaonline.uintegrator.entity.SecretKeyEntity;
import br.com.dbaonline.uintegrator.entity.dto.Application;
import br.com.dbaonline.uintegrator.repository.ApplicationRepository;
import br.com.dbaonline.uintegrator.repository.SecretKeyRepository;
import br.com.dbaonline.uintegrator.serializer.ApplicationSerializer;
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

    @Autowired
    private ApplicationSerializer applicationSerializer;

    @Override
    public boolean isValidSecretKeyAccess(String secretKey, UUID targetApplicationUUID) {

        if (secretKey == null || targetApplicationUUID == null) {
            return false;
        }

        val authorizedKeys = secretKeyRepository.findAllByAuthorizedApplicationsContains(
                applicationSerializer.toEntity(
                        Application.builder()
                                .registerCode(targetApplicationUUID)
                                .build()
                )
        );

        for (SecretKeyEntity authorizedKey : authorizedKeys) {
            val savedSecretKey = authorizedKey.getEncryptedKey();

            if (passwordEncoder.matches(secretKey, savedSecretKey)) {
                return true;
            }
        }

        return false;
    }
}
