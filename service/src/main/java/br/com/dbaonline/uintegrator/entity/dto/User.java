package br.com.dbaonline.uintegrator.entity.dto;

import br.com.dbaonline.uintegrator.entity.transients.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class User {

    Long id;
    String email;

    @JsonIgnore
    String password;

    List<UserRole> roles;
}
