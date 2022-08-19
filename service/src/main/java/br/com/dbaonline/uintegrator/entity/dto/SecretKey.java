package br.com.dbaonline.uintegrator.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
@Builder
public class SecretKey {
    UUID id;

    Long companyId;

    String title;

    @JsonIgnore
    String key;

    List<Application> authorizedApplications;
}
