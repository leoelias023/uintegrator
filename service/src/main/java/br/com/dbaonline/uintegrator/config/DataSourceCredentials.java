package br.com.dbaonline.uintegrator.config;

import lombok.*;

@Value
@Builder
@NoArgsConstructor(force = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DataSourceCredentials {
    String driverClassName;
    String url;
    String username;
    String password;
}
