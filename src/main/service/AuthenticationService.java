package service;

import model.user.User;
import request.user.LoginRequest;
import request.user.RegisterUserRequest;
import response.GenericResponse;
import response.user.RegisterUserResponse;

import javax.validation.Valid;

public interface AuthenticationService
{
    RegisterUserResponse registerUser(@Valid RegisterUserRequest registerUserRequest);
    GenericResponse login(@Valid LoginRequest loginRequest);
    User findUserByUsername(@Valid String userName);
}
