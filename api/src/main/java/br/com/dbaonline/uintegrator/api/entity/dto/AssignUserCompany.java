package br.com.dbaonline.uintegrator.api.entity.dto;

import br.com.dbaonline.uintegrator.api.entity.transients.CompanyRole;
import lombok.Builder;
import lombok.Value;

/**
 * Payload to assign a user to a company with a role-based relationship.
 *
 * @author Leonardo Elias
 */
@Value
@Builder
public class AssignUserCompany {
    Long companyId;
    Long userId;
    CompanyRole role;
}
