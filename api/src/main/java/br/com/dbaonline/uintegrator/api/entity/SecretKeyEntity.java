package br.com.dbaonline.uintegrator.api.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity(name = "secret_key")
@Data
public class SecretKeyEntity {

    @Id
    @Type(type="uuid-char")
    private UUID id;

    @Column
    private Long companyId;

    @Column
    private String title;

    @Column
    private String encryptedKey;

    @ManyToMany
    private List<ApplicationEntity> authorizedApplications;
}
