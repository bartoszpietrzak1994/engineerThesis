package response.album;

import dto.album.AlbumDto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import response.GenericResponse;

import java.util.List;

@NoArgsConstructor
final public class GetAlbumsOrderedByCriteriaResponse extends GenericResponse
{
    @Getter
    @Setter
    private List<AlbumDto> albums;

    public GetAlbumsOrderedByCriteriaResponse(List<AlbumDto> albums)
    {
        this.setSuccessful(true);
        this.setAlbums(albums);
    }
}
