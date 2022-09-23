package br.com.dbaonline.uintegrator.api.service.impl;

import br.com.dbaonline.uintegrator.serializer.CompanySerializer;
import br.com.dbaonline.uintegrator.api.service.CompanyService;
import br.com.dbaonline.uintegrator.entity.dto.Company;
import br.com.dbaonline.uintegrator.repository.CompanyRepository;
import lombok.NonNull;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanySerializer companySerializer;

    @Override
    @Transactional(readOnly = true)
    public List<Company> listAll() {
        return StreamSupport.stream(
                companyRepository.findAll().spliterator(), false)
                .map(companySerializer::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Company create(@NonNull Company company) {
        Assert.notNull(company.getName(), "Company name must be specified");

        val entity = companySerializer.toEntity(company);

        companyRepository.save(entity);

        return companySerializer.fromEntity(entity);
    }

}
