package br.com.dbaonline.uintegrator.ingestor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@ComponentScan(basePackages = "br.com.dbaonline.uintegrator")
@EnableJpaRepositories(basePackages = "br.com.dbaonline.uintegrator.repository")
@EntityScan(basePackages = "br.com.dbaonline.uintegrator.entity")
public class IngestorApplication {

    public static void main(String... args) {
        SpringApplication.run(IngestorApplication.class, args);
    }
}
