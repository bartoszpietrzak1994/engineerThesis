package client;

import manager.AuthenticationProvider;
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
        return null;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest)
    {
        return null;
    }
}
