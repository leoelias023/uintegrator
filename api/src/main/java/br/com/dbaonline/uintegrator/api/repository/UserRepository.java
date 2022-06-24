package br.com.dbaonline.uintegrator.api.repository;

import br.com.dbaonline.uintegrator.api.entity.UserEntity;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(@NonNull String email);

    boolean existsByEmail(@NonNull String email);
}
