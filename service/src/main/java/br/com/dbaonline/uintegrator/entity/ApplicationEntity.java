package br.com.dbaonline.uintegrator.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity(name = "application")
public class ApplicationEntity {

    @Id
    @Type(type="uuid-char")
    private UUID registerCode;

    @Column
    private String title;

    @Column(name = "company_id")
    private Long companyId;

    @Column
    private String description;

    @Column
    @Lob
    private byte[] picture;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "application_register_code")
    private List<ApplicationModuleEntity> modules;
}
