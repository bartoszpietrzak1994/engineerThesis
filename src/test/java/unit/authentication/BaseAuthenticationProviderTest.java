package unit.authentication;

import manager.AuthenticationProvider;
import org.junit.Before;
import org.mockito.Mock;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import repository.UserRepository;
import unit.BaseUnitTest;
import validation.RequestValidator;

import static org.mockito.Mockito.mock;

public abstract class BaseAuthenticationProviderTest extends BaseUnitTest
{
    protected static final String USERNAME = "username";
    protected static final String PASSWORD = "password";

    protected UserRepository userRepository;

    protected Environment environment;

    protected BCryptPasswordEncoder bCryptPasswordEncoder;

    protected AuthenticationProvider authenticationProvider;

    protected RequestValidator requestValidator;

    @Before
    public void setUp()
    {
        this.userRepository = mock(UserRepository.class);
        this.environment = mock(Environment.class);
        this.bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);
        this.requestValidator = mock(RequestValidator.class);
        this.authenticationProvider = new AuthenticationProvider(userRepository, environment, bCryptPasswordEncoder,
                requestValidator);
    }
}
