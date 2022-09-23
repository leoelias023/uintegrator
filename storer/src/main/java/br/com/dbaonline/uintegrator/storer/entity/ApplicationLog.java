package br.com.dbaonline.uintegrator.storer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationLog {

    private String level;
    private String message;

    @JsonProperty("@timestamp")
    private Date timestamp;
}
