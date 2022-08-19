package br.com.dbaonline.uintegrator.api.controller;

import br.com.dbaonline.uintegrator.api.security.annotation.isAdmin;
import br.com.dbaonline.uintegrator.api.service.CompanyService;
import br.com.dbaonline.uintegrator.entity.dto.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@isAdmin
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @GetMapping("/company")
    public List<Company> list() {
        return companyService.listAll();
    }

    @PostMapping("/company")
    public Company create(@RequestBody @Valid Company company) {
        return companyService.create(company);
    }
}
