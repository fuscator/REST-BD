package ru.tokarev.spring.boot_security.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tokarev.spring.boot_security.demo.models.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findRoleByRole(String role);

    Role findRoleById(int id);
}
