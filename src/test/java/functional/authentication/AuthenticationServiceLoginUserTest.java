package functional.authentication;

import functional.BaseFunctionalTest;
import org.junit.Test;
import request.user.LoginRequest;
import request.user.RegisterUserRequest;
import response.GenericResponse;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class AuthenticationServiceLoginUserTest extends BaseFunctionalTest
{
    @Test
    public void loginUserWhenValidRequestIsPassed()
    {
        // GIVEN
        authenticationService.registerUser(new RegisterUserRequest(USERNAME, PASSWORD));
        LoginRequest loginRequest = new LoginRequest(USERNAME, PASSWORD);

        // WHEN
        GenericResponse genericResponse = authenticationService.login(loginRequest);

        // THEN
        assertThat(genericResponse).isNotNull();
        assertThat(genericResponse.isSuccessful()).isTrue();
        assertThat(genericResponse.getErrorMessage()).isNullOrEmpty();
    }

    @Test
    public void loginUserWhenEmptyUsernameIsPassed()
    {
        // GIVEN
        authenticationService.registerUser(new RegisterUserRequest(USERNAME, PASSWORD));
        LoginRequest loginRequest = new LoginRequest("", PASSWORD);

        // WHEN
        GenericResponse genericResponse = authenticationService.login(loginRequest);

        // THEN
        assertThat(genericResponse).isNotNull();
        assertThat(genericResponse.isSuccessful()).isFalse();
        assertThat(genericResponse.getErrorMessage()).isEqualToIgnoringCase("must not be blank");
    }

    @Test
    public void loginUserWhenEmptyPasswordIsPassed()
    {
        // GIVEN
        authenticationService.registerUser(new RegisterUserRequest(USERNAME, PASSWORD));
        LoginRequest loginRequest = new LoginRequest(USERNAME, "");

        // WHEN
        GenericResponse genericResponse = authenticationService.login(loginRequest);

        // THEN
        assertThat(genericResponse).isNotNull();
        assertThat(genericResponse.isSuccessful()).isFalse();
        assertThat(genericResponse.getErrorMessage()).isEqualToIgnoringCase("must not be blank");
    }
}
