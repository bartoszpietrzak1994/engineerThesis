package integration.addalbum;

import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import integration.SpringIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import repository.AlbumRepository;
import testfx.AddAlbumWindowTest;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class AddAlbumWindowSteps extends SpringIntegrationTest
{
    @Autowired
    private AlbumRepository albumRepository;

    private AddAlbumWindowTest addAlbumWindowTest;

    @Before
    public void setUp()
    {
        this.addAlbumWindowTest = new AddAlbumWindowTest();
    }

    @When("^I fill album details with valid random data$")
    public void iFillAlbumDetailsWithValidRandomData()
    {
        this.addAlbumWindowTest.fillArtistField();
        this.addAlbumWindowTest.fillTitleField();
        this.addAlbumWindowTest.fillReleaseDate();
    }

    @When("^I fill artist field with (.*)$")
    public void iFillArtistFieldWith(String artist)
    {
        this.addAlbumWindowTest.fillArtistField(artist);
    }

    @When("^I fill title field with (.*)")
    public void iFillTitleFieldWith(String title)
    {
        this.addAlbumWindowTest.fillTitleField(title);
    }

    @When("^I fill release date field with (.*)")
    public void iFillReleaseDateFieldWith(String releaseDate)
    {
        this.addAlbumWindowTest.fillReleaseDate(releaseDate);
    }

    @When("^I click Add button$")
    public void iClickAddButton()
    {
        this.addAlbumWindowTest.clickAddButton();
    }

    @Then("^new album should be added$")
    public void newAlbumShouldBeAdded()
    {
        assertThat(albumRepository.findAll().size()).isEqualTo(1);
    }

    @Then("^new album should not be added$")
    public void newAlbumShouldNotBeAdded()
    {
        assertThat(albumRepository.findAll()).isEmpty();
    }

    @Then("^I should see an error message$")
    public void iShouldSeeAnErrorMessage()
    {
        assertThat(this.addAlbumWindowTest.isErrorMessageVisible()).isTrue();
    }
}
