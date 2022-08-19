package br.com.dbaonline.uintegrator.entity.dto;

import br.com.dbaonline.uintegrator.entity.transients.TagStatus;
import lombok.Builder;
import lombok.Value;

import java.util.Date;
import java.util.List;

/**
 * Application status heartbeat.
 *
 * @author Leonardo Elias
 */
@Value
@Builder
public class ApplicationStatus {

    /** Current tags of app. */
    List<TagStatus> tags;

    /** Datetime of last heartbeat event received. */
    Date lastPulse;
}
