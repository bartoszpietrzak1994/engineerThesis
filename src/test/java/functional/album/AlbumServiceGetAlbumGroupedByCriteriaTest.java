package functional.album;

import dto.album.AlbumDto;
import functional.BaseFunctionalTest;
import model.album.Album;
import model.album.AlbumOrderingCriteria;
import model.album.AlbumRating;
import org.junit.Test;
import request.album.AddAlbumRequest;
import request.album.GetAlbumsOrderedByCriteriaRequest;
import request.user.RegisterUserRequest;
import response.album.AddAlbumResponse;
import response.album.GetAlbumsOrderedByCriteriaResponse;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class AlbumServiceGetAlbumGroupedByCriteriaTest extends BaseFunctionalTest
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
        GetAlbumsOrderedByCriteriaResponse albumsGrouppedByCriteria = this.albumService.getAlbumsOrderedByCriteria
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

    @Test
    public void getAlbumsGroupedByCriteriaOrderedByRatingDate()
    {
        // GIVEN
        this.authenticationService.registerUser(new RegisterUserRequest(USERNAME, PASSWORD));
        AddAlbumRequest firstAlbum = this.prepareAddAlbumRequest();
        firstAlbum.setAlbumRating(AlbumRating.TEN);
        AddAlbumRequest secondAlbum = this.prepareAddAlbumRequest();
        secondAlbum.setAlbumRating(AlbumRating.TEN);
        AddAlbumRequest thirdAlbum = this.prepareAddAlbumRequest();
        thirdAlbum.setAlbumRating(AlbumRating.TEN);

        AddAlbumResponse addThirdAlbumResponse = this.albumService.addAlbum(thirdAlbum);
        this.setRatingDate(addThirdAlbumResponse.getAlbumId(), LocalDate.now().minusDays(10));

        AddAlbumResponse addFirstAlbumResponse = this.albumService.addAlbum(firstAlbum);
        this.setRatingDate(addFirstAlbumResponse.getAlbumId(), LocalDate.now().minusDays(20));

        AddAlbumResponse addSecondAlbumResponse = this.albumService.addAlbum(secondAlbum);
        this.setRatingDate(addSecondAlbumResponse.getAlbumId(), LocalDate.now().minusDays(30));

        GetAlbumsOrderedByCriteriaRequest getAlbumsOrderedByCriteriaRequest = new GetAlbumsOrderedByCriteriaRequest();
        getAlbumsOrderedByCriteriaRequest.setSortingCriteria(AlbumOrderingCriteria.RATING_DATE.toString());

        // WHEN
        GetAlbumsOrderedByCriteriaResponse albumsGrouppedByCriteria = this.albumService.getAlbumsOrderedByCriteria
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

    @Test
    public void getAlbumsGroupedByCriteriaOrderedByArtistName()
    {
        // GIVEN
        this.authenticationService.registerUser(new RegisterUserRequest(USERNAME, PASSWORD));
        AddAlbumRequest firstAlbum = this.prepareAddAlbumRequest();
        AddAlbumRequest secondAlbum = this.prepareAddAlbumRequest();
        secondAlbum.setReleaseDate(LocalDate.now().minusDays(1));
        AddAlbumRequest thirdAlbum = this.prepareAddAlbumRequest();
        thirdAlbum.setReleaseDate(LocalDate.now().minusDays(2));

        AddAlbumResponse addThirdAlbumResponse = this.albumService.addAlbum(thirdAlbum);
        this.setArtist(addThirdAlbumResponse.getAlbumId(), "aArtist");

        AddAlbumResponse addFirstAlbumResponse = this.albumService.addAlbum(firstAlbum);
        this.setArtist(addFirstAlbumResponse.getAlbumId(), "cArtist");

        AddAlbumResponse addSecondAlbumResponse = this.albumService.addAlbum(secondAlbum);
        this.setArtist(addSecondAlbumResponse.getAlbumId(), "bArtist");

        GetAlbumsOrderedByCriteriaRequest getAlbumsOrderedByCriteriaRequest = new GetAlbumsOrderedByCriteriaRequest();
        getAlbumsOrderedByCriteriaRequest.setSortingCriteria(AlbumOrderingCriteria.ARTIST_NAME.toString());

        // WHEN
        GetAlbumsOrderedByCriteriaResponse albumsGrouppedByCriteria = this.albumService.getAlbumsOrderedByCriteria
                (getAlbumsOrderedByCriteriaRequest);

        // THEN
        assertThat(albumsGrouppedByCriteria).isNotNull();
        assertThat(albumsGrouppedByCriteria.isSuccessful()).isTrue();

        List<AlbumDto> albums = albumsGrouppedByCriteria.getAlbums();
        assertThat(albums.size()).isEqualTo(3);

        List<String> albumTitles = albums.stream().map(AlbumDto::getTitle).collect(Collectors.toList());

        assertThat(albumTitles).containsExactlyInAnyOrderElementsOf(new ArrayList<>(Arrays.asList(thirdAlbum.getTitle
                (), secondAlbum.getTitle(), firstAlbum.getTitle())));
    }

    @Test
    public void getAlbumsGroupedByCriteriaOrderedByAlbumTitle()
    {
        // GIVEN
        this.authenticationService.registerUser(new RegisterUserRequest(USERNAME, PASSWORD));
        AddAlbumRequest firstAlbum = this.prepareAddAlbumRequest();
        AddAlbumRequest secondAlbum = this.prepareAddAlbumRequest();
        secondAlbum.setReleaseDate(LocalDate.now().minusDays(1));
        AddAlbumRequest thirdAlbum = this.prepareAddAlbumRequest();
        thirdAlbum.setReleaseDate(LocalDate.now().minusDays(2));

        AddAlbumResponse addThirdAlbumResponse = this.albumService.addAlbum(thirdAlbum);
        this.setTitle(addThirdAlbumResponse.getAlbumId(), "zTitle");

        AddAlbumResponse addFirstAlbumResponse = this.albumService.addAlbum(firstAlbum);
        this.setTitle(addFirstAlbumResponse.getAlbumId(), "bTitle");

        AddAlbumResponse addSecondAlbumResponse = this.albumService.addAlbum(secondAlbum);
        this.setTitle(addSecondAlbumResponse.getAlbumId(), "yTitle");

        GetAlbumsOrderedByCriteriaRequest getAlbumsOrderedByCriteriaRequest = new GetAlbumsOrderedByCriteriaRequest();
        getAlbumsOrderedByCriteriaRequest.setSortingCriteria(AlbumOrderingCriteria.ALBUM_TITLE.toString());

        // WHEN
        GetAlbumsOrderedByCriteriaResponse albumsGrouppedByCriteria = this.albumService.getAlbumsOrderedByCriteria
                (getAlbumsOrderedByCriteriaRequest);

        // THEN
        assertThat(albumsGrouppedByCriteria).isNotNull();
        assertThat(albumsGrouppedByCriteria.isSuccessful()).isTrue();

        List<AlbumDto> albums = albumsGrouppedByCriteria.getAlbums();
        assertThat(albums.size()).isEqualTo(3);

        List<String> albumTitles = albums.stream().map(AlbumDto::getTitle).collect(Collectors.toList());

        assertThat(albumTitles).containsExactlyInAnyOrderElementsOf(new ArrayList<>(Arrays.asList("bTitle", "yTitle",
                "zTitle")));
    }

    @Test
    public void getAlbumsGroupedByCriteriaOrderedByRating()
    {
        // GIVEN
        this.authenticationService.registerUser(new RegisterUserRequest(USERNAME, PASSWORD));
        AddAlbumRequest firstAlbum = this.prepareAddAlbumRequest();
        AddAlbumRequest secondAlbum = this.prepareAddAlbumRequest();
        secondAlbum.setReleaseDate(LocalDate.now().minusDays(1));
        AddAlbumRequest thirdAlbum = this.prepareAddAlbumRequest();
        thirdAlbum.setReleaseDate(LocalDate.now().minusDays(2));

        AddAlbumResponse addThirdAlbumResponse = this.albumService.addAlbum(thirdAlbum);
        this.setRating(addThirdAlbumResponse.getAlbumId(), AlbumRating.EIGHT);

        AddAlbumResponse addFirstAlbumResponse = this.albumService.addAlbum(firstAlbum);
        this.setRating(addFirstAlbumResponse.getAlbumId(), AlbumRating.TWO);

        AddAlbumResponse addSecondAlbumResponse = this.albumService.addAlbum(secondAlbum);
        this.setRating(addSecondAlbumResponse.getAlbumId(), AlbumRating.TEN);

        GetAlbumsOrderedByCriteriaRequest getAlbumsOrderedByCriteriaRequest = new GetAlbumsOrderedByCriteriaRequest();
        getAlbumsOrderedByCriteriaRequest.setSortingCriteria(AlbumOrderingCriteria.ALBUM_TITLE.toString());

        // WHEN
        GetAlbumsOrderedByCriteriaResponse albumsGrouppedByCriteria = this.albumService.getAlbumsOrderedByCriteria
                (getAlbumsOrderedByCriteriaRequest);

        // THEN
        assertThat(albumsGrouppedByCriteria).isNotNull();
        assertThat(albumsGrouppedByCriteria.isSuccessful()).isTrue();

        List<AlbumDto> albums = albumsGrouppedByCriteria.getAlbums();
        assertThat(albums.size()).isEqualTo(3);

        List<String> albumTitles = albums.stream().map(AlbumDto::getTitle).collect(Collectors.toList());

        assertThat(albumTitles).containsExactlyInAnyOrderElementsOf(new ArrayList<>(Arrays.asList(firstAlbum.getTitle
                (), thirdAlbum.getTitle(), secondAlbum.getTitle())));
    }

    private void setRatingDate(String albumId, LocalDate date)
    {
        Album album = this.albumRepository.findById(Long.valueOf(albumId)).get();
        album.setRatingDate(Date.valueOf(date));

        this.albumRepository.saveAndFlush(album);
    }

    private void setArtist(String albumId, String artist)
    {
        Album album = this.albumRepository.findById(Long.valueOf(albumId)).get();
        album.setArtist(artist);

        this.albumRepository.saveAndFlush(album);
    }

    private void setTitle(String albumId, String title)
    {
        Album album = this.albumRepository.findById(Long.valueOf(albumId)).get();
        album.setTitle(title);

        this.albumRepository.saveAndFlush(album);
    }

    private void setRating(String albumId, AlbumRating albumRating)
    {
        Album album = this.albumRepository.findById(Long.valueOf(albumId)).get();
        album.setAlbumRating(albumRating);

        this.albumRepository.saveAndFlush(album);
    }
}
