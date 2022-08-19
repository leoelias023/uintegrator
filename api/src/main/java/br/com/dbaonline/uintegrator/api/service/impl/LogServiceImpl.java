package br.com.dbaonline.uintegrator.api.service.impl;

import br.com.dbaonline.uintegrator.api.config.LogConfigVanilla;
import br.com.dbaonline.uintegrator.api.config.StorerConfig;
import br.com.dbaonline.uintegrator.api.service.LogsService;
import br.com.dbaonline.uintegrator.entity.dto.ApplicationStatus;
import br.com.dbaonline.uintegrator.entity.transients.TagStatus;
import br.com.dbaonline.uintegrator.storer.client.LogClient;
import br.com.dbaonline.uintegrator.storer.entity.ApplicationLog;
import lombok.NonNull;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class LogServiceImpl implements LogsService {

    @Autowired
    private StorerConfig config;

    @Autowired
    private LogConfigVanilla logConfig;

    private LogClient logClient;

    @PostConstruct
    private void init() {
        logClient = new LogClient(config);
    }

    @Override
    public ApplicationStatus getApplicationStatusByLogs(@NonNull UUID applicationRegisterCode) throws IOException {
        return ApplicationStatus.builder()
                .tags(getTagStatusOfApplication(applicationRegisterCode))
                .lastPulse(getLastPulse(applicationRegisterCode))
                .build();
    }

    @Override
    public void createApplicationLogIndex(@NonNull UUID applicationCode) throws IOException {
        logClient.createDataStream(applicationCode);
    }

    private List<TagStatus> getTagStatusOfApplication(@NonNull UUID applicationCode) throws IOException {
        val list = new ArrayList<TagStatus>();

        if (logClient.hasErrorToday(applicationCode)) {
            list.add(TagStatus.ERROR);
        }

        if (applicationIsActive(applicationCode)) {
            list.add(TagStatus.RUNNING);
        } else {
            list.add(TagStatus.DISCONNECTED);
        }

        return list;
    }

    private Date getLastPulse(@NonNull UUID applicationCode) throws IOException {
        return Optional.ofNullable(logClient.last(applicationCode))
                .map(ApplicationLog::getTimestamp)
                .orElse(null);
    }

    private boolean applicationIsActive(@NonNull UUID applicationCode) throws IOException {
        val lastLog = logClient.last(applicationCode);

        if (lastLog == null) {
            return false;
        }

        val now = new Date();

        long diffLastReceivedLogPulse = Math.abs(now.getTime() - lastLog.getTimestamp().getTime());
        long diffMinutes = TimeUnit.MINUTES.convert(diffLastReceivedLogPulse, TimeUnit.MILLISECONDS);

        return diffMinutes + 1 <= logConfig.getMinutesHeartbeatPulse();
    }
}
