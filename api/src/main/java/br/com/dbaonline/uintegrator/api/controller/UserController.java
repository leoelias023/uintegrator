package br.com.dbaonline.uintegrator.api.controller;

import br.com.dbaonline.uintegrator.api.entity.dto.CreateUser;
import br.com.dbaonline.uintegrator.api.entity.dto.User;
import br.com.dbaonline.uintegrator.api.security.annotation.isAdmin;
import br.com.dbaonline.uintegrator.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@isAdmin
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public void createUser(@Valid @RequestBody CreateUser user) {
        userService.createUser(user);
    }

    @DeleteMapping("/user/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.delete(userId);
    }

    @PutMapping("/user/{userId}")
    public void updateUser(@PathVariable Long userId, @RequestBody User user) {
        userService.update(userId, user);
    }

    @GetMapping("/user/{userId}")
    public User getUser(@PathVariable Long userId) {
        return userService.find(userId);
    }

}
