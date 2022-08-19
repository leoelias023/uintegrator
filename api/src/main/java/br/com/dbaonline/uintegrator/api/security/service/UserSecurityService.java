package br.com.dbaonline.uintegrator.api.security.service;

import br.com.dbaonline.uintegrator.entity.UserRoleEntity;
import br.com.dbaonline.uintegrator.entity.dto.User;
import br.com.dbaonline.uintegrator.entity.transients.UserRole;
import br.com.dbaonline.uintegrator.repository.UserRepository;
import br.com.dbaonline.uintegrator.repository.UserRoleRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserSecurityService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    public Optional<User> getUserByEmail(@NonNull String email) {
        return userRepository.findByEmail(email)
                .map(entity -> User.builder()
                        .id(entity.getId())
                        .password(entity.getPassword())
                        .email(entity.getEmail())
                        .roles(getUserRolesById(entity.getId()))
                        .build());
    }

    private List<UserRole> getUserRolesById(@NonNull Long userId) {
        return userRoleRepository.getUserRoleEntitiesByUserId(userId)
                .stream()
                .map(UserRoleEntity::getRole)
                .collect(Collectors.toList());
    }

}
