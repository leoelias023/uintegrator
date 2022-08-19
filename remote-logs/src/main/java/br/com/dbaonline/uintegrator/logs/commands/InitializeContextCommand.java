package br.com.dbaonline.uintegrator.logs.commands;

import lombok.Builder;
import lombok.Value;

import java.util.concurrent.Executor;

@Value
@Builder
public class InitializeContextCommand {
    String httpEndpoint;
    String secretKey;
    String applicationKey;
    Executor executor;
}
