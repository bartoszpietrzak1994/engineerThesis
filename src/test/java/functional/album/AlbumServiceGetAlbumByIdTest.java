package functional.album;

import functional.BaseFunctionalTest;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import request.album.AddAlbumRequest;
import request.album.GetAlbumByIdRequest;
import request.user.RegisterUserRequest;
import response.album.AddAlbumResponse;
import response.album.GetAlbumByIdResponse;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class AlbumServiceGetAlbumByIdTest extends BaseFunctionalTest
{
    @Test
    public void testGetAlbumByIdWhenValidRequestIsPassed()
    {
        // GIVEN
        this.authenticationService.registerUser(new RegisterUserRequest(USERNAME, PASSWORD));
        AddAlbumRequest addAlbumRequest = prepareAddAlbumRequest();

        AddAlbumResponse addAlbumResponse = this.albumService.addAlbum(addAlbumRequest);

        GetAlbumByIdRequest getAlbumByIdRequest = new GetAlbumByIdRequest();
        getAlbumByIdRequest.setAlbumId(addAlbumResponse.getAlbumId());

        // WHEN
        GetAlbumByIdResponse getAlbumByIdResponse = this.albumService.getAlbumById(getAlbumByIdRequest);

        // THEN
        assertThat(getAlbumByIdResponse).isNotNull();
        assertThat(getAlbumByIdResponse.isSuccessful()).isTrue();
        assertThat(getAlbumByIdResponse.getErrorMessage()).isBlank();
        assertThat(getAlbumByIdResponse.getAlbum().getTitle()).isEqualToIgnoringCase(addAlbumRequest.getTitle());
    }

    @Test
    public void testGetAlbumByIdWhenInvalidIdIsPassed()
    {
        // GIVEN
        GetAlbumByIdRequest getAlbumByIdRequest = new GetAlbumByIdRequest();
        getAlbumByIdRequest.setAlbumId(RandomStringUtils.randomAlphanumeric(15));

        // WHEN
        GetAlbumByIdResponse getAlbumByIdResponse = this.albumService.getAlbumById(getAlbumByIdRequest);

        // THEN
        assertThat(getAlbumByIdResponse).isNotNull();
        assertThat(getAlbumByIdResponse.isSuccessful()).isFalse();
        assertThat(getAlbumByIdResponse.getErrorMessage()).isEqualToIgnoringCase(this.environment.getProperty("album.unexpected_error"));
        assertThat(getAlbumByIdResponse.getAlbum()).isNull();
    }

    @Test
    public void testGetAlbumByIdWhenNotExistingIdIsPassed()
    {
        // GIVEN
        GetAlbumByIdRequest getAlbumByIdRequest = new GetAlbumByIdRequest();
        getAlbumByIdRequest.setAlbumId(RandomStringUtils.randomNumeric(15));

        // WHEN
        GetAlbumByIdResponse getAlbumByIdResponse = this.albumService.getAlbumById(getAlbumByIdRequest);

        // THEN
        assertThat(getAlbumByIdResponse).isNotNull();
        assertThat(getAlbumByIdResponse.isSuccessful()).isFalse();
        assertThat(getAlbumByIdResponse.getErrorMessage()).isEqualToIgnoringCase(this.environment.getProperty("album.not_found"));
        assertThat(getAlbumByIdResponse.getAlbum()).isNull();
    }

    @Test
    public void testGetAlbumByIdWhenEmptyRequestIsPassed()
    {
        // GIVEN
        GetAlbumByIdRequest getAlbumByIdRequest = new GetAlbumByIdRequest();

        // WHEN
        GetAlbumByIdResponse getAlbumByIdResponse = this.albumService.getAlbumById(getAlbumByIdRequest);

        // THEN
        assertThat(getAlbumByIdResponse).isNotNull();
        assertThat(getAlbumByIdResponse.isSuccessful()).isFalse();
        assertThat(getAlbumByIdResponse.getAlbum()).isNull();
    }
}
