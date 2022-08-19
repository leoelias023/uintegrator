package br.com.dbaonline.uintegrator.logs.submitter;

import br.com.dbaonline.uintegrator.entity.dto.Log;
import br.com.dbaonline.uintegrator.entity.transients.LogLevel;
import br.com.dbaonline.uintegrator.logs.LogContext;
import br.com.dbaonline.uintegrator.logs.client.IngestorClient;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;

/**
 * Log submitter responsible for send logs through http protocol to the server.
 *
 * @author Leonardo Elias
 */
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class HttpLogSubmitter implements LogSubmitter {

    private static final LogContext context = LogContext.getInstance();

    @Override
    public void submit(@NonNull String message, @NonNull LogLevel logLevel) {
        context.getExecutor().execute(() -> IngestorClient
                .send(
                        Log.builder()
                                .level(logLevel)
                                .message(message)
                                .timestamp(new Date())
                            .build(),
                        IngestorClient.Credentials.builder()
                                .applicationKey(context.applicationKey())
                                .secretKey(context.secretKey())
                                .build()
                )
        );
    }
}
