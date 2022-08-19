package br.com.dbaonline.uintegrator.repository;

import br.com.dbaonline.uintegrator.entity.UserRoleEntity;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRoleEntity, Long> {

    List<UserRoleEntity> getUserRoleEntitiesByUserId(@NonNull Long userId);
}
