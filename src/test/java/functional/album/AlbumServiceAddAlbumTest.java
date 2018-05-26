package functional.album;

import functional.BaseFunctionalTest;
import model.album.AlbumRating;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import request.album.AddAlbumRequest;
import request.user.RegisterUserRequest;
import response.album.AddAlbumResponse;
import service.AlbumService;
import service.AuthenticationService;

import java.time.LocalDate;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class AlbumServiceAddAlbumTest extends BaseFunctionalTest
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
    public void addAlbumWhenValidRequestIsPassed()
    {
        // GIVEN
        authenticationService.registerUser(new RegisterUserRequest(USERNAME, PASSWORD));

        AddAlbumRequest addAlbumRequest = new AddAlbumRequest();
        addAlbumRequest.setTitle("Sample title");
        addAlbumRequest.setArtist("Sample artist");
        addAlbumRequest.setReleaseDate(LocalDate.now());
        addAlbumRequest.setUserName(USERNAME);
        addAlbumRequest.setAlbumRating(AlbumRating.TEN);

        // WHEN
        AddAlbumResponse addAlbumResponse = albumService.addAlbum(addAlbumRequest);

        // THEN
        assertThat(addAlbumResponse).isNotNull();
        assertThat(addAlbumResponse.isSuccessful()).isTrue();
        assertThat(addAlbumResponse.getErrorMessage()).isBlank();
    }

    @Test
    public void addAlbumWhenThereIsNoUserInTheDatabase()
    {
        // GIVEN
        AddAlbumRequest addAlbumRequest = new AddAlbumRequest();
        addAlbumRequest.setTitle("Sample title");
        addAlbumRequest.setArtist("Sample artist");
        addAlbumRequest.setReleaseDate(LocalDate.now());
        addAlbumRequest.setUserName(USERNAME);
        addAlbumRequest.setAlbumRating(AlbumRating.TEN);

        // WHEN
        AddAlbumResponse addAlbumResponse = albumService.addAlbum(addAlbumRequest);

        // THEN
        assertThat(addAlbumResponse).isNotNull();
        assertThat(addAlbumResponse.isSuccessful()).isFalse();
        assertThat(addAlbumResponse.getErrorMessage()).isEqualToIgnoringCase(environment.getProperty("user.not_found"));
    }

    @Test
    public void addAlbumWhenWhenBlankRequestIsPassed()
    {
        // GIVEN
        AddAlbumRequest addAlbumRequest = new AddAlbumRequest();

        // WHEN
        AddAlbumResponse addAlbumResponse = albumService.addAlbum(addAlbumRequest);

        // THEN
        assertThat(addAlbumResponse).isNotNull();
        assertThat(addAlbumResponse.isSuccessful()).isFalse();
        assertThat(addAlbumResponse.getErrorMessage()).isEqualToIgnoringCase(environment.getProperty("album.add_album_error"));
    }
}
