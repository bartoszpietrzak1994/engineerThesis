package integration.albumdetails;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import integration.SpringIntegrationTest;
import org.junit.Before;
import testfx.AlbumDetailsWindowTest;

public class AlbumDetailsWindowSteps extends SpringIntegrationTest
{
    private AlbumDetailsWindowTest albumDetailsWindowTest;

    @Before
    public void setUp() throws Exception
    {
        this.albumDetailsWindowTest = new AlbumDetailsWindowTest();
        this.albumDetailsWindowTest.setUp();
    }



    @Then("^I should visit album details window$")
    public void iShouldVisitAlbumDetailsWindow() throws Throwable
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^artist field should be filled with AC/DC$")
    public void artistFieldShouldBeFilledWithACDC() throws Throwable
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^title field should be filled with Random$")
    public void titleFieldShouldBeFilledWithRandom() throws Throwable
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^release date field should be filled with (\\d+)-(\\d+)-(\\d+)$")
    public void releaseDateFieldShouldBeFilledWith(int arg0, int arg1, int arg2) throws Throwable
    {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
