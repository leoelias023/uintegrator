package br.com.dbaonline.uintegrator.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "br.com.dbaonline.uintegrator")
@EnableJpaRepositories(basePackages = "br.com.dbaonline.uintegrator.repository")
@EntityScan(basePackages = "br.com.dbaonline.uintegrator.entity")
public class ApiApplication {

    public static void main(String... args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}
