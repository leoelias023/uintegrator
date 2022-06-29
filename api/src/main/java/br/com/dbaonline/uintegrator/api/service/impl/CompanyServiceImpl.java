package br.com.dbaonline.uintegrator.api.service.impl;

import br.com.dbaonline.uintegrator.api.entity.dto.AssignUserCompany;
import br.com.dbaonline.uintegrator.api.entity.dto.Company;
import br.com.dbaonline.uintegrator.api.entity.dto.UserCompany;
import br.com.dbaonline.uintegrator.api.repository.CompanyRepository;
import br.com.dbaonline.uintegrator.api.repository.UserCompanyRepository;
import br.com.dbaonline.uintegrator.api.repository.UserRepository;
import br.com.dbaonline.uintegrator.api.serializer.CompanySerializer;
import br.com.dbaonline.uintegrator.api.serializer.UserCompanySerializer;
import br.com.dbaonline.uintegrator.api.serializer.UserSerializer;
import br.com.dbaonline.uintegrator.api.service.CompanyService;
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
    private UserRepository userRepository;

    @Autowired
    private UserCompanyRepository userCompanyRepository;

    @Autowired
    private UserCompanySerializer userCompanySerializer;

    @Autowired
    private CompanySerializer companySerializer;

    @Autowired
    private UserSerializer userSerializer;

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

    @Override
    @Transactional(readOnly = true)
    public List<UserCompany> listUsersOfCompany(@NonNull Long companyId) {

        val relationships = userCompanyRepository.findAllByCompany_Id(companyId);

        return relationships.stream()
                .map(userCompanySerializer::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserCompany assignUserToCompany(@NonNull AssignUserCompany assignUserCompany) {

        val companyId = assignUserCompany.getCompanyId();
        val userId = assignUserCompany.getUserId();
        val role = assignUserCompany.getRole();

        Assert.notNull(companyId, "Company ID must be specified");
        Assert.notNull(userId, "User ID must be specified");
        Assert.notNull(role, "USER role must be specified");

        val company = companyRepository.findById(companyId)
                .orElseThrow(() -> { throw new IllegalArgumentException(
                        String.format(
                            "Company with identifier %s not found",
                                companyId
                        )
                ); });

        val user = userRepository.findById(userId)
                .orElseThrow(() -> { throw new IllegalArgumentException(
                        String.format(
                                "User with identifier %s not found",
                                userId
                        )
                ); });

        val userCompany = UserCompany.builder()
                .role(role)
                .user(userSerializer.fromEntity(user))
                .company(companySerializer.fromEntity(company))
                .build();

        userCompanyRepository.save(userCompanySerializer.toEntity(userCompany));
        return userCompany;
    }

}
