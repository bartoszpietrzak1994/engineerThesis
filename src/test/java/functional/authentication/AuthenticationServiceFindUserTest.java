package functional.authentication;

import functional.BaseFunctionalTest;
import model.user.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import request.user.RegisterUserRequest;
import service.AuthenticationService;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class AuthenticationServiceFindUserTest extends BaseFunctionalTest
{
    @Autowired
    private AuthenticationService authenticationService;

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    @Test
    public void findUserWhenItsAlreadyRegistered()
    {
        // GIVEN
        authenticationService.registerUser(new RegisterUserRequest(USERNAME, PASSWORD));

        // WHEN
        User userByUsername = authenticationService.findUserByUsername(USERNAME);

        // THEN
        assertThat(userByUsername).isNotNull();
        assertThat(userByUsername.getPassword()).isNotEmpty();
        assertThat(userByUsername.getUserName()).isEqualToIgnoringCase(USERNAME);
    }

    @Test
    public void findUserWhenItsNotRegisteredYet()
    {
        // WHEN
        User userByUsername = authenticationService.findUserByUsername(USERNAME);

        // THEN
        assertThat(userByUsername).isNull();
    }
}
