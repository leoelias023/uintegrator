package br.com.dbaonline.uintegrator.api.service.impl;

import br.com.dbaonline.uintegrator.serializer.CompanySerializer;
import br.com.dbaonline.uintegrator.serializer.UserCompanySerializer;
import br.com.dbaonline.uintegrator.serializer.UserSerializer;
import br.com.dbaonline.uintegrator.api.service.UserCompanyService;
import br.com.dbaonline.uintegrator.entity.dto.AssignUserCompany;
import br.com.dbaonline.uintegrator.entity.dto.UserCompany;
import br.com.dbaonline.uintegrator.repository.CompanyRepository;
import br.com.dbaonline.uintegrator.repository.UserCompanyRepository;
import br.com.dbaonline.uintegrator.repository.UserRepository;
import lombok.NonNull;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserCompanyServiceImpl implements UserCompanyService {

    @Autowired
    private UserCompanyRepository userCompanyRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSerializer userSerializer;

    @Autowired
    private CompanySerializer companySerializer;

    @Autowired
    private UserCompanySerializer userCompanySerializer;

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

    @Override
    @Transactional(readOnly = true)
    public List<UserCompany> listUsersOfCompany(@NonNull Long companyId) {

        val relationships = userCompanyRepository.findAllByCompany_Id(companyId);

        return relationships.stream()
                .map(userCompanySerializer::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserCompany> userBelongsCompany(@NonNull Long companyId, @NonNull Long userId) {

        val userCompany = userCompanyRepository.findByCompany_IdAndUser_Id(companyId, userId);

        return Optional.ofNullable(userCompany)
                .map(userCompanySerializer::fromEntity);
    }

}
