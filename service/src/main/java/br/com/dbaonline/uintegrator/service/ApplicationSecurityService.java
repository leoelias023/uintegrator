package br.com.dbaonline.uintegrator.service;

import lombok.NonNull;

import java.util.UUID;

public interface ApplicationSecurityService {

    boolean isValidSecretKeyAccess(String secretKey, UUID targetApplicationUUID);
}
