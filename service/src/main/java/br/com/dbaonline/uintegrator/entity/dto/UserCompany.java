package br.com.dbaonline.uintegrator.entity.dto;

import br.com.dbaonline.uintegrator.entity.transients.CompanyRole;
import lombok.Builder;
import lombok.Value;

/**
 * Represents the relationship between a company and a user.
 *
 * @author Leonardo Elias
 */
@Value
@Builder
public class UserCompany {

    /** Company details. */
    Company company;

    /** User details. */
    User user;

    /** Internal Role of user in company. */
    CompanyRole role;
}
