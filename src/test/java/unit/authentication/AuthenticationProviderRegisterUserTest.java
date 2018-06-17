package unit.authentication;

import manager.AuthenticationProvider;
import model.user.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.mockito.Mockito;
import request.user.RegisterUserRequest;
import response.GenericResponse;
import response.user.RegisterUserResponse;
import validation.RequestValidator;

import java.util.HashSet;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AuthenticationProviderRegisterUserTest extends BaseAuthenticationProviderTest
{
    @Test
    public void itReturnsRegisterUserResponse()
    {
        // GIVEN
        Mockito.when(requestValidator.validate(any(RegisterUserRequest.class))).thenReturn(new HashSet<>());
        Mockito.when(bCryptPasswordEncoder.encode(any(String.class))).thenReturn(RandomStringUtils.randomAlphanumeric(5));
        Mockito.when(userRepository.findOneByUsername(any(String.class))).thenReturn(null);
        Mockito.when(userRepository.save(any(User.class))).thenReturn(new User(Long.valueOf(RandomStringUtils.randomNumeric(5)),
                USERNAME, PASSWORD));

        RegisterUserRequest registerUserRequest = new RegisterUserRequest(USERNAME, PASSWORD);

        // WHEN
        RegisterUserResponse registerUserResponse = authenticationProvider.registerUser(registerUserRequest);

        // THEN
        assertThat(registerUserResponse).isInstanceOf(RegisterUserResponse.class);
        assertThat(registerUserResponse).isInstanceOf(GenericResponse.class);
        assertThat(registerUserResponse).hasFieldOrProperty("userId");
        assertThat(registerUserResponse).hasFieldOrProperty("isSuccessful");
        assertThat(registerUserResponse).hasFieldOrProperty("errorMessage");
    }

    @Test
    public void itValidatesRegisterUserRequest()
    {
        // GIVEN
        this.authenticationProvider = new AuthenticationProvider(userRepository, environment, bCryptPasswordEncoder,
                new RequestValidator());
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();

        // WHEN
        RegisterUserResponse registerUserResponse = authenticationProvider.registerUser(registerUserRequest);

        // THEN
        assertThat(registerUserResponse.isSuccessful()).isFalse();
        assertThat(registerUserResponse.getErrorMessage()).contains("must not be");
        assertThat(registerUserResponse.getUserId()).isBlank();
    }

    @Test
    public void itEncodesUserPassword()
    {
        // GIVEN
        Mockito.when(requestValidator.validate(any(RegisterUserRequest.class))).thenReturn(new HashSet<>());
        Mockito.when(bCryptPasswordEncoder.encode(any(String.class))).thenReturn(RandomStringUtils.randomAlphanumeric(5));
        Mockito.when(userRepository.findOneByUsername(any(String.class))).thenReturn(null);
        Mockito.when(userRepository.save(any(User.class))).thenReturn(new User(Long.valueOf(RandomStringUtils.randomNumeric(5)),
                USERNAME, PASSWORD));

        RegisterUserRequest registerUserRequest = new RegisterUserRequest(USERNAME, PASSWORD);

        // WHEN
        authenticationProvider.registerUser(registerUserRequest);

        // THEN
        verify(bCryptPasswordEncoder, times(1)).encode(any(String.class));
    }

    @Test
    public void itLooksForPreviouslyRegisteredUser()
    {
        // GIVEN
        Mockito.when(requestValidator.validate(any(RegisterUserRequest.class))).thenReturn(new HashSet<>());
        Mockito.when(bCryptPasswordEncoder.encode(any(String.class))).thenReturn(RandomStringUtils.randomAlphanumeric(5));
        Mockito.when(userRepository.findOneByUsername(any(String.class))).thenReturn(new User(Long.valueOf(RandomStringUtils.randomNumeric(5)),
                USERNAME, PASSWORD));
        Mockito.when(environment.getProperty("user.already_registered")).thenReturn(String.format("User with username %s" +
                " has already been registered.", USERNAME));

        RegisterUserRequest registerUserRequest = new RegisterUserRequest(USERNAME, PASSWORD);

        // WHEN
        RegisterUserResponse registerUserResponse = authenticationProvider.registerUser(registerUserRequest);

        // THEN
        assertThat(registerUserResponse.isSuccessful()).isFalse();
        assertThat(registerUserResponse.getErrorMessage()).isEqualToIgnoringCase(String.format("User with username %s" +
                " has already been registered.", USERNAME));
        assertThat(registerUserResponse.getUserId()).isBlank();
    }

    @Test
    public void itReturnsErrorMessageWhenSavingUserToDatabaseFails()
    {
        // GIVEN
        Mockito.when(requestValidator.validate(any(RegisterUserRequest.class))).thenReturn(new HashSet<>());
        Mockito.when(bCryptPasswordEncoder.encode(any(String.class))).thenReturn(RandomStringUtils.randomAlphanumeric(5));
        Mockito.when(userRepository.findOneByUsername(any(String.class))).thenReturn(null);
        Mockito.when(userRepository.save(any(User.class))).thenThrow(new RuntimeException());
        Mockito.when(environment.getProperty("user.unexpected_error")).thenReturn("An unexpected error occurred");

        RegisterUserRequest registerUserRequest = new RegisterUserRequest(USERNAME, PASSWORD);

        // WHEN
        RegisterUserResponse registerUserResponse = authenticationProvider.registerUser(registerUserRequest);

        // THEN
        assertThat(registerUserResponse.isSuccessful()).isFalse();
        assertThat(registerUserResponse.getErrorMessage()).isEqualToIgnoringCase("An unexpected error occurred");
        assertThat(registerUserResponse.getUserId()).isNull();
    }
}
