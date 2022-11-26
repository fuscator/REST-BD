package ru.tokarev.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.tokarev.spring.boot_security.demo.dto.RoleDTO;
import ru.tokarev.spring.boot_security.demo.dto.UserDTO;
import ru.tokarev.spring.boot_security.demo.models.User;
import ru.tokarev.spring.boot_security.demo.services.RoleService;
import ru.tokarev.spring.boot_security.demo.services.UserService;
import ru.tokarev.spring.boot_security.demo.util.RegistrationWrongUsernameException;
import ru.tokarev.spring.boot_security.demo.util.RegistrationWrongUsernameResponse;
import ru.tokarev.spring.boot_security.demo.util.UserValidator;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("admin/api")
public class AdminsController {

    private final UserService userService;
    private final UserValidator userValidator;
    private final RoleService roleService;

    @Autowired
    public AdminsController(UserService userService, UserValidator userValidator, RoleService roleService) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.roleService = roleService;
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.findAll().stream().map(userService::convertToDto).collect(Collectors.toList());
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody UserDTO userDTO, @PathVariable("id") int id) {
        userService.changeUser(id, userService.convertToUser(userDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<?> addNewUser (@RequestBody UserDTO userDTO, BindingResult bindingResult) {
        User user = userService.convertToUser(userDTO);
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new RegistrationWrongUsernameException();
        }
        userService.addUser(user);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @GetMapping("/auth")
    public UserDTO getAuth(Authentication auth) {
        return userService.convertToDto(userService.findByUsername(auth.getName()));
    }

    @GetMapping("/roles")
    public Set<RoleDTO> getAllRoles() {
        return roleService.findAll().stream().map(roleService::convertToDto).collect(Collectors.toSet());
    }
    @ExceptionHandler
    private ResponseEntity<RegistrationWrongUsernameResponse> exceptionHandler(RegistrationWrongUsernameException e) {
        return new ResponseEntity<>(new RegistrationWrongUsernameResponse("Username already exists"),
                HttpStatus.NOT_ACCEPTABLE) ;
    }
}
