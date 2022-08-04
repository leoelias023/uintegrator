package br.com.dbaonline.uintegrator.api.repository;

import br.com.dbaonline.uintegrator.api.entity.ApplicationEntity;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ApplicationRepository extends CrudRepository<ApplicationEntity, UUID> {

    List<ApplicationEntity> findAllByCompanyId(@NonNull Long companyId);
}
