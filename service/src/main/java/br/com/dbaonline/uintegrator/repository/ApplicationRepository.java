package br.com.dbaonline.uintegrator.repository;

import br.com.dbaonline.uintegrator.entity.ApplicationEntity;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ApplicationRepository extends CrudRepository<ApplicationEntity, UUID> {

    List<ApplicationEntity> findAllByCompanyId(@NonNull Long companyId);

    ApplicationEntity getApplicationEntityByCompanyIdAndRegisterCode(@NonNull Long companyId, @NonNull UUID registerCode);
}
