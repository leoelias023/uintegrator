package br.com.dbaonline.uintegrator.api.controller;

import br.com.dbaonline.uintegrator.api.entity.dto.WelcomeMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class IndexController {

    @GetMapping("/")
    public WelcomeMessage get() {
        return WelcomeMessage.builder()
                .message("Unity Integrator API")
                .date(new Date())
                .build();
    }
}
