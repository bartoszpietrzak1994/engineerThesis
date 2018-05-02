package integration.mainwindow;

import cucumber.api.java.en.Given;
import integration.SpringIntegrationTest;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repository.UserRepository;

@Component
final public class MainWindowSetupSteps extends SpringIntegrationTest
{
    @Autowired
    private UserRepository userRepository;

    @Given("^I am a logged in user$")
    public void i_am_a_logged_in_user() throws Exception
    {
        User user = new User();
        user.setUserName("sample_user_name");
        user.setPassword("password");
        userRepository.save(user);
    }

    @Given("^a random album is added to collection$")
    public void a_random_album_is_added_to_collection() throws Exception
    {
        return;
    }
}
