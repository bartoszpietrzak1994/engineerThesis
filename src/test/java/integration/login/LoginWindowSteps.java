package integration.login;

import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import integration.SpringIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import repository.UserRepository;
import testfx.LoginWindowTest;

import static org.assertj.core.api.Java6Assertions.assertThat;

final public class LoginWindowSteps extends SpringIntegrationTest
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Environment environment;

    private LoginWindowTest loginWindowTest;

    @Before
    public void setUp() throws Exception
    {
        this.loginWindowTest = new LoginWindowTest();
        this.loginWindowTest.setUp();
    }

    @When("^I type (\\w+) as user name and (\\w+) as password$")
    public void i_type_user_name_and_password(String userName, String password)
    {
        loginWindowTest.fillLoginFieldWithLogin(userName);
        loginWindowTest.fillPasswordFieldWithPassword(password);
    }

    @When("^I click Register button$")
    public void i_click_Register_button()
    {
        loginWindowTest.clickRegisterButton();
    }

    @When("^I click Login button$")
    public void i_click_Login_button()
    {
        loginWindowTest.clickLoginButton();
    }

    @Then("^new user (\\w+) should be created$")
    public void new_user_should_be_created(String userName)
    {
        assertThat(userRepository.findOneByUsername(userName)).isNotNull();
    }

    @Then("^I should be notified that user (\\w+) already exists$")
    public void i_should_see_an_error_message(String username)
    {
        assertThat(loginWindowTest.messageShouldBeVisible(
                String.format(environment.getProperty("user.already_registered"), username))).isTrue();
    }
}
