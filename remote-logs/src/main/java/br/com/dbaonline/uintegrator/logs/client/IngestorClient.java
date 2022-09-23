package br.com.dbaonline.uintegrator.logs.client;

import br.com.dbaonline.uintegrator.constants.ServiceConstants;
import br.com.dbaonline.uintegrator.entity.dto.Log;
import br.com.dbaonline.uintegrator.entity.transients.LogLevel;
import br.com.dbaonline.uintegrator.logs.LogContext;
import br.com.dbaonline.uintegrator.logs.submitter.LogSubmitter;
import br.com.dbaonline.uintegrator.logs.submitter.Submitters;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;

public class IngestorClient {

    private static final LogContext context = LogContext.getInstance();
    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static final LogSubmitter localSubmitter = Submitters.localSubmitter();

    @Value
    @Builder
    public static class Credentials {
        String applicationKey;
        String secretKey;
    }

    public static void pulse(@NonNull Credentials credentials) {
        send(Log.builder()
                .message("heartbeat")
                .timestamp(new Date())
                .level(LogLevel.DEBUG)
                .build(), credentials);
    }

    public static void send(@NonNull Log log, @NonNull Credentials credentials) {
        try {
            val objectMapper = new ObjectMapper();

            httpClient.send(
                    HttpRequest.newBuilder()
                            .POST(
                                    HttpRequest.BodyPublishers
                                            .ofString(objectMapper.writeValueAsString(log))
                            )
                            .uri(
                                    URI.create(context.httpEndpoint()).resolve("/log")
                            )
                            .header(ServiceConstants.AUTHORIZATION_HEADER, credentials.getSecretKey())
                            .header(ServiceConstants.APPKEY_HEADER, credentials.getApplicationKey())
                            .header("Content-Type", "application/json")
                            .build(), HttpResponse.BodyHandlers.discarding()
            );
        } catch (IOException | InterruptedException e) {
            localSubmitter.submit("Could not possible send logs through HTTP, please verify the endpoint." + e.getMessage(), LogLevel.ERROR);
        }
    }
}
