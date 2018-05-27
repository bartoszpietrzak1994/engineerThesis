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
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Environment environment;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RequestValidator requestValidator;

    public RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest)
    {
        RegisterUserResponse registerUserResponse = new RegisterUserResponse();

        Set<ConstraintViolation<RegisterUserRequest>> validationErrors = this.requestValidator.validate
                (registerUserRequest);

        if (!validationErrors.isEmpty())
        {
            registerUserResponse.setSuccessful(false);
            registerUserResponse.setErrorMessage(Iterables.getFirst(validationErrors, null).getMessage());
            return  registerUserResponse;
        }

        String userName = registerUserRequest.getUserName();
        String password = registerUserRequest.getPassword();

        User user = new User();
        user.setUserName(userName);
        user.setPassword(bCryptPasswordEncoder.encode(password));

        User userByUsername = userRepository.findOneByUserName(userName);

        if (userByUsername != null)
        {
            registerUserResponse.setSuccessful(false);
            registerUserResponse.setErrorMessage(String.format("User with username %s has already been registered.",
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
            registerUserResponse.setErrorMessage(e.getMessage());
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

        User user = userRepository.findOneByUserName(userName);

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
        return userRepository.findOneByUserName(userName);
    }
}
