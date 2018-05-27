package response.album;

import dto.album.AlbumDto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import request.album.GetAlbumByIdRequest;
import response.GenericResponse;

@NoArgsConstructor
public class GetAlbumByIdResponse extends GenericResponse
{
    @Getter
    @Setter
    private AlbumDto album;

    public GetAlbumByIdResponse(AlbumDto album)
    {
        this.setSuccessful(true);
        this.setAlbum(album);
    }
}
