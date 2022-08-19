package br.com.dbaonline.uintegrator.logs;

import br.com.dbaonline.uintegrator.logs.commands.ConfigureCommand;
import br.com.dbaonline.uintegrator.logs.commands.InitializeContextCommand;
import lombok.NonNull;
import lombok.val;

import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Class config to manage log configurations of the application.
 *
 * @author Leonardo Elias de Oliveira
 */
public class LogConfigurer {

    public static Executor singleThreadExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    /**
     * Configure log application, setting-up configurations and parameters.
     *
     * @param configureCommand Command object with parameters and config
     */
    public static void configure(@NonNull ConfigureCommand configureCommand) {

        Objects.requireNonNull(configureCommand.getHttpEndpoint(), "Http endpoint for the send of logs must be defined");
        Objects.requireNonNull(configureCommand.getSecretKey(), "Secret key credential must be defined for log processing");
        Objects.requireNonNull(configureCommand.getApplicationKey(), "Application key must be specified as parameter");

        val executor = Objects.isNull(configureCommand.getExecutor())
                ? defaultExecutor()
                : configureCommand.getExecutor();

        LogContext.initialize(
            InitializeContextCommand.builder()
                    .applicationKey(configureCommand.getApplicationKey())
                    .secretKey(configureCommand.getSecretKey())
                    .executor(executor)
                    .httpEndpoint(configureCommand.getHttpEndpoint())
                    .build()
        );
    }

    private static Executor defaultExecutor() {
        return singleThreadExecutor();
    }

}
