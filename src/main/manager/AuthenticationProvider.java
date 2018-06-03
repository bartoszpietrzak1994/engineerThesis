package manager;

import com.google.common.collect.Iterables;
import model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import repository.UserRepository;
import request.user.LoginRequest;
import request.user.RegisterUserRequest;
import response.GenericResponse;
import response.user.RegisterUserResponse;
import validation.RequestValidator;

import javax.validation.ConstraintViolation;
import java.util.Set;

@Component
public class AuthenticationProvider
{
    private UserRepository userRepository;
    private Environment environment;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private RequestValidator requestValidator;

    @Autowired
    public AuthenticationProvider(UserRepository userRepository, Environment environment, BCryptPasswordEncoder
            bCryptPasswordEncoder, RequestValidator requestValidator)
    {
        this.userRepository = userRepository;
        this.environment = environment;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.requestValidator = requestValidator;
    }

    public RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest)
    {
        RegisterUserResponse registerUserResponse = new RegisterUserResponse();

        Set<ConstraintViolation<RegisterUserRequest>> validationErrors = this.requestValidator.validate
                (registerUserRequest);

        if (!validationErrors.isEmpty())
        {
            registerUserResponse.setSuccessful(false);
            registerUserResponse.setErrorMessage(Iterables.getFirst(validationErrors, null).getMessage());
            return registerUserResponse;
        }

        String userName = registerUserRequest.getUserName();
        String password = registerUserRequest.getPassword();

        User user = new User();
        user.setUsername(userName);
        user.setPassword(bCryptPasswordEncoder.encode(password));

        User userByUsername = userRepository.findOneByUsername(userName);

        if (userByUsername != null)
        {
            registerUserResponse.setSuccessful(false);
            registerUserResponse.setErrorMessage(String.format(environment.getProperty("user.already_registered"),
                    userName));
            return registerUserResponse;
        }

        User registeredUser;
        try
        {
            registeredUser = userRepository.save(user);
        }
        catch (Exception e)
        {
            registerUserResponse.setSuccessful(false);
            registerUserResponse.setErrorMessage(environment.getProperty("user.unexpected_error"));
            return registerUserResponse;
        }

        return new RegisterUserResponse(String.valueOf(registeredUser.getUserId()));
    }

    public GenericResponse loginUser(LoginRequest loginRequest)
    {
        GenericResponse loginResponse = new GenericResponse();

        Set<ConstraintViolation<LoginRequest>> validationErrors = this.requestValidator.validate(loginRequest);

        if (!validationErrors.isEmpty())
        {
            loginResponse.setSuccessful(false);
            loginResponse.setErrorMessage(Iterables.getFirst(validationErrors, null).getMessage());
            return loginResponse;
        }

        String userName = loginRequest.getUserName();
        String password = loginRequest.getPassword();

        User user = userRepository.findOneByUsername(userName);

        if (user == null)
        {
            loginResponse.setSuccessful(false);
            loginResponse.setErrorMessage(environment.getProperty("user.not_found"));
        }

        else if (!bCryptPasswordEncoder.matches(password, user.getPassword()))
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

    public User findUserByUsername(String userName)
    {
        return userRepository.findOneByUsername(userName);
    }
}
