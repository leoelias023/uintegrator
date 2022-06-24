package br.com.dbaonline.uintegrator.api.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<UserRoleEntity> roles;
}
