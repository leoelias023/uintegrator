package br.com.dbaonline.uintegrator.api.entity.dto;

import br.com.dbaonline.uintegrator.api.entity.transients.UserRole;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class User {

    Long id;
    String email;
    String password;

    List<UserRole> roles;


}
