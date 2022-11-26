package ru.tokarev.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.tokarev.spring.boot_security.demo.models.User;
import ru.tokarev.spring.boot_security.demo.services.UserService;

@Component
public class UserValidator implements Validator {
    UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "", "User with this email already exists");
        }
    }
}
