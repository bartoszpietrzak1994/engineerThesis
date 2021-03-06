package functional.album;

import functional.BaseFunctionalTest;
import model.album.AlbumRating;
import org.junit.Test;
import request.album.AddAlbumRequest;
import request.user.RegisterUserRequest;
import response.GenericResponse;

import java.time.LocalDate;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class AlbumServiceAddAlbumTest extends BaseFunctionalTest
{
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
        GenericResponse addAlbumResponse = albumService.addAlbum(addAlbumRequest);

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
        GenericResponse addAlbumResponse = albumService.addAlbum(addAlbumRequest);

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
        GenericResponse addAlbumResponse = albumService.addAlbum(addAlbumRequest);

        // THEN
        assertThat(addAlbumResponse).isNotNull();
        assertThat(addAlbumResponse.isSuccessful()).isFalse();
    }
}
