package ru.tokarev.spring.boot_security.demo.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tokarev.spring.boot_security.demo.dto.RoleDTO;
import ru.tokarev.spring.boot_security.demo.models.Role;
import ru.tokarev.spring.boot_security.demo.repositories.RoleRepository;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findRoleByRole(String role) {
        return roleRepository.findRoleByRole(role);
    }
    @Override
    public RoleDTO convertToDto(Role role) {
        return modelMapper.map(role, RoleDTO.class);
    }

    @Override
    public Role findRoleById(int id) {
        return roleRepository.findRoleById(id);
    }
}
