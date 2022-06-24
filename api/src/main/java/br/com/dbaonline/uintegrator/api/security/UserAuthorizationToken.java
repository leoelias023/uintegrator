package br.com.dbaonline.uintegrator.api.security;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.Date;

@Value
@RequiredArgsConstructor
public class UserAuthorizationToken {
    String token;
    Date createdAt = new Date();
}
