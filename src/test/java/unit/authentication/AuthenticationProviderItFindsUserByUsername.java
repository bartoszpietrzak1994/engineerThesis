package unit.authentication;

import model.user.User;
import org.junit.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class AuthenticationProviderItFindsUserByUsername extends BaseAuthenticationProviderTest
{
    @Test
    public void itReturnsUserEntity()
    {
        // GIVEN
        Mockito.when(userRepository.findOneByUsername(USERNAME)).thenReturn(new User());

        // WHEN
        User userByUsername = authenticationProvider.findUserByUsername(USERNAME);

        // THEN
        assertThat(userByUsername).isInstanceOf(User.class);
        assertThat(userByUsername).hasFieldOrProperty("userId");
        assertThat(userByUsername).hasFieldOrProperty("username");
        assertThat(userByUsername).hasFieldOrProperty("password");
    }
}
