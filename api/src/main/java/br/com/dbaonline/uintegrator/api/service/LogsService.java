package br.com.dbaonline.uintegrator.api.service;

import br.com.dbaonline.uintegrator.api.entity.dto.ApplicationStatus;
import lombok.NonNull;

import java.io.IOException;
import java.util.UUID;

public interface LogsService {

    ApplicationStatus getApplicationStatusByLogs(@NonNull UUID applicationRegisterCode) throws IOException;

    void createApplicationLogIndex(@NonNull UUID applicationCode) throws IOException;
}
