package integration.mainwindow;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testfx.framework.junit.ApplicationTest;
import repository.UserRepository;
import testfx.MainWindowTest;

import static org.assertj.core.api.Java6Assertions.assertThat;

final public class MainWindowSteps extends ApplicationTest
{
    @Autowired
    private UserRepository userRepository;

    private MainWindowTest mainWindowTest;

    @Before
    public void setUp() throws Exception
    {
        userRepository.deleteAll();
        userRepository.flush();
        this.mainWindowTest = new MainWindowTest();
    }

    @After
    public void tearDown() throws Exception
    {
        this.mainWindowTest.tearDown();
    }

    /**
     * Button steps
     */
    @When("^I click Add Album button$")
    public void i_click_Add_Album_button() throws Exception
    {
        this.mainWindowTest.clickAddAlbumButton();
    }

    @When("^I click Rate button$")
    public void i_click_Rate_button() throws Exception
    {
        this.mainWindowTest.clickRateButton();
    }

    @When("^I click Get Recommendations button$")
    public void i_click_Get_Recommendations_button() throws Exception
    {
        this.mainWindowTest.clickGetRecommendationsButton();
    }

    @When("^I click Details button$")
    public void i_click_Details_button() throws Exception
    {
        this.mainWindowTest.clickDetailsButton();
    }

    /**
     * Window steps
     */
    @Given("^I visit the main application window$")
    @Then("^I should visit the main application window$")
    public void i_visit_the_main_application_window() throws Exception
    {
        assertThat(this.mainWindowTest.isMainApplicationWindowCurrentWindow()).isTrue();
    }

    @Then("^the Add Album window should appear$")
    public void the_Add_Album_window_should_appear() throws Exception
    {
        this.mainWindowTest.addAlbumWindowShouldAppear();
    }

    @Then("^I the Rate Album window should appear$")
    public void i_the_Rate_Album_window_should_appear() throws Exception
    {
        this.mainWindowTest.rateAlbumWindowShouldAppear();
    }

    @Then("^I the Album Details window should appear$")
    public void i_the_Album_Details_window_should_appear() throws Exception
    {
        this.mainWindowTest.albumDetailsWindowShouldAppear();
    }

    /**
     * Other controls
     */
    @When("^I choose the first album from my collection$")
    public void i_choose_the_first_album_from_my_collection() throws Exception
    {
        throw new PendingException();
    }

    @Then("^I should receive the list of albums related to these from my collection$")
    public void i_should_receive_the_list_of_albums_related_to_these_from_my_collection() throws Exception
    {
        throw new PendingException();
    }

    @When("^I select an album from my collection$")
    public void i_select_an_album_from_my_collection() throws Exception
    {
        throw new PendingException();
    }

    @Then("^I should be able to see my album collection$")
    public void i_should_be_able_to_see_my_album_collection() throws Exception
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}