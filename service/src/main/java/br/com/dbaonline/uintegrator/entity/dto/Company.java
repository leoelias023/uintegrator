package br.com.dbaonline.uintegrator.entity.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Company {
    Long id;
    String name;
    byte[] logo;
}
