package br.com.dbaonline.uintegrator.api.service.impl;

import br.com.dbaonline.uintegrator.serializer.SecretKeySerializer;
import br.com.dbaonline.uintegrator.api.service.ApplicationService;
import br.com.dbaonline.uintegrator.api.service.SecretKeyService;
import br.com.dbaonline.uintegrator.entity.dto.CreateApplicationKey;
import br.com.dbaonline.uintegrator.entity.dto.CreatedApplicationKey;
import br.com.dbaonline.uintegrator.entity.dto.SecretKey;
import br.com.dbaonline.uintegrator.repository.ApplicationRepository;
import br.com.dbaonline.uintegrator.repository.SecretKeyRepository;
import lombok.NonNull;
import lombok.val;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SecretKeyServiceImpl implements SecretKeyService {

    @Autowired
    private SecretKeyRepository secretKeyRepository;

    @Autowired
    private SecretKeySerializer secretKeySerializer;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public SecretKey getById(@NonNull UUID uuid) {
        val secretKey = secretKeyRepository.findById(uuid);

        return secretKey
                .map(secretKeySerializer::fromEntity)
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SecretKey> getSecretKeysByCompanyId(@NonNull Long companyId) {
        return Optional.ofNullable(secretKeyRepository.findAllByCompanyId(companyId))
                .map(secretKeyEntities -> secretKeyEntities
                        .stream().map(secretKeySerializer::fromEntity)
                        .collect(Collectors.toList())
                )
                .orElse(new ArrayList<>());
    }

    @Override
    @Transactional
    public void authorizeApplication(@NonNull UUID secretKeyId, @NonNull UUID applicationRegisterCode) {

        secretKeyRepository.findById(secretKeyId)
                .ifPresentOrElse(secretKeyEntity -> applicationRepository.findById(applicationRegisterCode).ifPresentOrElse(
                            applicationEntity -> {

                                secretKeyEntity.getAuthorizedApplications()
                                        .add(applicationEntity);

                                secretKeyRepository.save(secretKeyEntity);

                            },
                        () -> { throw new IllegalArgumentException("Target application not exists"); }
                        ), () -> {throw new IllegalArgumentException("Secret key with specified id not found");});
    }

    @Override
    @Transactional
    public CreatedApplicationKey create(@NonNull CreateApplicationKey createApplicationKey, @NonNull Long companyId) {

        val generatedKey = Base64.encodeBase64URLSafeString(UUID.randomUUID().toString().getBytes());
        val cryptedGeneratedKey = passwordEncoder.encode(generatedKey);

        secretKeyRepository.save(
                secretKeySerializer.toEntity(
                        SecretKey.builder()
                                .id(UUID.randomUUID())
                                .title(createApplicationKey.getTitle())
                                .key(cryptedGeneratedKey)
                                .companyId(companyId)
                                .authorizedApplications(createApplicationKey.getAuthorizedApplications().stream().map(uuid -> applicationService
                                                .getApplicationBy(uuid, companyId) )
                                        .collect(Collectors.toList()))
                                .build()
                )
        );

        return CreatedApplicationKey.builder()
                .secretKey(generatedKey)
                .build();
    }
}
