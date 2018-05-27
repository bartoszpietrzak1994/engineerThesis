package functional.album;

import dto.album.AlbumDto;
import functional.BaseFunctionalTest;
import model.album.AlbumOrderingCriteria;
import org.junit.Test;
import request.album.AddAlbumRequest;
import request.album.GetAlbumsOrderedByCriteriaRequest;
import request.album.RateAlbumRequest;
import request.user.RegisterUserRequest;
import response.album.GetAlbumsOrderedByCriteriaResponse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class AlbumServiceGetAlbumGroupedByCrietriaTest extends BaseFunctionalTest
{
    @Test
    public void getAlbumsGroupedByCriteriaOrderedByReleaseDate()
    {
        // GIVEN
        this.authenticationService.registerUser(new RegisterUserRequest(USERNAME, PASSWORD));
        AddAlbumRequest firstAlbum = this.prepareAddAlbumRequest();
        AddAlbumRequest secondAlbum = this.prepareAddAlbumRequest();
        secondAlbum.setReleaseDate(LocalDate.now().minusDays(1));
        AddAlbumRequest thirdAlbum = this.prepareAddAlbumRequest();
        thirdAlbum.setReleaseDate(LocalDate.now().minusDays(2));

        this.albumService.addAlbum(thirdAlbum);
        this.albumService.addAlbum(firstAlbum);
        this.albumService.addAlbum(secondAlbum);

        GetAlbumsOrderedByCriteriaRequest getAlbumsOrderedByCriteriaRequest = new GetAlbumsOrderedByCriteriaRequest();
        getAlbumsOrderedByCriteriaRequest.setSortingCriteria(AlbumOrderingCriteria.RELEASE_DATE.toString());

        // WHEN
        GetAlbumsOrderedByCriteriaResponse albumsGrouppedByCriteria = this.albumService.getAlbumsGrouppedByCriteria
                (getAlbumsOrderedByCriteriaRequest);

        // THEN
        assertThat(albumsGrouppedByCriteria).isNotNull();
        assertThat(albumsGrouppedByCriteria.isSuccessful()).isTrue();

        List<AlbumDto> albums = albumsGrouppedByCriteria.getAlbums();
        assertThat(albums.size()).isEqualTo(3);

        List<String> albumTitles = albums.stream().map(AlbumDto::getTitle).collect(Collectors.toList());

        assertThat(albumTitles).containsExactlyInAnyOrderElementsOf(new ArrayList<>(Arrays.asList(firstAlbum.getTitle
                (), secondAlbum.getTitle(), thirdAlbum.getTitle())));
    }
}
