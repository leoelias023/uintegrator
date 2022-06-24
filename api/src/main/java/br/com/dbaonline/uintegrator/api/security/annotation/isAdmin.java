package br.com.dbaonline.uintegrator.api.security.annotation;

import br.com.dbaonline.uintegrator.api.security.UserRoles;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

/**
 * Identifica um recurso que só poderá ser acessivel a {@link br.com.dbaonline.uintegrator.api.entity.transients.UserRole}.
 *
 * Utilize em controllers para delimitar acesso aos recursos.
 *
 * @author Leonardo Elias
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@PreAuthorize("hasRole('" + UserRoles.Fields.ADMIN + "')")
public @interface isAdmin {
}
