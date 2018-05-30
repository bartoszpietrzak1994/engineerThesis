package unit.album;

import manager.sorting.AlbumSortingMethod;
import manager.sorting.ReleaseDateSortingMethod;
import model.album.AlbumOrderingCriteria;
import org.junit.Test;
import org.mockito.Mockito;
import request.album.GetAlbumsOrderedByCriteriaRequest;
import response.album.GetAlbumsOrderedByCriteriaResponse;

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
}
