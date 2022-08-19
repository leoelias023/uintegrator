package br.com.dbaonline.uintegrator.ingestor.filter;

import br.com.dbaonline.uintegrator.constants.ServiceConstants;
import br.com.dbaonline.uintegrator.service.ApplicationSecurityService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Component
@WebFilter(urlPatterns = "/log/*")
public class SecretKeyValidationFilter implements Filter {

    @Autowired
    private ApplicationSecurityService applicationSecurityService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        val req = (HttpServletRequest) request;
        val res = (HttpServletResponse) response;

        val secretKey = req.getHeader(ServiceConstants.AUTHORIZATION_HEADER);
        val applicationUUID = req.getHeader(ServiceConstants.APPKEY_HEADER);

        if (applicationSecurityService.isValidSecretKeyAccess(secretKey, UUID.fromString(applicationUUID))) {
            chain.doFilter(request, response);
        }

        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
