package br.com.dbaonline.uintegrator.storer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ApplicationLog {

    private String message;

    @JsonProperty("@timestamp")
    private Date timestamp;
}
