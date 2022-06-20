package ru.alexey.soapapp.endpoint;
/* 
19.06.2022: Alexey created this file inside the package: ru.alexey.soapapp.endpoint 
*/

import localhost._8190.*;
import localhost._8190.Error;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.alexey.soapapp.entity.Role;
import ru.alexey.soapapp.entity.User;
import ru.alexey.soapapp.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Endpoint
public class UserResourceEndpoint {
    private static final String NAMESPACE_URL = "http://localhost:8190/";

    private final UserService userService;

    public UserResourceEndpoint(UserService userService) {
        this.userService = userService;
    }

    @PayloadRoot(namespace = NAMESPACE_URL, localPart = "getAllUsersRequest")
    @ResponsePayload
    public GetAllUsersResponse getAllUsers(@RequestPayload GetAllUsersRequest request) {
        GetAllUsersResponse response = new GetAllUsersResponse();
        response.getUsers().addAll(mapUsersToUserResponseList(userService.findAllUsers()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URL, localPart = "getUserByIdRequest")
    @ResponsePayload
    public GetUserByIdResponse getUserById(@RequestPayload GetUserByIdRequest request) {
        GetUserByIdResponse response = new GetUserByIdResponse();
        response.setUser(mapUserToUserResponse(userService.findUserById(request.getId())));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URL, localPart = "deleteUserByLoginRequest")
    @ResponsePayload
    public DeleteUserByLoginResponse deleteUserByLogin(@RequestPayload DeleteUserByLoginRequest request) {
        DeleteUserByLoginResponse response = new DeleteUserByLoginResponse();
        userService.removeUserById(request.getLogin());
        response.setSuccess(true);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URL, localPart = "addNewUserRequest")
    @ResponsePayload
    public AddNewUserResponse addNewUser(@RequestPayload AddNewUserRequest request) {
        AddNewUserResponse response = new AddNewUserResponse();
        response.getErrors().addAll(checkUserOnValid(request.getNewUser()));
        if (response.getErrors().isEmpty()) {
            response.setSuccess(true);
            userService.addNewUser(request.getNewUser());
            return response;
        } else response.setSuccess(false);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URL, localPart = "updateUserRequest")
    @ResponsePayload
    public UpdateUserResponse updateUser(@RequestPayload UpdateUserRequest request) {
        UpdateUserResponse response = new UpdateUserResponse();
        response.getErrors().addAll(checkUserOnValid(request.getNewUser()));
        if (response.getErrors().isEmpty()) {
            response.setSuccess(true);
            userService.updateUser(request.getNewUser());
        } else response.setSuccess(false);
        return response;
    }

    private List<Error> checkUserOnValid(UserRequest newUser) {
        List<Error> errors = new ArrayList<>();
        if (newUser.getLogin().length() <= 0) {
            Error error = new Error();
            error.setError("Login too short");
            errors.add(error);
        }
        if (newUser.getPassword().length() <= 0) {
            Error error = new Error();
            error.setError("Password too short");
            errors.add(error);
        }
        if (!newUser.getPassword().matches(".*[A-Z]+.*")) {
            Error error = new Error();
            error.setError("add uppercase letter to ur password");
            errors.add(error);
        }
        if (!newUser.getPassword().matches(".*[0-9]+.*")) {
            Error error = new Error();
            error.setError("add number to ur password");
            errors.add(error);
        }
        return errors;
    }

    private List<UserResponse> mapUsersToUserResponseList(List<User> users) {
        return users.stream().map(u -> {
            UserResponse userResponse = new UserResponse();
            userResponse.setLogin(u.getLogin());
            return userResponse;
        }).collect(Collectors.toList());
    }

    private UserResponse mapUserToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setLogin(user.getLogin());
        userResponse.getRoles().addAll(
                user.getRoles()
                        .stream()
                        .map(this::mapRoleToRoleResponse)
                        .collect(Collectors.toList()));
        return userResponse;
    }

    private RoleResponse mapRoleToRoleResponse(Role role) {
        RoleResponse response = new RoleResponse();
        response.setRoleName(role.getName());
        return response;
    }
}
