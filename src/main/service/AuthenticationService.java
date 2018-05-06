package service;

import model.user.User;
import request.user.LoginRequest;
import request.user.RegisterUserRequest;
import response.user.LoginResponse;
import response.user.RegisterUserResponse;

public interface AuthenticationService
{
    RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest);
    LoginResponse login(LoginRequest loginRequest);
    User findUserByUsername(String userName);
}
