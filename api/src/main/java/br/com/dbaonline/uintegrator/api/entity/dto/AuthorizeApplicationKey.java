package br.com.dbaonline.uintegrator.api.entity.dto;

import lombok.*;

import java.util.UUID;

@Builder
@Value
@NoArgsConstructor(force = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthorizeApplicationKey {
    UUID applicationRegisterCode;
}
