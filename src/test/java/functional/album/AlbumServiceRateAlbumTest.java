package functional.album;

import com.google.common.collect.Iterables;
import functional.BaseFunctionalTest;
import model.album.AlbumRating;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import request.album.AddAlbumRequest;
import request.album.FindAllUserAlbumRequest;
import request.album.RateAlbumRequest;
import request.user.RegisterUserRequest;
import response.GenericResponse;
import service.AlbumService;
import service.AuthenticationService;

import java.time.LocalDate;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class AlbumServiceRateAlbumTest extends BaseFunctionalTest
{
    @Autowired
    private AlbumService albumService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private Environment environment;

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    @Test
    public void testRateAlbumWhenValidRequestIsPassed()
    {
        // GIVEN
        authenticationService.registerUser(new RegisterUserRequest(USERNAME, PASSWORD));
        AddAlbumRequest addAlbumRequest = new AddAlbumRequest();
        addAlbumRequest.setUserName(USERNAME);
        addAlbumRequest.setReleaseDate(LocalDate.now());
        addAlbumRequest.setArtist("test_artist");
        addAlbumRequest.setTitle("test_title");

        albumService.addAlbum(addAlbumRequest);

        FindAllUserAlbumRequest findAllUserAlbumRequest = new FindAllUserAlbumRequest();
        findAllUserAlbumRequest.setUserName(USERNAME);
        String albumId = Iterables.getFirst(albumService.findAllAlbumsAddedByUser(findAllUserAlbumRequest)
                .getAlbumList(), null).getAlbumId();

        RateAlbumRequest rateAlbumRequest = new RateAlbumRequest();
        rateAlbumRequest.setAlbumId(albumId);
        rateAlbumRequest.setAlbumRating(AlbumRating.TEN.toString());

        // WHEN
        GenericResponse rateAlbumResponse = albumService.rateAlbum(rateAlbumRequest);

        // THEN
        assertThat(rateAlbumResponse).isNotNull();
        assertThat(rateAlbumResponse.isSuccessful()).isTrue();
        assertThat(rateAlbumResponse.getErrorMessage()).isBlank();
    }

    @Test
    public void testRateAlbumWhenThereIsNoValidAlbumIdPassed()
    {
        // GIVEN
        RateAlbumRequest rateAlbumRequest = new RateAlbumRequest();
        rateAlbumRequest.setAlbumId(RandomStringUtils.randomAlphanumeric(15));
        rateAlbumRequest.setAlbumRating(AlbumRating.TEN.toString());

        // WHEN
        GenericResponse rateAlbumResponse = albumService.rateAlbum(rateAlbumRequest);

        // THEN
        assertThat(rateAlbumResponse).isNotNull();
        assertThat(rateAlbumResponse.isSuccessful()).isFalse();
        assertThat(rateAlbumResponse.getErrorMessage()).isEqualToIgnoringCase(this.environment.getProperty("album" +
                ".unexpected_error"));
    }

    @Test
    public void testRateAlbumWhenEmptyRequestIsPassed()
    {
        // GIVEN
        RateAlbumRequest rateAlbumRequest = new RateAlbumRequest();

        // WHEN
        GenericResponse rateAlbumResponse = this.albumService.rateAlbum(rateAlbumRequest);

        // THEN
        assertThat(rateAlbumResponse).isNotNull();
        assertThat(rateAlbumResponse.isSuccessful()).isFalse();

        //todo
//        assertThat(rateAlbumResponse.getErrorMessage()).isEqualToIgnoringCase();

    }
}
