package br.com.dbaonline.uintegrator.api.entity;

import br.com.dbaonline.uintegrator.api.entity.transients.ApplicationModule;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity(name = "application_module")
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "unique_application_module", columnNames = {"application_register_code", "module"})
})
public class ApplicationModuleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "application_register_code")
    @Type(type="uuid-char")
    private UUID applicationRegisterCode;

    @Column(name = "module")
    private ApplicationModule module;
}
