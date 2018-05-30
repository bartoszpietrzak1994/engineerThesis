package unit.album;

import dto.album.AlbumDto;
import manager.AlbumManager;
import model.album.Album;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.mockito.Mockito;
import request.album.AddAlbumRequest;
import request.album.GetAlbumByIdRequest;
import request.album.GetAlbumCoverRequest;
import response.GenericResponse;
import response.album.GetAlbumByIdResponse;
import validation.RequestValidator;

import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.any;

public class AlbumManagerGetAlbumByIdTest extends BaseAlbumManagerTest
{
    @Test
    public void itReturnsGetAlbumByIdResponse()
    {
        // GIVEN
        Mockito.when(requestValidator.validate(any(AddAlbumRequest.class))).thenReturn(new HashSet<>());
        Mockito.when(albumRepository.findById(123L)).thenReturn(Optional.of(prepareAlbum()));
        GetAlbumByIdRequest getAlbumByIdRequest = new GetAlbumByIdRequest();
        getAlbumByIdRequest.setAlbumId("123");

        // WHEN
        GetAlbumByIdResponse albumById = albumManager.getAlbumById(getAlbumByIdRequest);

        // THEN
        assertThat(albumById).isInstanceOf(GetAlbumByIdResponse.class);
        assertThat(albumById).isInstanceOf(GenericResponse.class);
        assertThat(albumById).hasFieldOrProperty("album");
        assertThat(albumById).hasFieldOrProperty("isSuccessful");
        assertThat(albumById).hasFieldOrProperty("errorMessage");
    }

    @Test
    public void itValidatesGetAlbumByIdRequest()
    {
        // GIVEN
        albumManager = new AlbumManager(albumRepository, authenticationService, albumSortingMethodResolver,
                new RequestValidator(), environment);

        // WHEN
        GetAlbumByIdResponse albumById = albumManager.getAlbumById(new GetAlbumByIdRequest());

        // THEN
        assertThat(albumById.isSuccessful()).isFalse();
        assertThat(albumById.getErrorMessage()).contains("must not be");
    }

    @Test
    public void isResolvesAlbumId()
    {
        // GIVEN
        Mockito.when(requestValidator.validate(any(GetAlbumCoverRequest.class))).thenReturn(new HashSet<>());
        Mockito.when(environment.getProperty("album.unexpected_error")).thenReturn("An unexpected error occurred");
        GetAlbumByIdRequest getAlbumByIdRequest = new GetAlbumByIdRequest();
        getAlbumByIdRequest.setAlbumId(RandomStringUtils.randomAlphabetic(5));

        // WHEN
        GetAlbumByIdResponse albumById = albumManager.getAlbumById(getAlbumByIdRequest);

        // THEN
        assertThat(albumById.isSuccessful()).isFalse();
        assertThat(albumById.getErrorMessage()).isEqualToIgnoringCase("An unexpected error occurred");
    }

    @Test
    public void itChecksIfAlbumWasAdded()
    {
        // GIVEN
        Mockito.when(requestValidator.validate(any(GetAlbumCoverRequest.class))).thenReturn(new HashSet<>());
        Mockito.when(albumRepository.findById(123L)).thenReturn(Optional.empty());
        Mockito.when(environment.getProperty("album.not_found")).thenReturn("Album not found");
        GetAlbumByIdRequest getAlbumByIdRequest = new GetAlbumByIdRequest();
        getAlbumByIdRequest.setAlbumId("123");

        // WHEN
        GetAlbumByIdResponse albumById = albumManager.getAlbumById(getAlbumByIdRequest);

        // THEN
        assertThat(albumById.isSuccessful()).isFalse();
        assertThat(albumById.getErrorMessage()).isEqualToIgnoringCase("Album not found");
    }

    @Test
    public void itReturnsPreviouslyAddedAlbum()
    {
        // GIVEN
        Mockito.when(requestValidator.validate(any(GetAlbumCoverRequest.class))).thenReturn(new HashSet<>());
        Mockito.when(albumRepository.findById(123L)).thenReturn(Optional.of(prepareAlbum()));
        GetAlbumByIdRequest getAlbumByIdRequest = new GetAlbumByIdRequest();
        getAlbumByIdRequest.setAlbumId("123");

        // WHEN
        GetAlbumByIdResponse albumById = albumManager.getAlbumById(getAlbumByIdRequest);

        // THEN
        assertThat(albumById.isSuccessful()).isTrue();
        assertThat(albumById.getErrorMessage()).isBlank();
        assertThat(albumById.getAlbum()).isNotNull();
        assertThat(albumById.getAlbum()).isInstanceOf(AlbumDto.class);
    }
}
