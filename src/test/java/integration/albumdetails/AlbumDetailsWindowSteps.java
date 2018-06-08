package integration.albumdetails;

import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import integration.SpringIntegrationTest;
import testfx.AlbumDetailsWindowTest;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class AlbumDetailsWindowSteps extends SpringIntegrationTest
{
    private AlbumDetailsWindowTest albumDetailsWindowTest;

    @Before
    public void setUp()
    {
        this.albumDetailsWindowTest = new AlbumDetailsWindowTest();
    }

    @And("^artist field should be filled with (.*)$")
    public void artistFieldShouldBeFilledWith(String artist)
    {
        assertThat(this.albumDetailsWindowTest.isArtistFieldFilledWith(artist)).isTrue();
    }

    @And("^title field should be filled with (.*)$")
    public void titleFieldShouldBeFilledWith(String title)
    {
        assertThat(this.albumDetailsWindowTest.isTitleFieldFilledWith(title)).isTrue();
    }

    @And("^release date field should be filled with (.*)$")
    public void releaseDateFieldShouldBeFilledWith(String releaseDate)
    {
        assertThat(this.albumDetailsWindowTest.isReleaseDateFieldFilledWith(releaseDate)).isTrue();
    }
}
