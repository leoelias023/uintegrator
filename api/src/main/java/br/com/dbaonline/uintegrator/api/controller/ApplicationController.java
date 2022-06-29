package br.com.dbaonline.uintegrator.api.controller;

import br.com.dbaonline.uintegrator.api.entity.dto.Application;
import br.com.dbaonline.uintegrator.api.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/company/{companyId}/application")
    public List<Application> listApplicationsOfCompany(@PathVariable Long companyId) {
        return applicationService.listApplications(companyId);
    }

    @PostMapping("/application")
    public void createApplication(@RequestBody Application application) {
        applicationService.createApplication(application);
    }

}
