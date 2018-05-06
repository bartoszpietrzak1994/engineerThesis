package client;

import manager.AuthenticationProvider;
import model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import request.user.LoginRequest;
import request.user.RegisterUserRequest;
import response.user.LoginResponse;
import response.user.RegisterUserResponse;
import service.AuthenticationService;

@Component
public class AuthenticationServiceImpl implements AuthenticationService
{
    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Override
    public RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest)
    {
        return authenticationProvider.registerUser(registerUserRequest);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest)
    {
        return authenticationProvider.loginUser(loginRequest);
    }

    @Override
    public User findUserByUsername(String userName)
    {
        return authenticationProvider.findUserByUsername(userName);
    }
}
