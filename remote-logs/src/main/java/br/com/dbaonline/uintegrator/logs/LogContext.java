package br.com.dbaonline.uintegrator.logs;

import br.com.dbaonline.uintegrator.logs.commands.InitializeContextCommand;
import lombok.*;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;

@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LogContext {
    private final InitializeContextCommand context;
    private static LogContext instance;

    public static void initialize(@NonNull InitializeContextCommand context) {
        instance = new LogContext(context);
    }

    public static LogContext getInstance() {
        return instance;
    }

    public String httpEndpoint() {
        return Optional.ofNullable(context)
                .map(InitializeContextCommand::getHttpEndpoint)
                .orElse(null);
    }

    public Executor getExecutor() {
        return Optional.ofNullable(context)
                .map(InitializeContextCommand::getExecutor)
                .orElse(null);
    }

    public String secretKey() {
        return Optional.ofNullable(context)
                .map(InitializeContextCommand::getSecretKey)
                .orElse(null);
    }

    public String applicationKey() {
        return Optional.ofNullable(context)
                .map(InitializeContextCommand::getApplicationKey)
                .orElse(null);
    }
}
