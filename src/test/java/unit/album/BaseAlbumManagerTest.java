package unit.album;

import manager.AlbumManager;
import model.album.Album;
import model.album.AlbumRating;
import model.user.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import repository.AlbumRepository;
import service.AuthenticationService;
import unit.BaseUnitTest;
import util.album.AlbumSortingMethodResolver;
import validation.RequestValidator;

import java.sql.Date;
import java.time.LocalDate;

import static org.mockito.Mockito.mock;

public class BaseAlbumManagerTest extends BaseUnitTest
{
    @Mock
    protected AlbumRepository albumRepository;

    @Mock
    protected AlbumManager albumManager;

    @Mock
    protected AuthenticationService authenticationService;

    @Mock
    protected AlbumSortingMethodResolver albumSortingMethodResolver;

    @Mock
    protected RequestValidator requestValidator;

    @Mock
    protected Environment environment;

    @Before
    public void setUp()
    {
        this.albumRepository = mock(AlbumRepository.class);
        this.authenticationService = mock(AuthenticationService.class);
        this.albumSortingMethodResolver = mock(AlbumSortingMethodResolver.class);
        this.requestValidator = mock(RequestValidator.class);
        this.environment = mock(Environment.class);
        this.albumManager = mock(AlbumManager.class);
        MockitoAnnotations.initMocks(this);

        this.albumManager = new AlbumManager(albumRepository, authenticationService, albumSortingMethodResolver,
                requestValidator, environment);
    }

    protected Album prepareAlbum()
    {
        Album album = new Album();
        album.setTitle(RandomStringUtils.randomAlphanumeric(5));
        album.setArtist(RandomStringUtils.randomAlphanumeric(5));
        album.setAlbumRating(AlbumRating.TEN);
        album.setRatingDate(new Date(LocalDate.now().toEpochDay()));
        album.setReleaseDate(new Date(LocalDate.now().toEpochDay()));
        album.setUser(new User());
        album.setAlbumCover(new byte[1]);
        album.setAlbumId(Long.valueOf(RandomStringUtils.randomNumeric(3)));

        return album;
    }
}
