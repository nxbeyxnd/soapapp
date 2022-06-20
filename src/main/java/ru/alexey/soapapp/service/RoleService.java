package ru.alexey.soapapp.service;
/* 
19.06.2022: Alexey created this file inside the package: ru.alexey.soapapp.service 
*/

import localhost._8190.RoleRequest;
import ru.alexey.soapapp.entity.Role;

import java.util.List;

public interface RoleService {
    Role addNewRole(String name);

    List<Role> addNewRoles(List<RoleRequest> roleRequests);
}
