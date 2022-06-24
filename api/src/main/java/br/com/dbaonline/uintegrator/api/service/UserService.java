package br.com.dbaonline.uintegrator.api.service;

import br.com.dbaonline.uintegrator.api.entity.dto.CreateUser;
import br.com.dbaonline.uintegrator.api.entity.dto.User;
import lombok.NonNull;

public interface UserService {
    void createUser(@NonNull CreateUser createUser);

    void delete(@NonNull Long userId);

    void update(@NonNull Long userId, @NonNull User user);

    User find(@NonNull Long userId);
}
