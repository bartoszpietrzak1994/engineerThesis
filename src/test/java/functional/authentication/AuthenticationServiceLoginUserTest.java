package functional.authentication;

import functional.BaseFunctionalTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import request.user.LoginRequest;
import request.user.RegisterUserRequest;
import response.user.LoginResponse;
import service.AuthenticationService;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class AuthenticationServiceLoginUserTest extends BaseFunctionalTest
{
    @Autowired
    private AuthenticationService authenticationService;

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    @Test
    public void loginUserWhenValidRequestIsPassed()
    {
        // GIVEN
        authenticationService.registerUser(new RegisterUserRequest(USERNAME, PASSWORD));
        LoginRequest loginRequest = new LoginRequest(USERNAME, PASSWORD);

        // WHEN
        LoginResponse loginResponse = authenticationService.login(loginRequest);

        // THEN
        assertThat(loginResponse).isNotNull();
        assertThat(loginResponse.isSuccessful()).isTrue();
        assertThat(loginResponse.getErrorMessage()).isNullOrEmpty();
    }

    @Test
    public void loginUserWhenEmptyUsernameIsPassed()
    {
        // GIVEN
        authenticationService.registerUser(new RegisterUserRequest(USERNAME, PASSWORD));
        LoginRequest loginRequest = new LoginRequest("", PASSWORD);

        // WHEN
        LoginResponse loginResponse = authenticationService.login(loginRequest);

        // THEN
        assertThat(loginResponse).isNotNull();
        assertThat(loginResponse.isSuccessful()).isFalse();
        assertThat(loginResponse.getErrorMessage()).isEqualToIgnoringCase("must not be blank");
    }

    @Test
    public void loginUserWhenEmptyPasswordIsPassed()
    {
        // GIVEN
        authenticationService.registerUser(new RegisterUserRequest(USERNAME, PASSWORD));
        LoginRequest loginRequest = new LoginRequest(USERNAME, "");

        // WHEN
        LoginResponse loginResponse = authenticationService.login(loginRequest);

        // THEN
        assertThat(loginResponse).isNotNull();
        assertThat(loginResponse.isSuccessful()).isFalse();
        assertThat(loginResponse.getErrorMessage()).isEqualToIgnoringCase("must not be blank");
    }
}
