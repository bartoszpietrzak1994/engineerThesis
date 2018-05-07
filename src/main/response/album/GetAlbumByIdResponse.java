package response.album;

import dto.album.AlbumDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetAlbumByIdResponse
{
    private boolean isSuccessful;
    private String errorMessage;
    private AlbumDto album;
}
