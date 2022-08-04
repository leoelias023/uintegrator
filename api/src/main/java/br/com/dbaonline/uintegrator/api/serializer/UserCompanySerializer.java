package br.com.dbaonline.uintegrator.api.serializer;

import br.com.dbaonline.uintegrator.api.entity.UserCompanyEntity;
import br.com.dbaonline.uintegrator.api.entity.dto.UserCompany;
import lombok.NonNull;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCompanySerializer {

    @Autowired
    private UserSerializer userSerializer;

    @Autowired
    private CompanySerializer companySerializer;

    public UserCompany fromEntity(@NonNull UserCompanyEntity entity) {
        return UserCompany.builder()
                .user(userSerializer.fromEntity(entity.getUser()))
                .company(companySerializer.fromEntity(entity.getCompany()))
                .role(entity.getRole())
                .build();
    }

    public UserCompanyEntity toEntity(@NonNull UserCompany userCompany) {
        val entity = new UserCompanyEntity();

        entity.setCompany(companySerializer.toEntity(userCompany.getCompany()));
        entity.setUser(userSerializer.toEntity(userCompany.getUser()));
        entity.setRole(userCompany.getRole());

        return entity;
    }

}
