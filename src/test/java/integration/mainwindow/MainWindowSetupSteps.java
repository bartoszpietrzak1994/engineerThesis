package integration.mainwindow;

import cucumber.api.java.en.Given;
import integration.SpringIntegrationTest;
import model.album.Album;
import model.album.AlbumRating;
import model.user.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import repository.AlbumRepository;
import repository.UserRepository;
import testfx.LoginWindowTest;

import java.sql.Date;
import java.time.LocalDate;

final public class MainWindowSetupSteps extends SpringIntegrationTest
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AlbumRepository albumRepository;

    private LoginWindowTest loginWindowTest;

    @Given("^a random album is added to collection$")
    public void a_random_album_is_added_to_collection()
    {
        Album album = new Album();
        album.setReleaseDate(Date.valueOf(LocalDate.now()));
        album.setRatingDate(Date.valueOf(LocalDate.now()));
        album.setUser(userRepository.findOneByUsername("Tomek"));
        album.setAlbumRating(AlbumRating.TEN);
        album.setArtist(RandomStringUtils.randomAlphabetic(5));
        album.setTitle(RandomStringUtils.randomAlphabetic(5));

        albumRepository.save(album);
    }
}

