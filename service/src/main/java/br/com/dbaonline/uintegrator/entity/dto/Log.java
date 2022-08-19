package br.com.dbaonline.uintegrator.entity.dto;

import br.com.dbaonline.uintegrator.entity.transients.LogLevel;
import lombok.Builder;
import lombok.Value;

import java.util.Date;

@Value
@Builder
public class Log {
    LogLevel level;
    String message;
    Date timestamp;
}
