package functional;

import com.google.common.collect.Iterables;
import config.PersistenceConfiguration;
import config.TestApplicationConfiguration;
import model.album.AlbumRating;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import repository.AlbumRepository;
import repository.UserRepository;
import request.album.AddAlbumRequest;
import request.album.FindAllUserAlbumRequest;
import service.AlbumService;
import service.AuthenticationService;

import java.time.LocalDate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestApplicationConfiguration.class, PersistenceConfiguration.class},
        loader = AnnotationConfigContextLoader.class)
public abstract class BaseFunctionalTest
{
    @Autowired
    protected AlbumRepository albumRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    protected AlbumService albumService;

    @Autowired
    protected AuthenticationService authenticationService;

    @Autowired
    protected Environment environment;

    protected static final String USERNAME = "username";
    protected static final String PASSWORD = "password";

    @Before
    public void setUp()
    {
        albumRepository.deleteAll();
        userRepository.deleteAll();
    }

    protected AddAlbumRequest prepareAddAlbumRequest()
    {
        AddAlbumRequest addAlbumRequest = new AddAlbumRequest();
        addAlbumRequest.setTitle(RandomStringUtils.randomAlphanumeric(5));
        addAlbumRequest.setArtist(RandomStringUtils.randomAlphanumeric(5));
        addAlbumRequest.setReleaseDate(LocalDate.now());
        addAlbumRequest.setUserName(USERNAME);
        addAlbumRequest.setAlbumRating(AlbumRating.TEN);

        return addAlbumRequest;
    }

    protected String getLastAlbumId()
    {
        FindAllUserAlbumRequest findAllUserAlbumRequest = new FindAllUserAlbumRequest();
        findAllUserAlbumRequest.setUserName(USERNAME);
        return Iterables.getFirst(albumService.findAllAlbumsAddedByUser(findAllUserAlbumRequest)
                .getAlbumList(), null).getAlbumId();
    }
}
