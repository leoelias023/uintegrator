package br.com.dbaonline.uintegrator.api.repository;

import br.com.dbaonline.uintegrator.api.service.UserService;
import br.com.dbaonline.uintegrator.entity.dto.CreateUser;
import br.com.dbaonline.uintegrator.entity.transients.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserService userService;

    @Test
    public void createUser() {

        userService.createUser(CreateUser.builder()
                .email("leoelias02@hotmail.com")
                .password("1234")
                .roles(List.of(UserRole.ADMIN, UserRole.COMMON))
                .build());
    }

}
