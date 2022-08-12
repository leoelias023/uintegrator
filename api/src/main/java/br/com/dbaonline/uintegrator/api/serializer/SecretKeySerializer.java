package br.com.dbaonline.uintegrator.api.serializer;

import br.com.dbaonline.uintegrator.api.entity.SecretKeyEntity;
import br.com.dbaonline.uintegrator.api.entity.dto.SecretKey;
import lombok.NonNull;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SecretKeySerializer {

    @Autowired
    private ApplicationSerializer applicationSerializer;

    public SecretKeyEntity toEntity(@NonNull SecretKey secretKey) {
        val entity = new SecretKeyEntity();

        val applicationEntities = Optional.ofNullable(secretKey.getAuthorizedApplications())
                .map(applications -> applications.stream()
                        .map(applicationSerializer::toEntity)
                        .collect(Collectors.toList())
                ).orElse(new ArrayList<>());

        entity.setId(secretKey.getId());
        entity.setTitle(secretKey.getTitle());
        entity.setAuthorizedApplications(applicationEntities);
        entity.setCompanyId(secretKey.getCompanyId());
        entity.setEncryptedKey(secretKey.getKey());

        return entity;
    }

    public SecretKey fromEntity(@NonNull SecretKeyEntity entity) {

        val authorizedApplications = Optional.ofNullable(entity.getAuthorizedApplications())
                .map(applicationEntities -> applicationEntities.stream().map(applicationSerializer::fromEntity).collect(Collectors.toList()))
                .orElse(new ArrayList<>());

        return SecretKey.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .key(entity.getEncryptedKey())
                .companyId(entity.getCompanyId())
                .authorizedApplications(authorizedApplications)
                .build();
    }
}
