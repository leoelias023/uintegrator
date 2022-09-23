package br.com.dbaonline.uintegrator.repository;

import br.com.dbaonline.uintegrator.entity.ApplicationEntity;
import br.com.dbaonline.uintegrator.entity.SecretKeyEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SecretKeyRepository extends CrudRepository<SecretKeyEntity, UUID> {

    List<SecretKeyEntity> findAllByCompanyId(Long companyId);

    SecretKeyEntity getSecretKeyEntityByEncryptedKey(String encryptedKey);

    List<SecretKeyEntity> findAllByAuthorizedApplicationsContains(ApplicationEntity applicationEntity);
}
