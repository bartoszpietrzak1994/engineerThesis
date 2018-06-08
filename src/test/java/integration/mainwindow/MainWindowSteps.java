package integration.mainwindow;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import integration.SpringIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import repository.AlbumRepository;
import repository.UserRepository;
import testfx.MainWindowTest;

import static org.assertj.core.api.Java6Assertions.assertThat;

final public class MainWindowSteps extends SpringIntegrationTest
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AlbumRepository albumRepository;

    private MainWindowTest mainWindowTest;

    @Before
    public void beforeAll()
    {
        albumRepository.deleteAll();
        albumRepository.flush();

        userRepository.deleteAll();
        userRepository.flush();

        this.mainWindowTest = new MainWindowTest();
    }

    /**
     * Button steps
     */
    @When("^I click Add Album button$")
    public void i_click_Add_Album_button()
    {
        this.mainWindowTest.clickAddAlbumButton();
    }

    @When("^I click Rate button$")
    public void i_click_Rate_button()
    {
        this.mainWindowTest.clickRateButton();
    }

    @When("^I choose (\\w+) rating option$")
    public void i_choose_rating_option(String ratingOption)
    {
        this.mainWindowTest.chooseRatingOption(ratingOption);
    }

    @Then("^the album should be rated with (\\w+)$")
    public void the_album_should_be_rated_with(String ratingOption)
    {
        this.mainWindowTest.albumShouldBeRatedWith(ratingOption);
    }

    @When("^I click Get Recommendations button$")
    public void i_click_Get_Recommendations_button()
    {
        this.mainWindowTest.clickGetRecommendationsButton();
    }

    @When("^I click Details button$")
    public void i_click_Details_button()
    {
        this.mainWindowTest.clickDetailsButton();
    }

    /**
     * Window steps
     */
    @Given("^I visit the main application window$")
    @Then("^I should visit the main application window$")
    public void i_visit_the_main_application_window()
    {
        assertThat(this.mainWindowTest.isMainApplicationWindowCurrentWindow()).isTrue();
    }

    @Then("^the Add Album window should appear$")
    public void the_Add_Album_window_should_appear()
    {
        this.mainWindowTest.addAlbumWindowShouldAppear();
    }

    @Then("^I the Rate Album window should appear$")
    public void i_the_Rate_Album_window_should_appear()
    {
        this.mainWindowTest.rateAlbumWindowShouldAppear();
    }

    @Then("^the Album Details window should appear$")
    public void the_Album_Details_window_should_appear()
    {
        this.mainWindowTest.albumDetailsWindowShouldAppear();
    }

    /**
     * Other controls
     */
    @When("^I choose the first album from my collection$")
    public void i_choose_the_first_album_from_my_collection()
    {
        this.mainWindowTest.selectFirstAlbum();
    }

    @Then("^I should receive the list of albums related to these from my collection$")
    public void i_should_receive_the_list_of_albums_related_to_these_from_my_collection()
    {
        return;
    }

    @Then("^I should be able to see my album collection$")
    public void i_should_be_able_to_see_my_album_collection()
    {
        assertThat(this.mainWindowTest.albumCollectionIsVisible()).isTrue();
    }
}