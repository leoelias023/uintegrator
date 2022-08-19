package br.com.dbaonline.uintegrator.ingestor.controller;

import br.com.dbaonline.uintegrator.repository.UserRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String index() {
        return "Hello";
    }

}
