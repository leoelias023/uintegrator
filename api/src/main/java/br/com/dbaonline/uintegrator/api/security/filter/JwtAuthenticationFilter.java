package br.com.dbaonline.uintegrator.api.security.filter;

import br.com.dbaonline.uintegrator.api.security.SecurityConstant;
import br.com.dbaonline.uintegrator.api.security.UserAuthorizationToken;
import br.com.dbaonline.uintegrator.api.security.UserDetailsData;
import br.com.dbaonline.uintegrator.entity.UserEntity;
import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Filtro de requisição utilizado durante requisição de login
 * via configuração do Spring Security para realização da autenticação do usuário
 * através dos dados recebidos.
 *
 * @author Leonardo Elias de Oliveira.
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final SecurityConstant securityConstant;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, SecurityConstant securityConstant) {
        super(authenticationManager);
        this.securityConstant = securityConstant;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            val usuario = new ObjectMapper().readValue(request.getInputStream(), UserEntity.class);
            return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(usuario.getEmail(), usuario.getPassword(), new ArrayList<>()));

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        val userData = (UserDetailsData) authResult.getPrincipal();

        val token = JWT.create()
                .withSubject(userData.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + securityConstant.EXPIRED_MILLISECONDS))
                .sign(securityConstant.getAlgorithm());

        response.getWriter().write(new ObjectMapper().writeValueAsString(new UserAuthorizationToken(token)));
        response.getWriter().flush();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
