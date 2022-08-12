package br.com.dbaonline.uintegrator.api.service;

import br.com.dbaonline.uintegrator.api.entity.dto.CreateApplicationKey;
import br.com.dbaonline.uintegrator.api.entity.dto.CreatedApplicationKey;
import br.com.dbaonline.uintegrator.api.entity.dto.SecretKey;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

public interface SecretKeyService {

    SecretKey getById(@NonNull UUID uuid);

    List<SecretKey> getSecretKeysByCompanyId(@NonNull Long companyId);

    void authorizeApplication(@NonNull UUID secretKeyId, @NonNull UUID applicationRegisterCode);

    CreatedApplicationKey create(@NonNull CreateApplicationKey createApplicationKey, @NonNull Long companyId);
}
