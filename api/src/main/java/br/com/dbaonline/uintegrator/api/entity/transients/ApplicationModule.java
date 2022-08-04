package br.com.dbaonline.uintegrator.api.entity.transients;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Module that a application may implement.
 *
 * @author Leonardo Elias
 */
@RequiredArgsConstructor
public enum ApplicationModule {
    LOGGING("Logging"),
    REMOTE_CONTROL("Remote Control"),
    PARAMETERS("Parameters");

    @Getter
    private final String friendlyName;
}
