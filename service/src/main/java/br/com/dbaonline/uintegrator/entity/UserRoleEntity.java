package br.com.dbaonline.uintegrator.entity;

import br.com.dbaonline.uintegrator.entity.transients.UserRole;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_role", uniqueConstraints = {
        @UniqueConstraint(name = "unique_user_role", columnNames = {"role", "user_id"})
})
public class UserRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "role")
    private UserRole role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
