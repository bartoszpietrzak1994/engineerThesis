package integration.recommendations;

import cucumber.api.java.Before;
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

    @Then("^there should see (\\d+) recommendations$")
    public void thereShouldSeeRecommendations(int count)
    {
        assertThat(this.albumRecommendationsWindowTest.thereAreRecommendations(count)).isTrue();
    }
}
