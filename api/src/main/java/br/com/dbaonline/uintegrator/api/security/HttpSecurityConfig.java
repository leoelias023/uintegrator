package br.com.dbaonline.uintegrator.api.security;

import br.com.dbaonline.uintegrator.api.security.filter.JwtAuthenticationFilter;
import br.com.dbaonline.uintegrator.api.security.filter.JwtAuthorizationFilter;
import br.com.dbaonline.uintegrator.api.security.service.UserSecurityService;
import br.com.dbaonline.uintegrator.api.service.UserService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Configuração do esquema de autenticação/autorização e demais
 * atributos de segurança HTTP.
 *
 * @author Leonardo Elias.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class HttpSecurityConfig {

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private SecurityConstant securityConstant;

    @Autowired
    private UserSecurityService userSecurityService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .userDetailsService(jwtUserDetailsService)
            .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.GET,
                        "/swagger-ui.html",
                        "/v3/api-docs/**",
                        "/swagger-ui/**"). permitAll()
            .anyRequest().authenticated().and()
            .addFilter(new JwtAuthenticationFilter(authenticationConfiguration.getAuthenticationManager(), securityConstant))
            .addFilter(new JwtAuthorizationFilter(authenticationConfiguration.getAuthenticationManager(), securityConstant, userSecurityService))
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfiguration() {
        val source = new UrlBasedCorsConfigurationSource();
        val corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
