package br.com.dbaonline.uintegrator.api.config;

import br.com.dbaonline.uintegrator.logs.LogConfigurer;
import br.com.dbaonline.uintegrator.logs.commands.ConfigureCommand;
import lombok.Data;
import lombok.val;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Properties;

@Data
@Configuration
@ConfigurationProperties(prefix = "log.config")
public class LogConfigVanilla {

    private Integer minutesHeartbeatPulse = 3;

    private static final String PROP_HTTP_ENDPOINT = "http_endpoint";
    private static final String PROP_SECRET_KEY = "secret_key";
    private static final String PROP_APPLICATION_KEY = "application_key";

    public static void initialize() {
        val properties = getLogProperties();

        LogConfigurer.configure(
                ConfigureCommand.builder()
                        .httpEndpoint(properties.getProperty(PROP_HTTP_ENDPOINT))
                        .secretKey(properties.getProperty(PROP_SECRET_KEY))
                        .applicationKey(properties.getProperty(PROP_APPLICATION_KEY))
                        .build()
        );
    }

    private static Properties getLogProperties() {
        try {
            val properties = new Properties();
            val propertiesResource = new ClassPathResource("log.properties");

            if (!propertiesResource.exists()) {
                throw new IllegalStateException("Log configuration resource not found, please configure log.properties in classpath");
            }

            properties.load(propertiesResource.getInputStream());

            return properties;
        } catch (IOException e) {
            throw new IllegalStateException("Failed in load log.properties as properties", e);
        }
    }
}
