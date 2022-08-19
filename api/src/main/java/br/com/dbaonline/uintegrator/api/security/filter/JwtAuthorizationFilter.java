package br.com.dbaonline.uintegrator.api.security.filter;

import br.com.dbaonline.uintegrator.api.security.SecurityConstant;
import br.com.dbaonline.uintegrator.api.security.service.UserSecurityService;
import br.com.dbaonline.uintegrator.entity.dto.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.NonNull;
import lombok.val;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Filtro para realizar autorização de um usuário a acesso a um recurso
 * através da verificação do JWT token disponibilizado.
 *
 * @author Leonardo Elias
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final SecurityConstant securityConstant;
    private final UserSecurityService userService;

    public JwtAuthorizationFilter(
            AuthenticationManager authenticationManager,
            SecurityConstant securityConstant,
            UserSecurityService userService) {
        super(authenticationManager);
        this.userService = userService;
        this.securityConstant = securityConstant;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            val authorization = request.getHeader(securityConstant.AUTHORIZATION_HEADER);

            if (authorization == null || !authorization.startsWith(securityConstant.AUTHORIZATION_HEADER_PREFIX)) {
                chain.doFilter(request, response);
                return;
            }

            val token = getTokenFromHeader(authorization);
            val authorizedToken = getAuthorizedToken(token);

            SecurityContextHolder.getContext().setAuthentication(authorizedToken);
            chain.doFilter(request, response);
        } catch (TokenExpiredException e) {
            chain.doFilter(request, response);
        }
    }

    private @NonNull String getTokenFromHeader(@NonNull String authorizationHeader) {
        return authorizationHeader.replace(securityConstant.AUTHORIZATION_HEADER_PREFIX, "");
    }

    private UsernamePasswordAuthenticationToken getAuthorizedToken(@NonNull String token) {
        val user = JWT.require(securityConstant.getAlgorithm())
                .build()
                .verify(token)
                .getSubject();

        if (user == null) {
            return null;
        }

        val userObject = userService.getUserByEmail(user);

        val roles = userObject
                .map(User::getRoles)
                .orElse(new ArrayList<>());

        return new UsernamePasswordAuthenticationToken(userObject, null, roles);
    }
}
