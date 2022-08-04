package br.com.dbaonline.uintegrator.api.serializer;

import br.com.dbaonline.uintegrator.api.entity.UserEntity;
import br.com.dbaonline.uintegrator.api.entity.UserRoleEntity;
import br.com.dbaonline.uintegrator.api.entity.dto.User;
import br.com.dbaonline.uintegrator.api.entity.transients.UserRole;
import lombok.NonNull;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserSerializer {

    public User fromEntity(@NonNull UserEntity entity) {
        return User.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .roles(
                        entity.getRoles()
                                .stream()
                                .map(this::fromEntity)
                                .collect(Collectors.toList())
                )
                .build();
    }

    public UserRole fromEntity(@NonNull UserRoleEntity userRoleEntity) {
        return userRoleEntity.getRole();
    }

    public UserEntity toEntity(@NonNull User user) {
        val entity = toEntityWithoutRoles(user);

        entity.setRoles(user.getRoles().stream().map(userRole -> toEntity(user, userRole)).collect(Collectors.toList()));

        return entity;
    }

    public UserRoleEntity toEntity(@NonNull User user, @NonNull UserRole role) {
        val entity = new UserRoleEntity();

        entity.setUser(toEntityWithoutRoles(user));
        entity.setRole(role);

        return entity;
    }

    private UserEntity toEntityWithoutRoles(@NonNull User user) {
        val entity = new UserEntity();

        entity.setPassword(user.getPassword());
        entity.setId(user.getId());
        entity.setEmail(user.getEmail());

        return entity;
    }

}
