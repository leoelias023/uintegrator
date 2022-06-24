package br.com.dbaonline.uintegrator.api.security;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Constants utilitárias usadas durante processos de autenticação.
 *
 * @author Leonardo Elias
 */
@Component
public class SecurityConstant {

    @Autowired
    private SecurityCredentials securityCredentials;

    /** Validade para JWT tokens gerados no processo de autenticação. */
    public final int EXPIRED_MILLISECONDS = 600_000;

    /** Nome da chave do cabeçalho que contém o JWT token para autorização. */
    public final String AUTHORIZATION_HEADER = "Authorization";

    /** Prefixo do JWT token contido no cabeçalho da requisição. */
    public final String AUTHORIZATION_HEADER_PREFIX = "Bearer ";

    public final Algorithm getAlgorithm() {
        return securityCredentials.getALGORITHM();
    }
}
