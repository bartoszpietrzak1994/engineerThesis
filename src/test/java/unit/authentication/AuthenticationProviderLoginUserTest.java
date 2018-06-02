package unit.authentication;

import manager.AuthenticationProvider;
import model.user.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.mockito.Mockito;
import request.user.LoginRequest;
import response.GenericResponse;
import validation.RequestValidator;

import java.util.HashSet;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.any;

public class AuthenticationProviderLoginUserTest extends BaseAuthenticationProviderTest
{
    @Test
    public void itReturnsGenericResponse()
    {
        // GIVEN
        Mockito.when(requestValidator.validate(any(LoginRequest.class ))).thenReturn(new HashSet<>());
        Mockito.when(userRepository.findOneByUserName(USERNAME)).thenReturn(new User(Long.valueOf(RandomStringUtils.randomNumeric
                (5)), USERNAME, PASSWORD));
        Mockito.when(bCryptPasswordEncoder.matches(PASSWORD, PASSWORD)).thenReturn(true);

        LoginRequest loginRequest = new LoginRequest(USERNAME, PASSWORD);

        // WHEN
        GenericResponse loginResponse = authenticationProvider.loginUser(loginRequest);

        // THEN
        assertThat(loginResponse).isInstanceOf(GenericResponse.class);
        assertThat(loginResponse).hasFieldOrProperty("isSuccessful");
        assertThat(loginResponse).hasFieldOrProperty("errorMessage");
    }

    @Test
    public void itValidatesLoginRequest()
    {
        // GIVEN
        this.authenticationProvider = new AuthenticationProvider(userRepository, environment, bCryptPasswordEncoder,
                new RequestValidator());

        LoginRequest loginRequest = new LoginRequest("", "");

        // WHEN
        GenericResponse loginResponse = authenticationProvider.loginUser(loginRequest);

        // THEN
        assertThat(loginResponse).isNotNull();
        assertThat(loginResponse.isSuccessful()).isFalse();
        assertThat(loginResponse.getErrorMessage()).contains("must not be");
    }

    @Test
    public void itFindsUserByUsername()
    {
        // GIVEN
        Mockito.when(requestValidator.validate(any(LoginRequest.class ))).thenReturn(new HashSet<>());
        Mockito.when(userRepository.findOneByUserName(USERNAME)).thenReturn(null);
        Mockito.when(bCryptPasswordEncoder.matches(PASSWORD, PASSWORD)).thenReturn(true);
        Mockito.when(environment.getProperty("user.not_found")).thenReturn("Requested user not found");

        LoginRequest loginRequest = new LoginRequest(USERNAME, PASSWORD);

        // WHEN
        GenericResponse genericResponse = authenticationProvider.loginUser(loginRequest);

        // THEN
        assertThat(genericResponse.isSuccessful()).isFalse();
        assertThat(genericResponse.getErrorMessage()).isEqualToIgnoringCase("Requested user not found");
    }

    @Test
    public void itChecksIfEncryptedPasswordMatchesWithGivenOne()
    {
        // GIVEN
        Mockito.when(requestValidator.validate(any(LoginRequest.class ))).thenReturn(new HashSet<>());
        Mockito.when(userRepository.findOneByUserName(USERNAME)).thenReturn(new User(Long.valueOf(RandomStringUtils.randomNumeric
                (5)), USERNAME, PASSWORD));
        Mockito.when(bCryptPasswordEncoder.matches(PASSWORD, PASSWORD)).thenReturn(false);
        Mockito.when(environment.getProperty("user.invalid_password")).thenReturn("You have provided wrong password");

        LoginRequest loginRequest = new LoginRequest(USERNAME, PASSWORD);

        // WHEN
        GenericResponse loginResponse = authenticationProvider.loginUser(loginRequest);

        // THEN
        assertThat(loginResponse.isSuccessful()).isFalse();
        assertThat(loginResponse.getErrorMessage()).isEqualToIgnoringCase("You have provided wrong password");
    }
}
