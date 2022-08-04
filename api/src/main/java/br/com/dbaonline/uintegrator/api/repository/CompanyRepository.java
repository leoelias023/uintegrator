package br.com.dbaonline.uintegrator.api.repository;

import br.com.dbaonline.uintegrator.api.entity.CompanyEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends CrudRepository<CompanyEntity, Long> {
}
