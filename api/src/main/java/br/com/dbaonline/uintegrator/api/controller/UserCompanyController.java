package br.com.dbaonline.uintegrator.api.controller;

import br.com.dbaonline.uintegrator.api.security.annotation.isAdmin;
import br.com.dbaonline.uintegrator.api.service.UserCompanyService;
import br.com.dbaonline.uintegrator.entity.dto.AssignUserCompany;
import br.com.dbaonline.uintegrator.entity.dto.UserCompany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@isAdmin
public class UserCompanyController {

    @Autowired
    private UserCompanyService userCompanyService;

    @GetMapping("/company/{companyId}/users")
    public List<UserCompany> listCompanyUsers(@PathVariable @Valid Long companyId) {

        Assert.notNull(companyId, "Company ID must be specified");

        return userCompanyService.listUsersOfCompany(companyId);
    }

    @PostMapping("/company/{companyId}/users")
    public UserCompany assignUserToCompany(@RequestBody @Valid AssignUserCompany userCompany) {
        return userCompanyService.assignUserToCompany(userCompany);
    }

}
