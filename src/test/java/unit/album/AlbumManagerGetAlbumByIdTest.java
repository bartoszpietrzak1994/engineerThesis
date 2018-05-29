package unit.album;

import org.junit.Test;
import org.mockito.Mockito;
import request.album.AddAlbumRequest;
import request.album.GetAlbumByIdRequest;
import response.GenericResponse;
import response.album.GetAlbumByIdResponse;
import response.album.GetAlbumCoverResponse;

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
}
