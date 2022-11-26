package ru.tokarev.spring.boot_security.demo.services;

import ru.tokarev.spring.boot_security.demo.dto.RoleDTO;
import ru.tokarev.spring.boot_security.demo.models.Role;

import java.util.List;

public interface RoleService {

    void save(Role role);

    List<Role> findAll();

    Role findRoleByRole(String role);

    RoleDTO convertToDto(Role role);

    Role findRoleById(int id);
}
