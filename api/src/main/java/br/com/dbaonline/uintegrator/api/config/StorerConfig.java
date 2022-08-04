package br.com.dbaonline.uintegrator.api.config;

import br.com.dbaonline.uintegrator.storer.config.ElasticSearchConfig;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@ConfigurationProperties(prefix = "storer")
public class StorerConfig extends ElasticSearchConfig {

}
