package br.com.dbaonline.uintegrator.api.controller;

import br.com.dbaonline.uintegrator.api.entity.dto.AssignUserCompany;
import br.com.dbaonline.uintegrator.api.entity.dto.UserCompany;
import br.com.dbaonline.uintegrator.api.security.annotation.isAdmin;
import br.com.dbaonline.uintegrator.api.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@isAdmin
public class UserCompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/company/{companyId}/users")
    public List<UserCompany> listCompanyUsers(@PathVariable @Valid Long companyId) {

        Assert.notNull(companyId, "Company ID must be specified");

        return companyService.listUsersOfCompany(companyId);
    }

    @PostMapping("/company/{companyId}/users")
    public UserCompany assignUserToCompany(@RequestBody @Valid AssignUserCompany userCompany) {
        return companyService.assignUserToCompany(userCompany);
    }

}
