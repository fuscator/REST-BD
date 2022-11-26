package ru.tokarev.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.tokarev.spring.boot_security.demo.dto.UserDTO;
import ru.tokarev.spring.boot_security.demo.services.UserService;

@RestController
@RequestMapping("user/api")
public class UsersController {
    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public UserDTO getUser(Authentication auth) {
        return userService.convertToDto(userService.findByUsername(auth.getName()));
    }
}
