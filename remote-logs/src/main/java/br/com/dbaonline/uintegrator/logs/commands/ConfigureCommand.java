package br.com.dbaonline.uintegrator.logs.commands;

import lombok.Builder;
import lombok.Value;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Builder
@Value
public class ConfigureCommand {

    /** Http endpoint for receive logs data stream. */
    String httpEndpoint;

    /** Application register code (identify uniquely an application). */
    String applicationKey;

    /** Secret key for authentication of application. */
    String secretKey;

    /** An executor responsible for the sending http requests. (default will be {@link Executors#newSingleThreadExecutor()}. */
    Executor executor;
}
