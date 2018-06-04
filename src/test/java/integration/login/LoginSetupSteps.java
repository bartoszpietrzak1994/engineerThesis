package integration.login;

import cucumber.api.java.en.Given;
import integration.SpringIntegrationTest;
import model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import repository.UserRepository;
import testfx.LoginWindowTest;

final public class LoginSetupSteps extends SpringIntegrationTest
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private LoginWindowTest loginWindowTest;

    @Given("^I am at login and register window$")
    public void i_am_at_login_and_register_window() throws Exception
    {
        this.loginWindowTest = new LoginWindowTest();
        this.loginWindowTest.setUp();
    }

    @Given("^the user (\\w+) is already registered$")
    public void the_user_Tomek_is_already_registered(String username) throws Exception
    {
        User user = new User();
        user.setUsername(username);
        user.setPassword(bCryptPasswordEncoder.encode("password"));

        userRepository.save(user);
    }
}
