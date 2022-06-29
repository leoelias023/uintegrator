package br.com.dbaonline.uintegrator.api.repository;

import br.com.dbaonline.uintegrator.api.entity.UserCompanyEntity;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCompanyRepository extends CrudRepository<UserCompanyEntity, Long> {

    List<UserCompanyEntity> findAllByCompany_Id(@NonNull Long companyId);

    UserCompanyEntity findByCompany_IdAndUser_Id(@NonNull Long companyId, @NonNull Long userId);
}
