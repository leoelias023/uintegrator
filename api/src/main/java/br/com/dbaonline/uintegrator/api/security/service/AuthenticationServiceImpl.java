package br.com.dbaonline.uintegrator.api.security.service;

import br.com.dbaonline.uintegrator.api.security.UserDetailsData;
import br.com.dbaonline.uintegrator.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementação de {@link UserDetailsService} com um único método
 * responsável por obter os dados de um usuário através do seu email.
 *
 * @author Leonardo Elias
 */
@Service
public class AuthenticationServiceImpl implements UserDetailsService {

    @Autowired
    private UserSecurityService userService;

    /**
     * Obtém as credenciais de um usuário.
     *
     * @param username Email do usuário.
     * @return Detalhes do usuário com credenciais.
     *
     * @throws UsernameNotFoundException Usuário com o email especificado não for encontrado.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.getUserByEmail(username)
                .map(UserDetailsData::new)
                .orElseThrow(() -> { throw new UsernameNotFoundException("User not found with e-mail " + username); });
    }

}
