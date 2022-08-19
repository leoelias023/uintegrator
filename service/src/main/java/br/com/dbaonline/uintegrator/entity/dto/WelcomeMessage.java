package br.com.dbaonline.uintegrator.entity.dto;

import lombok.Builder;
import lombok.Value;

import java.util.Date;

@Value
@Builder
public class WelcomeMessage {
    String message;
    Date date;
}
