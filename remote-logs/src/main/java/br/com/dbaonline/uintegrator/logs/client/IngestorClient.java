package br.com.dbaonline.uintegrator.logs.client;

import br.com.dbaonline.uintegrator.constants.ServiceConstants;
import br.com.dbaonline.uintegrator.entity.dto.Log;
import br.com.dbaonline.uintegrator.logs.LogContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class IngestorClient {

    private static final LogContext context = LogContext.getInstance();
    private static final HttpClient httpClient = HttpClient.newHttpClient();

    @Value
    @Builder
    public static class Credentials {
        String applicationKey;
        String secretKey;
    }

    @SneakyThrows
    public static void send(@NonNull Log log, @NonNull Credentials credentials) {

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
    }
}
