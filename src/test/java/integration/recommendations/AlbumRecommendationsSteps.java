package integration.recommendations;

import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import integration.SpringIntegrationTest;
import testfx.AlbumRecommendationsWindowTest;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class AlbumRecommendationsSteps extends SpringIntegrationTest
{
    private AlbumRecommendationsWindowTest albumRecommendationsWindowTest;

    @Before
    public void setUp() throws Exception
    {
        this.albumRecommendationsWindowTest = new AlbumRecommendationsWindowTest();
    }

    @Then("^I should see recommendations window$")
    public void iShouldSeeRecommendationsWindow()
    {
        assertThat(this.albumRecommendationsWindowTest.isRecommendationsWindowVisible()).isTrue();
    }

    @And("^there should be (\\d+) recommendations$")
    public void thereShouldBeRecommendations(int count)
    {
        assertThat(this.albumRecommendationsWindowTest.thereAreRecommendations(count)).isTrue();
    }
}
