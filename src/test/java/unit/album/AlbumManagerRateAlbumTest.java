package unit.album;

import manager.AlbumManager;
import model.album.Album;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.mockito.Mockito;
import request.album.AddAlbumRequest;
import request.album.RateAlbumRequest;
import response.GenericResponse;
import validation.RequestValidator;

import java.util.HashSet;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.any;

public class AlbumManagerRateAlbumTest extends BaseAlbumManagerTest
{
    @Test
    public void itReturnsGenericResponse()
    {
        // GIVEN
        Mockito.when(requestValidator.validate(any(AddAlbumRequest.class))).thenReturn(new HashSet<>());
        Mockito.when(albumRepository.getOne(123L)).thenReturn(new Album());
        RateAlbumRequest rateAlbumRequest = new RateAlbumRequest();
        rateAlbumRequest.setAlbumId("123");
        rateAlbumRequest.setAlbumRating("TEN");

        // WHEN
        GenericResponse genericResponse = albumManager.rateAlbum(rateAlbumRequest);

        // THEN
        assertThat(genericResponse).isInstanceOf(GenericResponse.class);
        assertThat(genericResponse).hasFieldOrProperty("isSuccessful");
        assertThat(genericResponse).hasFieldOrProperty("errorMessage");
    }

    @Test
    public void itValidatesRateAlbumRequest()
    {
        // GIVEN
        albumManager = new AlbumManager(albumRepository, authenticationService, albumSortingMethodResolver,
                new RequestValidator(), environment);

        // WHEN
        GenericResponse genericResponse = albumManager.rateAlbum(new RateAlbumRequest());

        // THEN
        assertThat(genericResponse.isSuccessful()).isFalse();
        assertThat(genericResponse.getErrorMessage()).isEqualToIgnoringCase("must not be null");
    }

    @Test
    public void isResolvesAlbumId()
    {
        // GIVEN
        Mockito.when(requestValidator.validate(any(AddAlbumRequest.class))).thenReturn(new HashSet<>());
        Mockito.when(environment.getProperty("album.unexpected_error")).thenReturn("An unexpected error occurred");
        RateAlbumRequest rateAlbumRequest = new RateAlbumRequest();
        rateAlbumRequest.setAlbumId(RandomStringUtils.randomAlphabetic(5));

        // WHEN
        GenericResponse genericResponse = albumManager.rateAlbum(rateAlbumRequest);

        // THEN
        assertThat(genericResponse.isSuccessful()).isFalse();
        assertThat(genericResponse.getErrorMessage()).isEqualToIgnoringCase("An unexpected error occurred");
    }

    @Test
    public void itChecksIfAlbumIsAdded()
    {
        // GIVEN
        Mockito.when(requestValidator.validate(any(AddAlbumRequest.class))).thenReturn(new HashSet<>());
        Mockito.when(environment.getProperty("album.not_found")).thenReturn("Album not found");
        Mockito.when(albumRepository.getOne(123L)).thenReturn(null);
        RateAlbumRequest rateAlbumRequest = new RateAlbumRequest();
        rateAlbumRequest.setAlbumId("123");

        // WHEN
        GenericResponse genericResponse = albumManager.rateAlbum(rateAlbumRequest);

        // THEN
        assertThat(genericResponse.isSuccessful()).isFalse();
        assertThat(genericResponse.getErrorMessage()).isEqualToIgnoringCase("Album not found");
    }

    @Test
    public void itReturnsErrorMessageWhenAnErrorOccurredWhileSavingAlbumInTheDatabase()
    {
        // GIVEN
        Mockito.when(requestValidator.validate(any(AddAlbumRequest.class))).thenReturn(new HashSet<>());
        Mockito.when(albumRepository.save(any(Album.class))).thenThrow(new RuntimeException());
        Mockito.when(albumRepository.getOne(123L)).thenReturn(new Album());
        Mockito.when(environment.getProperty("album.unexpected_error")).thenReturn("An unexpected error occurred");
        RateAlbumRequest rateAlbumRequest = new RateAlbumRequest();
        rateAlbumRequest.setAlbumId("123");
        rateAlbumRequest.setAlbumRating("TEN");

        // WHEN
        GenericResponse genericResponse = albumManager.rateAlbum(rateAlbumRequest);

        // THEN
        assertThat(genericResponse.isSuccessful()).isFalse();
        assertThat(genericResponse.getErrorMessage()).isEqualToIgnoringCase("An unexpected error occurred");
    }
}
