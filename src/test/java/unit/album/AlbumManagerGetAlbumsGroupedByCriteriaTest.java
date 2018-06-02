package unit.album;

import manager.AlbumManager;
import manager.sorting.ReleaseDateSortingMethod;
import model.album.AlbumOrderingCriteria;
import org.junit.Test;
import org.mockito.Mockito;
import request.album.GetAlbumsOrderedByCriteriaRequest;
import response.album.GetAlbumsOrderedByCriteriaResponse;
import validation.RequestValidator;

import java.util.ArrayList;
import java.util.HashSet;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.any;

public class AlbumManagerGetAlbumsGroupedByCriteriaTest extends BaseAlbumManagerTest
{
    @Test
    public void itReturnsGetAlbumsOrderedByCriteriaResponse()
    {
        // GIVEN
        Mockito.when(requestValidator.validate(any(GetAlbumsOrderedByCriteriaRequest.class))).thenReturn(new
                HashSet<>());
        Mockito.when(albumSortingMethodResolver.resolve(AlbumOrderingCriteria.RELEASE_DATE)).thenReturn(new
                ReleaseDateSortingMethod(albumRepository));
        Mockito.when(albumRepository.findAllOrderByReleaseDate()).thenReturn(new ArrayList<>());

        GetAlbumsOrderedByCriteriaRequest getAlbumsOrderedByCriteriaRequest = new GetAlbumsOrderedByCriteriaRequest();
        getAlbumsOrderedByCriteriaRequest.setSortingCriteria("RELEASE_DATE");

        // WHEN
        GetAlbumsOrderedByCriteriaResponse albumsGroupedByCriteria = albumManager.getAlbumsGroupedByCriteria
                (getAlbumsOrderedByCriteriaRequest);

        // THEN
        assertThat(albumsGroupedByCriteria).isInstanceOf(GetAlbumsOrderedByCriteriaResponse.class);
        assertThat(albumsGroupedByCriteria).hasFieldOrProperty("albums");
        assertThat(albumsGroupedByCriteria).hasFieldOrProperty("isSuccessful");
        assertThat(albumsGroupedByCriteria).hasFieldOrProperty("errorMessage");
    }

    @Test
    public void itValidatesGetAlbumsOrderedByCriteriaRequest()
    {
        // GIVEN
        albumManager = new AlbumManager(albumRepository, authenticationService, albumSortingMethodResolver,
                new RequestValidator(), environment);

        // WHEN
        GetAlbumsOrderedByCriteriaResponse albumsGroupedByCriteria = albumManager.getAlbumsGroupedByCriteria
                (new GetAlbumsOrderedByCriteriaRequest());

        // THEN
        assertThat(albumsGroupedByCriteria.isSuccessful()).isFalse();
        assertThat(albumsGroupedByCriteria.getErrorMessage()).contains("must not be");
    }

    @Test
    public void itReturnsErrorMessageWhenAnErrorOccursWhileQueryingDatabase()
    {
        // GIVEN
        Mockito.when(requestValidator.validate(any(GetAlbumsOrderedByCriteriaRequest.class))).thenReturn(new
                HashSet<>());
        Mockito.when(albumRepository.findAllOrderByReleaseDate()).thenThrow(new RuntimeException());
        Mockito.when(albumSortingMethodResolver.resolve(AlbumOrderingCriteria.RELEASE_DATE)).thenReturn(new
                ReleaseDateSortingMethod(albumRepository));
        Mockito.when(environment.getProperty("album.unexpected_error")).thenReturn("An unexpected error occurred");

        GetAlbumsOrderedByCriteriaRequest getAlbumsOrderedByCriteriaRequest = new GetAlbumsOrderedByCriteriaRequest();
        getAlbumsOrderedByCriteriaRequest.setSortingCriteria("RELEASE_DATE");

        // WHEN
        GetAlbumsOrderedByCriteriaResponse albumsGroupedByCriteria = albumManager.getAlbumsGroupedByCriteria
                (getAlbumsOrderedByCriteriaRequest);

        // THEN
        assertThat(albumsGroupedByCriteria.isSuccessful()).isFalse();
        assertThat(albumsGroupedByCriteria.getErrorMessage()).isEqualToIgnoringCase("An unexpected error occurred");
    }
}
