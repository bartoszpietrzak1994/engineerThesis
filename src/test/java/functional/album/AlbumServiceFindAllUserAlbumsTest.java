package functional.album;

import dto.album.AlbumDto;
import functional.BaseFunctionalTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import request.album.AddAlbumRequest;
import request.album.FindAllUserAlbumRequest;
import request.user.RegisterUserRequest;
import response.album.FindAllUserAlbumsResponse;
import service.AlbumService;
import service.AuthenticationService;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class AlbumServiceFindAllUserAlbumsTest extends BaseFunctionalTest
{
    @Autowired
    private AlbumService albumService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private Environment environment;

    @Test
    public void testFindAllUserAlbumsWhenValidRequestIsPassed()
    {
        // GIVEN
        this.authenticationService.registerUser(new RegisterUserRequest(USERNAME, PASSWORD));

        AddAlbumRequest firstAlbum = this.prepareAddAlbumRequest();
        AddAlbumRequest secondAlbum = this.prepareAddAlbumRequest();
        albumService.addAlbum(firstAlbum);
        albumService.addAlbum(secondAlbum);

        FindAllUserAlbumRequest findAllUserAlbumRequest = new FindAllUserAlbumRequest();
        findAllUserAlbumRequest.setUserName(USERNAME);

        // WHEN
        FindAllUserAlbumsResponse allAlbumsAddedByUser = this.albumService.findAllAlbumsAddedByUser
                (findAllUserAlbumRequest);

        // THEN
        assertThat(allAlbumsAddedByUser).isNotNull();
        assertThat(allAlbumsAddedByUser.isSuccessful()).isTrue();
        assertThat(allAlbumsAddedByUser.getErrorMessage()).isBlank();
        List<AlbumDto> albumList = allAlbumsAddedByUser.getAlbumList();

        assertThat(albumList.size()).isEqualTo(2);
        List<String> albumsTitles = albumList.stream().map(AlbumDto::getTitle).collect(Collectors.toList());

        assertThat(albumsTitles).containsExactly(firstAlbum.getTitle(), secondAlbum.getTitle());
    }

    @Test
    public void testFindAllUserAlbumsWhenNoAlbumIsAdded()
    {
        // GIVEN
        this.authenticationService.registerUser(new RegisterUserRequest(USERNAME, PASSWORD));

        FindAllUserAlbumRequest findAllUserAlbumRequest = new FindAllUserAlbumRequest();
        findAllUserAlbumRequest.setUserName(USERNAME);

        // WHEN
        FindAllUserAlbumsResponse allAlbumsAddedByUser = this.albumService.findAllAlbumsAddedByUser
                (findAllUserAlbumRequest);

        // THEN
        assertThat(allAlbumsAddedByUser).isNotNull();
        assertThat(allAlbumsAddedByUser.getAlbumList().size()).isEqualTo(0);
    }

    @Test
    public void testFindAllUserAlbumsWhenEmptyRequestIsPassed()
    {
        // GIVEN
        FindAllUserAlbumRequest findAllUserAlbumRequest = new FindAllUserAlbumRequest();

        // WHEN
        FindAllUserAlbumsResponse allAlbumsAddedByUser = this.albumService.findAllAlbumsAddedByUser
                (findAllUserAlbumRequest);

        // THEN
        assertThat(allAlbumsAddedByUser).isNotNull();
        assertThat(allAlbumsAddedByUser.isSuccessful()).isFalse();
        // todo message

    }
}
