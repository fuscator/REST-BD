package ru.tokarev.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.tokarev.spring.boot_security.demo.models.Role;
import ru.tokarev.spring.boot_security.demo.models.User;
import ru.tokarev.spring.boot_security.demo.services.RoleService;
import ru.tokarev.spring.boot_security.demo.services.UserService;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class InitializeUsers implements ApplicationRunner {
    private final UserService userService;
    private final RoleService roleService;
    @Autowired
    public InitializeUsers(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {

        Role adminRole = new Role("ROLE_ADMIN");
        Role userRole = new Role("ROLE_USER");
        Role guestRole = new Role("ROLE_GUEST");

        final Set<Role> roles1 = new HashSet<>(List.of(adminRole, userRole));
        final Set<Role> roles2 = new HashSet<>(List.of(userRole, guestRole));
        final Set<Role> roles3 = new HashSet<>(List.of(guestRole));

        final User admin = new User("admin", "admin", (byte)26, "St.Pt", "admin@mail.com", "admin", roles1);
        final User user = new User("user", "user", (byte)18, "Moscow", "user@mail.com", "user", roles2);
        final User guest = new User("guest", "guest", (byte)45, "Madrid", "guest@mail.com", "guest", roles3);

        roleService.save(adminRole);
        roleService.save(userRole);
        roleService.save(guestRole);
        userService.addUser(admin);
        userService.addUser(user);
        userService.addUser(guest);
    }
}
