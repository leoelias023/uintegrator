package br.com.dbaonline.uintegrator.api.service;

import br.com.dbaonline.uintegrator.entity.dto.AssignUserCompany;
import br.com.dbaonline.uintegrator.entity.dto.UserCompany;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface UserCompanyService {

    UserCompany assignUserToCompany(@NonNull AssignUserCompany userCompany);

    List<UserCompany> listUsersOfCompany(@NonNull Long companyId);

    /**
     * @return {@link UserCompany} with respective role if this is part of companyId and userId specified.
     */
    Optional<UserCompany> userBelongsCompany(@NonNull Long companyId, @NonNull Long userId);
}
