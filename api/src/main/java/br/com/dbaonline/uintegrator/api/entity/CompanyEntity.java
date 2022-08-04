package br.com.dbaonline.uintegrator.api.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "company")
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Lob
    @Column
    private byte[] logo;

    @OneToMany
    @JoinColumn(name = "company_id")
    private List<ApplicationEntity> applications;
}
