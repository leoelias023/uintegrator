package br.com.dbaonline.uintegrator.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "log.config")
public class LogConfig {
    private Integer minutesHeartbeatPulse = 2;
}
