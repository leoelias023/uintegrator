package br.com.dbaonline.uintegrator.api.service;

import br.com.dbaonline.uintegrator.api.entity.dto.Application;
import br.com.dbaonline.uintegrator.api.entity.dto.ApplicationStatus;
import lombok.NonNull;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ApplicationService {

    Application createApplication(Application application) throws IOException;

    Application getApplicationBy(@NonNull UUID applicationRegisterCode, @NonNull Long companyId);

    List<Application> listApplications(@NonNull Long companyId);

    ApplicationStatus searchByApplicationStatus(@NonNull UUID applicationRegisterCode) throws IOException;
}
