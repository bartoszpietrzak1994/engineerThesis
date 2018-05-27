package functional.album;

import com.google.common.collect.Iterables;
import functional.BaseFunctionalTest;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import request.album.AddAlbumRequest;
import request.album.FindAllUserAlbumRequest;
import request.album.GetAlbumCoverRequest;
import request.user.RegisterUserRequest;
import response.album.GetAlbumCoverResponse;
import service.AlbumService;
import service.AuthenticationService;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class AlbumServiceGetAlbumCoverTest extends BaseFunctionalTest
{
    @Autowired
    private AlbumService albumService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private Environment environment;

    @Test
    public void testGetAlbumCoverWhenValidRequestIsPassed()
    {
        // GIVEN
        this.authenticationService.registerUser(new RegisterUserRequest(USERNAME, PASSWORD));
        AddAlbumRequest addAlbumRequest = prepareAddAlbumRequest();
        byte[] albumCover = new byte[1024];
        addAlbumRequest.setAlbumCover(albumCover);

        FindAllUserAlbumRequest findAllUserAlbumRequest = new FindAllUserAlbumRequest();
        findAllUserAlbumRequest.setUserName(USERNAME);
        String albumId = Iterables.getFirst(albumService.findAllAlbumsAddedByUser(findAllUserAlbumRequest)
                .getAlbumList(), null).getAlbumId();

        GetAlbumCoverRequest getAlbumCoverRequest = new GetAlbumCoverRequest();
        getAlbumCoverRequest.setAlbumId(albumId);

        // WHEN
        GetAlbumCoverResponse getAlbumCoverResponse = this.albumService.getAlbumCover(getAlbumCoverRequest);

        // THEN
        assertThat(getAlbumCoverResponse).isNotNull();
        assertThat(getAlbumCoverResponse.isSuccessful()).isTrue();
        assertThat(getAlbumCoverResponse.getErrorMessage()).isBlank();
        assertThat(getAlbumCoverResponse.getAlbumCover()).isEqualTo(albumCover);
    }

    @Test
    public void testGetAlbumCoverWhenInvalidUserIdIsPassed()
    {
        // GIVEN
        GetAlbumCoverRequest getAlbumCoverRequest = new GetAlbumCoverRequest();
        getAlbumCoverRequest.setAlbumId(RandomStringUtils.randomAlphanumeric(15));

        // WHEN
        GetAlbumCoverResponse getAlbumCoverResponse = this.albumService.getAlbumCover(getAlbumCoverRequest);

        // THEN
        assertThat(getAlbumCoverResponse).isNotNull();
        assertThat(getAlbumCoverResponse.isSuccessful()).isFalse();
        assertThat(getAlbumCoverResponse.getAlbumCover()).isNull();
        assertThat(getAlbumCoverResponse.getErrorMessage()).isEqualToIgnoringCase(this.environment.getProperty("album" +
                ".unexpected_error"));
    }

    @Test
    public void testGetAlbumCoverWhenEmptyRequestIsPassed()
    {
        // GIVEN
        GetAlbumCoverRequest getAlbumCoverRequest = new GetAlbumCoverRequest();

        // WHEN
        GetAlbumCoverResponse getAlbumCoverResponse = this.albumService.getAlbumCover(getAlbumCoverRequest);

        // THEN
        assertThat(getAlbumCoverResponse).isNotNull();
        assertThat(getAlbumCoverResponse.isSuccessful()).isFalse();
//        todo
//        assertThat(getAlbumCoverResponse.getErrorMessage()).isEqualToIgnoringCase();
    }
}
