package functional.authentication;

import functional.BaseFunctionalTest;
import model.user.User;
import org.junit.Test;
import request.user.RegisterUserRequest;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class AuthenticationServiceFindUserTest extends BaseFunctionalTest
{
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
        assertThat(userByUsername.getUsername()).isEqualToIgnoringCase(USERNAME);
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
