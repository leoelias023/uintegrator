package br.com.dbaonline.uintegrator.ingestor.controller;

import br.com.dbaonline.uintegrator.constants.ServiceConstants;
import br.com.dbaonline.uintegrator.entity.dto.Log;
import br.com.dbaonline.uintegrator.storer.client.LogClient;
import br.com.dbaonline.uintegrator.storer.config.ElasticSearchConfig;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@RestController
public class LogController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ElasticSearchConfig elkConfig;

    private LogClient logClient;

    @PostConstruct
    public void init() {
        logClient = new LogClient(elkConfig);
    }

    @PostMapping("/log")
    public void consume(@RequestBody Log log) {
        val applicationCode = request.getHeader(ServiceConstants.APPKEY_HEADER);

        try {
            logClient.insert(UUID.fromString(applicationCode), log);
            System.out.println("[INGESTED | " + applicationCode + "] " + log);
        } catch (IOException e) {
            System.out.println("[ERROR| " + applicationCode + "] " + log);
        }
    }
}
