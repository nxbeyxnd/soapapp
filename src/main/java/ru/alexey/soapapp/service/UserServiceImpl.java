package ru.alexey.soapapp.service;
/* 
19.06.2022: Alexey created this file inside the package: ru.alexey.soapapp.service 
*/

import localhost._8190.UserRequest;
import org.springframework.stereotype.Service;
import ru.alexey.soapapp.entity.Role;
import ru.alexey.soapapp.entity.User;
import ru.alexey.soapapp.exception.EntityNotFoundException;
import ru.alexey.soapapp.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(String login) {
        return userRepository.findById(login).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with login (%s) doesn't exists", login)));
    }

    @Override
    public void removeUserById(String login) {
        userRepository.delete(
                userRepository.findById(login)
                        .orElseThrow(
                                () -> new EntityNotFoundException(String.format("User with login (%s) doesn't exists", login))));
    }

    @Override
    public void addNewUser(UserRequest user) {
        if (!userRepository.findById(user.getLogin()).isPresent()) {
            List<Role> roles = roleService.addNewRoles(user.getRoles());
            User newUser = new User(user.getLogin(), user.getPassword(), roles);
            userRepository.save(newUser);
        }
    }

    @Override
    public void updateUser(UserRequest request) {
        User user = userRepository.findById(request.getLogin()).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with login (%s) doesn't exists", request.getLogin())));
        List<Role> roles = roleService.addNewRoles(request.getRoles());
        user.getRoles().addAll(roles);
        user.setPassword(request.getPassword());
        userRepository.save(user);
    }
}
