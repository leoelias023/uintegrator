package br.com.dbaonline.uintegrator.api.service.impl;

import br.com.dbaonline.uintegrator.api.controller.AuthenticationContext;
import br.com.dbaonline.uintegrator.api.entity.dto.Application;
import br.com.dbaonline.uintegrator.api.entity.dto.ApplicationStatus;
import br.com.dbaonline.uintegrator.api.entity.transients.ApplicationModule;
import br.com.dbaonline.uintegrator.api.entity.transients.CompanyRole;
import br.com.dbaonline.uintegrator.api.exception.AccessDeniedException;
import br.com.dbaonline.uintegrator.api.repository.ApplicationRepository;
import br.com.dbaonline.uintegrator.api.serializer.ApplicationSerializer;
import br.com.dbaonline.uintegrator.api.service.ApplicationService;
import br.com.dbaonline.uintegrator.api.service.LogsService;
import br.com.dbaonline.uintegrator.api.service.UserCompanyService;
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
    private UserCompanyService userCompanyService;

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

        if (!currentUserhasAdminRoleInCompany(companyId)) {
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
    @Transactional(readOnly = true)
    public List<Application> listApplications(@NonNull Long companyId) {

        if (!currentUserIsMemberInCompany(companyId)) {
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

    private boolean currentUserIsMemberInCompany(@NonNull Long companyId) {
        val currentUser = authenticationContext.currentUser();

        if (currentUser == null) {
            return false;
        }

        return userCompanyService.userBelongsCompany(companyId, currentUser.getId())
                .isPresent();
    }

    private boolean currentUserhasAdminRoleInCompany(@NonNull Long companyId) {

        val currentUser = authenticationContext.currentUser();

        if (currentUser == null) {
            return false;
        }

        return userCompanyService.userBelongsCompany(companyId, currentUser.getId())
                .filter(userCompany -> CompanyRole.ADMIN.equals(userCompany.getRole()))
                .isPresent();
    }

}
