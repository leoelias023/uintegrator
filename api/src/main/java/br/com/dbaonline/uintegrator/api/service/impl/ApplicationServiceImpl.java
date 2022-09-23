package br.com.dbaonline.uintegrator.api.service.impl;

import br.com.dbaonline.uintegrator.api.controller.AuthenticationContext;
import br.com.dbaonline.uintegrator.api.exception.AccessDeniedException;
import br.com.dbaonline.uintegrator.serializer.ApplicationSerializer;
import br.com.dbaonline.uintegrator.api.service.ApplicationService;
import br.com.dbaonline.uintegrator.api.service.LogsService;
import br.com.dbaonline.uintegrator.entity.dto.Application;
import br.com.dbaonline.uintegrator.entity.dto.ApplicationStatus;
import br.com.dbaonline.uintegrator.entity.transients.ApplicationModule;
import br.com.dbaonline.uintegrator.repository.ApplicationRepository;
import lombok.NonNull;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private AuthenticationContext authenticationContext;

    @Autowired
    private ApplicationSerializer applicationSerializer;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private LogsService logsService;

    @Override
    @Transactional
    public Application createApplication(Application application) throws IOException {
        val companyId = application.getCompanyId();
        val title = application.getTitle();
        val description = application.getDescription();
        val modules = application.getModules();

        Assert.notNull(companyId, "Company ID must be specified to create new application");
        Assert.notNull(title, "Title must be specified");
        Assert.notNull(description, "Description must be specified");
        Assert.notNull(modules, "At least one module must be specified");

        if (authenticationContext.currentUserNotHaveAdminRoleInCompany(companyId)) {
            throw new AccessDeniedException();
        }

        val newApplication = Application.builder()
            .registerCode(UUID.randomUUID())
            .picture(application.getPicture())
            .title(title)
            .description(description)
            .modules(modules)
            .companyId(companyId)
            .build();

        applicationRepository.save(applicationSerializer.toEntity(newApplication));

        if (modules.contains(ApplicationModule.LOGGING)) {
            logsService.createApplicationLogIndex(newApplication.getRegisterCode());
        }

        return newApplication;
    }

    @Override
    public Application getApplicationBy(@NonNull UUID applicationRegisterCode, @NonNull Long companyId) {
        return applicationSerializer.fromEntity(
                applicationRepository.getApplicationEntityByCompanyIdAndRegisterCode(companyId, applicationRegisterCode)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<Application> listApplications(@NonNull Long companyId) {

        if (authenticationContext.currentUserIsNotMemberInCompany(companyId)) {
            throw new AccessDeniedException();
        }

        return applicationRepository.findAllByCompanyId(companyId).stream()
            .map(applicationSerializer::fromEntity)
            .collect(Collectors.toList());
    }

    @Override
    public ApplicationStatus searchByApplicationStatus(@NonNull UUID applicationRegisterCode) throws IOException {
        return logsService.getApplicationStatusByLogs(applicationRegisterCode);
    }

}
