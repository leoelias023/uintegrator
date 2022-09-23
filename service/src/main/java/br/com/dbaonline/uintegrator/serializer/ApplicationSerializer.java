package br.com.dbaonline.uintegrator.serializer;

import br.com.dbaonline.uintegrator.entity.ApplicationEntity;
import br.com.dbaonline.uintegrator.entity.ApplicationModuleEntity;
import br.com.dbaonline.uintegrator.entity.dto.Application;
import br.com.dbaonline.uintegrator.entity.transients.ApplicationModule;
import lombok.NonNull;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ApplicationSerializer {

    public ApplicationEntity toEntity(@NonNull Application application) {
        val entity = new ApplicationEntity();

        entity.setDescription(application.getDescription());
        entity.setCompanyId(application.getCompanyId());
        entity.setPicture(application.getPicture());
        entity.setTitle(application.getTitle());
        entity.setRegisterCode(application.getRegisterCode());

        if (Objects.nonNull(application.getModules())) {
            entity.setModules(toEntities(application.getRegisterCode(), application.getModules()));
        }

        return entity;
    }

    public Application fromEntity(@NonNull ApplicationEntity entity) {
        return Application.builder()
            .companyId(entity.getCompanyId())
            .modules(fromEntities(entity.getModules()))
            .description(entity.getDescription())
            .title(entity.getTitle())
            .picture(entity.getPicture())
            .registerCode(entity.getRegisterCode())
            .build();
    }

    private List<ApplicationModuleEntity> toEntities(UUID registerCode, List<ApplicationModule> modules) {
        return modules.stream()
            .map(applicationModule -> {

                val entity = new ApplicationModuleEntity();

                entity.setApplicationRegisterCode(registerCode);
                entity.setModule(applicationModule);

                return entity;
            })
            .collect(Collectors.toList());
    }

    private List<ApplicationModule> fromEntities(List<ApplicationModuleEntity> entities) {
        return entities.stream()
                .map(ApplicationModuleEntity::getModule)
                .collect(Collectors.toList());
    }
}
