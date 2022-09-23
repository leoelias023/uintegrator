package br.com.dbaonline.uintegrator.serializer;

import br.com.dbaonline.uintegrator.entity.CompanyEntity;
import br.com.dbaonline.uintegrator.entity.dto.Company;
import lombok.NonNull;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
public class CompanySerializer {

    public CompanyEntity toEntity(@NonNull Company company) {
        val entity = new CompanyEntity();

        entity.setId(company.getId());
        entity.setLogo(company.getLogo());
        entity.setName(company.getName());

        return entity;
    }

    public Company fromEntity(@NonNull CompanyEntity entity) {
        return Company.builder()
                .id(entity.getId())
                .logo(entity.getLogo())
                .name(entity.getName())
                .build();
    }
}
