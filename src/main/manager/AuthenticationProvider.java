package manager;

import model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import repository.UserRepository;
import request.user.LoginRequest;
import request.user.RegisterUserRequest;
import response.user.LoginResponse;
import response.user.RegisterUserResponse;

@Component
public class AuthenticationProvider
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Environment environment;

    public RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest)
    {
        String userName = registerUserRequest.getUserName();
        String password = registerUserRequest.getPassword();

        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);

        RegisterUserResponse registerUserResponse = new RegisterUserResponse();

        User registeredUser;
        try
        {
            registeredUser = userRepository.save(user);
        }
        catch(Exception e)
        {
            registerUserResponse.setSuccessful(false);
            return registerUserResponse;
        }

        registerUserResponse.setSuccessful(true);
        registerUserResponse.setUserId(String.valueOf(registeredUser.getId()));

        return registerUserResponse;
    }

    public LoginResponse loginUser(LoginRequest loginRequest)
    {
        String userName = loginRequest.getUserName();
        String password = loginRequest.getPassword();

        LoginResponse loginResponse = new LoginResponse();

        User user = userRepository.findOneByUserName(userName);

        if (user == null)
        {
            loginResponse.setSuccessful(false);
            loginResponse.setErrorMessage(environment.getProperty("user.not_found"));
        }

        else if (!user.getPassword().equals(password))
        {
            loginResponse.setSuccessful(false);
            loginResponse.setErrorMessage(environment.getProperty("user.invalid_password"));
        }

        else
        {
            loginResponse.setSuccessful(true);
        }

        return loginResponse;
    }
}
