package unit.album;

import manager.AlbumManager;
import model.album.Album;
import model.user.User;
import org.junit.Test;
import org.mockito.Mockito;
import request.album.AddAlbumRequest;
import response.GenericResponse;
import response.album.AddAlbumResponse;
import validation.RequestValidator;

import java.time.LocalDate;
import java.util.HashSet;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.any;

public class AlbumManagerAddAlbumTest extends BaseAlbumManagerTest
{
    @Test
    public void itReturnsAddAlbumResponse()
    {
        // GIVEN
        Mockito.when(requestValidator.validate(any(AddAlbumRequest.class))).thenReturn(new HashSet<>());
        Mockito.when(authenticationService.findUserByUsername(USERNAME)).thenReturn(new User());

        // WHEN
        AddAlbumResponse addAlbumResponse = albumManager.addAlbum(new AddAlbumRequest());

        // THEN
        assertThat(addAlbumResponse).isInstanceOf(AddAlbumResponse.class);
        assertThat(addAlbumResponse).isInstanceOf(GenericResponse.class);
        assertThat(addAlbumResponse).hasFieldOrProperty("isSuccessful");
        assertThat(addAlbumResponse).hasFieldOrProperty("errorMessage");
        assertThat(addAlbumResponse).hasFieldOrProperty("albumId");
    }

    @Test
    public void itValidatesAddAlbumRequest()
    {
        // GIVEN
        albumManager = new AlbumManager(albumRepository, authenticationService, albumSortingMethodResolver,
                new RequestValidator(), environment);

        // WHEN
        AddAlbumResponse addAlbumResponse = albumManager.addAlbum(new AddAlbumRequest());

        // THEN
        assertThat(addAlbumResponse.isSuccessful()).isFalse();
        assertThat(addAlbumResponse.getErrorMessage()).contains("must not be");
        assertThat(addAlbumResponse.getAlbumId()).isNull();
    }

    @Test
    public void itChecksIfUserIsRegistered()
    {
        // GIVEN
        Mockito.when(requestValidator.validate(any(AddAlbumRequest.class))).thenReturn(new HashSet<>());
        Mockito.when(authenticationService.findUserByUsername(USERNAME)).thenReturn(null);
        Mockito.when(environment.getProperty("user.not_found")).thenReturn("Requested user not found");

        AddAlbumRequest addAlbumRequest = new AddAlbumRequest();
        addAlbumRequest.setUserName(USERNAME);

        // WHEN
        AddAlbumResponse addAlbumResponse = albumManager.addAlbum(addAlbumRequest);

        // THEN
        assertThat(addAlbumResponse.isSuccessful()).isFalse();
        assertThat(addAlbumResponse.getErrorMessage()).isEqualToIgnoringCase("Requested user not found");
        assertThat(addAlbumResponse.getAlbumId()).isNull();
    }

    @Test
    public void itReturnsErrorMessageWhenAnErrorOccurredWhileSavingAlbumInTheDatabase()
    {
        // GIVEN
        Mockito.when(requestValidator.validate(any(AddAlbumRequest.class))).thenReturn(new HashSet<>());
        Mockito.when(authenticationService.findUserByUsername(USERNAME)).thenReturn(new User());
        Mockito.when(albumRepository.save(any(Album.class))).thenThrow(new RuntimeException());
        Mockito.when(environment.getProperty("album.add_album_error"))
                .thenReturn("An unexpected error occurred while trying to add album. Check if you provided all necessary data.");

        AddAlbumRequest addAlbumRequest = new AddAlbumRequest();
        addAlbumRequest.setUserName(USERNAME);
        addAlbumRequest.setReleaseDate(LocalDate.now());

        // WHEN
        AddAlbumResponse addAlbumResponse = albumManager.addAlbum(addAlbumRequest);

        // THEN
        assertThat(addAlbumResponse.isSuccessful()).isFalse();
        assertThat(addAlbumResponse.getErrorMessage())
                .isEqualToIgnoringCase("An unexpected error occurred while trying to add album. Check if you provided all necessary data.");
        assertThat(addAlbumResponse.getAlbumId()).isNull();
    }
}
