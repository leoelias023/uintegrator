package br.com.dbaonline.uintegrator.api.service;

import br.com.dbaonline.uintegrator.entity.dto.Company;
import lombok.NonNull;

import java.util.List;

public interface CompanyService {

    List<Company> listAll();

    Company create(@NonNull Company company);
}
