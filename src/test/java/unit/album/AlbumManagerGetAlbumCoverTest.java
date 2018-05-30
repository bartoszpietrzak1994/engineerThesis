package unit.album;

import manager.AlbumManager;
import model.album.Album;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.mockito.Mockito;
import request.album.AddAlbumRequest;
import request.album.GetAlbumCoverRequest;
import response.GenericResponse;
import response.album.GetAlbumCoverResponse;
import validation.RequestValidator;

import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.any;

public class AlbumManagerGetAlbumCoverTest extends BaseAlbumManagerTest
{
    @Test
    public void itReturnsGetAlbumCoverResponse()
    {
        // GIVEN
        Mockito.when(requestValidator.validate(any(AddAlbumRequest.class))).thenReturn(new HashSet<>());
        Mockito.when(albumRepository.findById(123L)).thenReturn(Optional.empty());
        GetAlbumCoverRequest getAlbumCoverRequest = new GetAlbumCoverRequest();
        getAlbumCoverRequest.setAlbumId("123");

        // WHEN
        GetAlbumCoverResponse albumCover = albumManager.getAlbumCover(getAlbumCoverRequest);

        // THEN
        assertThat(albumCover).isInstanceOf(GetAlbumCoverResponse.class);
        assertThat(albumCover).isInstanceOf(GenericResponse.class);
        assertThat(albumCover).hasFieldOrProperty("albumCover");
        assertThat(albumCover).hasFieldOrProperty("isSuccessful");
        assertThat(albumCover).hasFieldOrProperty("errorMessage");
    }

    @Test
    public void itValidatesGetAlbumCoverRequest()
    {
        // GIVEN
        albumManager = new AlbumManager(albumRepository, authenticationService, albumSortingMethodResolver,
                new RequestValidator(), environment);

        // WHEN
        GetAlbumCoverResponse albumCover = albumManager.getAlbumCover(new GetAlbumCoverRequest());

        // THEN
        assertThat(albumCover.isSuccessful()).isFalse();
        assertThat(albumCover.getErrorMessage()).contains("must not be");
        assertThat(albumCover.getAlbumCover()).isNull();
    }

    @Test
    public void isResolvesAlbumId()
    {
        // GIVEN
        Mockito.when(requestValidator.validate(any(GetAlbumCoverRequest.class))).thenReturn(new HashSet<>());
        Mockito.when(environment.getProperty("album.unexpected_error")).thenReturn("An unexpected error occurred");
        GetAlbumCoverRequest getAlbumCoverRequest = new GetAlbumCoverRequest();
        getAlbumCoverRequest.setAlbumId(RandomStringUtils.randomAlphabetic(5));

        // WHEN
        GetAlbumCoverResponse albumCover = albumManager.getAlbumCover(getAlbumCoverRequest);

        // THEN
        assertThat(albumCover.isSuccessful()).isFalse();
        assertThat(albumCover.getErrorMessage()).isEqualToIgnoringCase("An unexpected error occurred");
    }

    @Test
    public void itChecksIfAlbumWasAdded()
    {
        // GIVEN
        Mockito.when(requestValidator.validate(any(GetAlbumCoverRequest.class))).thenReturn(new HashSet<>());
        Mockito.when(environment.getProperty("album.not_found")).thenReturn("Album not found");
        Mockito.when(albumRepository.findById(123L)).thenReturn(Optional.empty());
        GetAlbumCoverRequest getAlbumCoverRequest = new GetAlbumCoverRequest();
        getAlbumCoverRequest.setAlbumId("123");

        // WHEN
        GetAlbumCoverResponse albumCover = albumManager.getAlbumCover(getAlbumCoverRequest);

        // THEN
        assertThat(albumCover.isSuccessful()).isFalse();
        assertThat(albumCover.getErrorMessage()).isEqualToIgnoringCase("Album not found");
        assertThat(albumCover.getAlbumCover()).isNull();
    }

    @Test
    public void itChecksIfAlbumHasCover()
    {
        // GIVEN
        Mockito.when(requestValidator.validate(any(GetAlbumCoverRequest.class))).thenReturn(new HashSet<>());
        Mockito.when(environment.getProperty("album.cover_not_found")).thenReturn("Album cover not found");
        Mockito.when(albumRepository.findById(123L)).thenReturn(Optional.of(new Album()));
        GetAlbumCoverRequest getAlbumCoverRequest = new GetAlbumCoverRequest();
        getAlbumCoverRequest.setAlbumId("123");

        // WHEN
        GetAlbumCoverResponse albumCover = albumManager.getAlbumCover(getAlbumCoverRequest);

        // THEN
        assertThat(albumCover.isSuccessful()).isFalse();
        assertThat(albumCover.getErrorMessage()).isEqualToIgnoringCase("Album cover not found");
        assertThat(albumCover.getAlbumCover()).isNull();
    }
}
