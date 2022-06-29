package br.com.dbaonline.uintegrator.api.service;

import br.com.dbaonline.uintegrator.api.entity.dto.AssignUserCompany;
import br.com.dbaonline.uintegrator.api.entity.dto.Company;
import br.com.dbaonline.uintegrator.api.entity.dto.UserCompany;
import lombok.NonNull;

import java.util.List;

public interface CompanyService {

    List<Company> listAll();

    Company create(@NonNull Company company);
}
