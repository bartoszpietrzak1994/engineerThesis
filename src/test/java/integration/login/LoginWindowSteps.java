package integration.login;

import cucumber.api.PendingException;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

final public class LoginWindowSteps
{
    @When("^I type (\\w+) as user name and (\\w+) as password$")
    public void i_type_user_name_and_password(String userName, String password) throws Exception
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I click Register button$")
    public void i_click_Register_button() throws Exception
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^new user (\\w+) should be created$")
    public void new_user_should_be_created(String userName) throws Exception
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I click Login button$")
    public void i_click_Login_button() throws Exception
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I should see an error message$")
    public void i_should_see_an_error_message() throws Exception
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
