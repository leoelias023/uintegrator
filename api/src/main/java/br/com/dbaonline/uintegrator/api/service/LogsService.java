package br.com.dbaonline.uintegrator.api.service;

import br.com.dbaonline.uintegrator.entity.dto.ApplicationStatus;
import br.com.dbaonline.uintegrator.storer.entity.ApplicationLog;
import lombok.NonNull;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface LogsService {

    ApplicationStatus getApplicationStatusByLogs(@NonNull UUID applicationRegisterCode) throws IOException;

    List<ApplicationLog> tailLogs(@NonNull UUID applicatioCode) throws IOException;

    void createApplicationLogIndex(@NonNull UUID applicationCode) throws IOException;
}
