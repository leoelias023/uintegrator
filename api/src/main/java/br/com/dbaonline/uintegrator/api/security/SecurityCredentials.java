package br.com.dbaonline.uintegrator.api.security;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Getter
public class SecurityCredentials {

    /** Chave secreta para geração da assinatura JWT. */
    @Value("${auth.secret-key}")
    private String authSecretKey;

    /** Algoritmo de assinatura JWT utilizado pelo processo de autenticação. */
    private Algorithm ALGORITHM;

    @PostConstruct
    public void init() {
        ALGORITHM = Algorithm.HMAC256(authSecretKey);
    }
}
