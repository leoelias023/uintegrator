package br.com.dbaonline.uintegrator.api.service;

import br.com.dbaonline.uintegrator.api.entity.dto.Application;
import lombok.NonNull;

import java.util.List;

public interface ApplicationService {

    void createApplication(Application application);

    List<Application> listApplications(@NonNull Long companyId);
}
