package br.com.dbaonline.uintegrator.entity;

import br.com.dbaonline.uintegrator.entity.transients.CompanyRole;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_company", uniqueConstraints = {
    @UniqueConstraint(name = "unique_user_company", columnNames = {"company_id", "user_id"})
})
public class UserCompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private CompanyEntity company;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column
    private CompanyRole role;
}
