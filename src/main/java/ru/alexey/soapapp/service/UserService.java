package ru.alexey.soapapp.service;/* 
18.06.2022: Alexey created this file inside the package: ru.alexey.soapapp.service 
*/

import localhost._8190.UserRequest;
import ru.alexey.soapapp.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAllUsers();

    User findUserById(String login);

    void removeUserById(String login);

    void addNewUser(UserRequest user);

    void updateUser(UserRequest userRequest);
}
