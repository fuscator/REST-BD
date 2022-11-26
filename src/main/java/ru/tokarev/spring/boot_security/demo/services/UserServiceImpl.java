package ru.tokarev.spring.boot_security.demo.services;

import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tokarev.spring.boot_security.demo.dto.UserDTO;
import ru.tokarev.spring.boot_security.demo.models.Role;
import ru.tokarev.spring.boot_security.demo.models.User;
import ru.tokarev.spring.boot_security.demo.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
    @Override
    public User findById(int id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Hibernate.initialize(user.getRoles());
            return user;
        } else {
         throw  new UsernameNotFoundException("No user with such id");
        }
    }
    @Override
    @Transactional
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> userRoles = user.getRoles().stream().map(role -> roleService.findRoleById(role.getId()))
                .collect(Collectors.toSet());
        user.setRoles(userRoles);
        userRepository.save(user);
    }
    @Override
    @Transactional
    public void changeUser(int id, User replaceUser) {
        replaceUser.setId(id);
        Optional<User> previousUser = userRepository.findById(id);
        if (replaceUser.getRoles().isEmpty()){
            previousUser.ifPresent(user -> replaceUser.setRoles(user.getRoles()));
        }
        if (replaceUser.getPassword().isEmpty()) {
            Set<Role> roles = replaceUser.getRoles().stream().map(role -> roleService.findRoleById(role.getId()))
                    .collect(Collectors.toSet());
            replaceUser.setRoles(roles);
            previousUser.ifPresent(user -> replaceUser.setPassword(user.getPassword()));
            userRepository.save(replaceUser);
        } else {
            addUser(replaceUser);
        }
    }
    @Override
    @Transactional
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByUsername(String username) {
        Optional<User> optionalUser = userRepository.findUserByUsername(username);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            Hibernate.initialize(user.getRoles());
            return user;
        }
        else
            return null;
    }

    @Override
    public UserDTO convertToDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

}
