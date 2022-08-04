package br.com.dbaonline.uintegrator.api.service.impl;

import br.com.dbaonline.uintegrator.api.controller.AuthenticationContext;
import br.com.dbaonline.uintegrator.api.entity.dto.Application;
import br.com.dbaonline.uintegrator.api.entity.transients.CompanyRole;
import br.com.dbaonline.uintegrator.api.exception.AccessDeniedException;
import br.com.dbaonline.uintegrator.api.repository.ApplicationRepository;
import br.com.dbaonline.uintegrator.api.serializer.ApplicationSerializer;
import br.com.dbaonline.uintegrator.api.service.ApplicationService;
import br.com.dbaonline.uintegrator.api.service.UserCompanyService;
import lombok.NonNull;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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

    @Override
    @Transactional
    public void createApplication(Application application) {
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
