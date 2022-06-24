package br.com.dbaonline.uintegrator.api.entity.dto;

import br.com.dbaonline.uintegrator.api.entity.UserEntity;
import br.com.dbaonline.uintegrator.api.entity.transients.UserRole;
import lombok.Builder;
import lombok.Value;
import lombok.val;

import java.util.List;

@Value
@Builder
public class CreateUser {
    String email;
    String password;
    List<UserRole> roles;

    public UserEntity toEntity() {
        val user = new UserEntity();

        user.setEmail(email);
        user.setPassword(password);

        return user;
    }

}
