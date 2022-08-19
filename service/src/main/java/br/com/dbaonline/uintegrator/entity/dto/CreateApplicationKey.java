package br.com.dbaonline.uintegrator.entity.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
@Builder
public class CreateApplicationKey {
    String title;
    List<UUID> authorizedApplications;
}
