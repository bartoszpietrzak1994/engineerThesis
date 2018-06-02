package unit.album;

import manager.AlbumManager;
import model.user.User;
import org.junit.Test;
import org.mockito.Mockito;
import request.album.AddAlbumRequest;
import request.album.FindAllUserAlbumRequest;
import response.GenericResponse;
import response.album.FindAllUserAlbumsResponse;
import validation.RequestValidator;

import java.util.ArrayList;
import java.util.HashSet;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.any;

public class AlbumManagerFindAllUserAlbumsTest extends BaseAlbumManagerTest
{
    @Test
    public void itReturnsFindAllUserAlbumsResponse()
    {
        // GIVEN
        Mockito.when(requestValidator.validate(any(AddAlbumRequest.class))).thenReturn(new HashSet<>());
        Mockito.when(authenticationService.findUserByUsername(any())).thenReturn(new User());
        Mockito.when(albumRepository.findAllByUser(any())).thenReturn(new ArrayList<>());

        // WHEN
        FindAllUserAlbumsResponse allUserAlbums = albumManager.findAllUserAlbums(new FindAllUserAlbumRequest());

        // THEN
        assertThat(allUserAlbums).isInstanceOf(FindAllUserAlbumsResponse.class);
        assertThat(allUserAlbums).isInstanceOf(GenericResponse.class);
        assertThat(allUserAlbums).hasFieldOrProperty("albumList");
        assertThat(allUserAlbums).hasFieldOrProperty("isSuccessful");
        assertThat(allUserAlbums).hasFieldOrProperty("errorMessage");
    }

    @Test
    public void itValidatesFindAllUserAlbumsRequest()
    {
        // GIVEN
        albumManager = new AlbumManager(albumRepository, authenticationService, albumSortingMethodResolver,
                new RequestValidator(), environment);

        // WHEN
        FindAllUserAlbumsResponse allUserAlbums = albumManager.findAllUserAlbums(new FindAllUserAlbumRequest());

        // THEN
        assertThat(allUserAlbums.isSuccessful()).isFalse();
        assertThat(allUserAlbums.getErrorMessage()).contains("must not be");
        assertThat(allUserAlbums.getAlbumList()).isNull();
    }

    @Test
    public void itChecksIfUserIsRegistered()
    {
        // GIVEN
        Mockito.when(requestValidator.validate(any(AddAlbumRequest.class))).thenReturn(new HashSet<>());
        Mockito.when(authenticationService.findUserByUsername(USERNAME)).thenReturn(null);
        Mockito.when(environment.getProperty("user.not_found")).thenReturn("User not found");
        FindAllUserAlbumRequest findAllUserAlbumRequest = new FindAllUserAlbumRequest();
        findAllUserAlbumRequest.setUserName(USERNAME);

        // WHEN
        FindAllUserAlbumsResponse allUserAlbums = albumManager.findAllUserAlbums(findAllUserAlbumRequest);

        // THEN
        assertThat(allUserAlbums.isSuccessful()).isFalse();
        assertThat(allUserAlbums.getErrorMessage()).isEqualToIgnoringCase("User not found");
    }

    @Test
    public void itReturnsErrorMessageWhenAnErrorOccurredWhileGettingUserAlbums()
    {
        // GIVEN
        Mockito.when(requestValidator.validate(any(AddAlbumRequest.class))).thenReturn(new HashSet<>());
        Mockito.when(albumRepository.findAllByUser(any(User.class))).thenThrow(new RuntimeException());
        Mockito.when(authenticationService.findUserByUsername(USERNAME)).thenReturn(new User());
        Mockito.when(environment.getProperty("album.unexpected_error")).thenReturn("An unexpected error occurred");
        FindAllUserAlbumRequest findAllUserAlbumRequest = new FindAllUserAlbumRequest();
        findAllUserAlbumRequest.setUserName(USERNAME);

        // WHEN
        FindAllUserAlbumsResponse allUserAlbums = albumManager.findAllUserAlbums(findAllUserAlbumRequest);

        // THEN
        assertThat(allUserAlbums.isSuccessful()).isFalse();
        assertThat(allUserAlbums.getErrorMessage()).isEqualToIgnoringCase("An unexpected error occurred");
        assertThat(allUserAlbums.getAlbumList()).isNull();
    }
}
