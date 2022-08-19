package br.com.dbaonline.uintegrator.ingestor.controller;

import br.com.dbaonline.uintegrator.constants.ServiceConstants;
import br.com.dbaonline.uintegrator.entity.dto.Log;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LogController {

    @Autowired
    private HttpServletRequest request;

    @PostMapping("/log")
    public void consume(@RequestBody Log log) {
        val applicationCode = request.getHeader(ServiceConstants.APPKEY_HEADER);

        System.out.println("Inserir log no datastream");
    }
}
