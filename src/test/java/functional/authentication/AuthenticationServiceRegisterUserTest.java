package functional.authentication;

import functional.BaseFunctionalTest;
import model.user.User;
import org.junit.Test;
import request.user.RegisterUserRequest;
import response.user.RegisterUserResponse;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class AuthenticationServiceRegisterUserTest extends BaseFunctionalTest
{
    @Test
    public void registerUserWhenValidRequestIsPassed()
    {
        // GIVEN
        RegisterUserRequest registerUserRequest = new RegisterUserRequest(USERNAME, PASSWORD);

        // WHEN
        RegisterUserResponse registerUserResponse = authenticationService.registerUser(registerUserRequest);

        // THEN
        assertThat(registerUserResponse).isNotNull();
        assertThat(registerUserResponse.isSuccessful()).isTrue();
        assertThat(registerUserResponse.getErrorMessage()).isNullOrEmpty();

        User userByUsername = authenticationService.findUserByUsername(USERNAME);
        assertThat(userByUsername).isNotNull();
        assertThat(userByUsername.getUserName()).isEqualTo(USERNAME);
    }

    @Test
    public void registerUserWhenUserWithTheSameUsernameIsAlreadyRegistered()
    {
        // GIVEN
        RegisterUserRequest registerUserRequest = new RegisterUserRequest(USERNAME, PASSWORD);
        authenticationService.registerUser(registerUserRequest);

        // WHEN
        RegisterUserResponse registerUserResponse = authenticationService.registerUser(registerUserRequest);

        // THEN
        assertThat(registerUserResponse).isNotNull();
        assertThat(registerUserResponse.isSuccessful()).isFalse();
        assertThat(registerUserResponse.getErrorMessage()).isEqualToIgnoringCase(String.format("User with username %s" +
                " has already been registered.", USERNAME));
    }

    @Test
    public void registerUserWhenEmptyUsernameIsPassed()
    {
        // GIVEN
        RegisterUserRequest registerUserRequest = new RegisterUserRequest("", PASSWORD);

        // WHEN
        RegisterUserResponse registerUserResponse = authenticationService.registerUser(registerUserRequest);

        // THEN
        assertThat(registerUserResponse).isNotNull();
        assertThat(registerUserResponse.isSuccessful()).isFalse();
        assertThat(registerUserResponse.getErrorMessage()).isEqualToIgnoringCase("must not be blank");
    }

    @Test
    public void registerUserWhenEmptyPasswordIsPassed()
    {
        // GIVEN
        RegisterUserRequest registerUserRequest = new RegisterUserRequest(USERNAME, "");

        // WHEN
        RegisterUserResponse registerUserResponse = authenticationService.registerUser(registerUserRequest);

        // THEN
        assertThat(registerUserResponse).isNotNull();
        assertThat(registerUserResponse.isSuccessful()).isFalse();
        assertThat(registerUserResponse.getErrorMessage()).isEqualToIgnoringCase("must not be blank");
    }
}
