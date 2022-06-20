package ru.alexey.soapapp.service;
/* 
19.06.2022: Alexey created this file inside the package: ru.alexey.soapapp.service 
*/

import localhost._8190.RoleRequest;
import org.springframework.stereotype.Service;
import ru.alexey.soapapp.entity.Role;
import ru.alexey.soapapp.repository.RoleRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role addNewRole(String name) {
        return addNewRoleOrFindRole(name);
    }

    @Override
    public List<Role> addNewRoles(List<RoleRequest> roleRequests) {
        return roleRequests.stream()
                .map(x -> addNewRoleOrFindRole(x.getRoleName()))
                .collect(Collectors.toList());
    }

    private Role addNewRoleOrFindRole(String name) {
        Optional<Role> role = roleRepository.findRoleByName(name);
        if (role.isPresent()) {
            return role.get();
        }
        Role newRole = new Role(name);
        return roleRepository.save(newRole);
    }
}
