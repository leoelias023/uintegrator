package br.com.dbaonline.uintegrator.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Current request does not have access to the resource")
public class AccessDeniedException extends RuntimeException {
}
