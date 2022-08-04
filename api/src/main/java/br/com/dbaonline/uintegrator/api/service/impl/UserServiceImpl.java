package br.com.dbaonline.uintegrator.api.service.impl;

import br.com.dbaonline.uintegrator.api.entity.UserEntity;
import br.com.dbaonline.uintegrator.api.entity.UserRoleEntity;
import br.com.dbaonline.uintegrator.api.entity.dto.CreateUser;
import br.com.dbaonline.uintegrator.api.entity.dto.User;
import br.com.dbaonline.uintegrator.api.entity.transients.UserRole;
import br.com.dbaonline.uintegrator.api.exception.UserAlreadyExistsException;
import br.com.dbaonline.uintegrator.api.exception.UserNotFoundException;
import br.com.dbaonline.uintegrator.api.repository.UserRepository;
import br.com.dbaonline.uintegrator.api.repository.UserRoleRepository;
import br.com.dbaonline.uintegrator.api.serializer.UserSerializer;
import br.com.dbaonline.uintegrator.api.service.UserService;
import lombok.NonNull;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserSerializer userSerializer;

    @Override
    public void createUser(@NonNull CreateUser createUser) {

        Assert.notNull(createUser.getRoles(), "Some role must be specified");
        Assert.notNull(createUser.getEmail(), "E-mail of user must be specified");
        Assert.notNull(createUser.getPassword(), "Password of user must be specified");

        if (userRepository.existsByEmail(createUser.getEmail())) {
            throw new UserAlreadyExistsException();
        }

        val userEntity = CreateUser.builder()
                .email(createUser.getEmail())
                .password(passwordEncoder.encode(createUser.getPassword()))
                .build()
                .toEntity();

        userRepository.save(userEntity);
        saveRoles(userEntity, createUser.getRoles());
    }

    @Override
    public void delete(@NonNull Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public void update(@NonNull Long userId, @NonNull User user) {
        val entity = userRepository.findById(userId);

        if (entity.isEmpty()) {
            throw new UserNotFoundException();
        }

        val userEntity = entity.get();

        userRepository.save(userEntity);
    }

    @Override
    public User find(@NonNull Long userId) {
        return userRepository.findById(userId)
                .map(userSerializer::fromEntity)
                .orElseThrow(() -> { throw new UserNotFoundException(); });
    }

    private void saveRoles(@NonNull UserEntity user, @NonNull List<UserRole> roles) {
        roles.forEach(role -> saveRole(user, role));
    }

    private void saveRole(@NonNull UserEntity user, @NonNull UserRole role) {
        val userRoleEntity = new UserRoleEntity();

        userRoleEntity.setRole(role);
        userRoleEntity.setUser(user);

        userRoleRepository.save(userRoleEntity);
    }

}
