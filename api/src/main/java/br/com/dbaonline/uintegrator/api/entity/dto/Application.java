package br.com.dbaonline.uintegrator.api.entity.dto;

import br.com.dbaonline.uintegrator.api.entity.transients.ApplicationModule;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.UUID;

/**
 * Represents a registered application to be managed by Unity Integrator.
 *
 * @author Leonardo Elias
 */
@Value
@Builder
public class Application {

    /** Unique code that identify uniquely the application. */
    UUID registerCode;

    /** Identifier of company where application is registered. */
    Long companyId;

    /** Friendly title. */
    String title;

    /** Friendly description about application. */
    String description;

    /** Representative picture to make the identification easy. */
    byte[] picture;

    /** Used modules. */
    List<ApplicationModule> modules;
}
