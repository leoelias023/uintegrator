package br.com.dbaonline.uintegrator.entity.dto;

import br.com.dbaonline.uintegrator.entity.transients.LogLevel;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;

import java.util.Date;

@Value
@Builder
@ToString
public class Log {
    LogLevel level;
    String message;
    Date timestamp;
}
