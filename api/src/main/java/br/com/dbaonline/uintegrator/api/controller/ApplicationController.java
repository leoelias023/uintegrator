package br.com.dbaonline.uintegrator.api.controller;

import br.com.dbaonline.uintegrator.api.service.ApplicationService;
import br.com.dbaonline.uintegrator.entity.dto.Application;
import br.com.dbaonline.uintegrator.entity.dto.ApplicationStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/company/{companyId}/application")
    public List<Application> listApplicationsOfCompany(@PathVariable Long companyId) {
        return applicationService.listApplications(companyId);
    }

    @PostMapping("/application")
    public Application createApplication(@RequestBody Application application) throws IOException {
        log.info("Creating application");
        return applicationService.createApplication(application);
    }

    @GetMapping("/application/{id}/status")
    public ApplicationStatus applicationStatus(@PathVariable UUID id) throws IOException {
        return applicationService.searchByApplicationStatus(id);
    }

}
