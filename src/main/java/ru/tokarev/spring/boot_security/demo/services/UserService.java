package ru.tokarev.spring.boot_security.demo.services;

import ru.tokarev.spring.boot_security.demo.dto.UserDTO;
import ru.tokarev.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(int id);

    void addUser(User user);

    void changeUser(int id, User replaceUser);

    void deleteUser(int id);

    User findByUsername(String username);

    UserDTO convertToDto(User user);

    User convertToUser(UserDTO userDTO);
}
