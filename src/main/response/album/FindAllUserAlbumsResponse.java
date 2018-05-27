package response.album;

import dto.album.AlbumDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import response.GenericResponse;

import java.util.List;

@NoArgsConstructor
public class FindAllUserAlbumsResponse extends GenericResponse
{
    @Getter
    @Setter
    private List<AlbumDto> albumList;

    public FindAllUserAlbumsResponse(List<AlbumDto> albumList)
    {
        this.setSuccessful(true);
        this.albumList = albumList;
    }
}
