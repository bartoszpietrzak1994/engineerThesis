package response.album;

import dto.album.AlbumDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class FindAllUserAlbumsResponse
{
    private boolean isSuccessful;
    private String errorMessage;
    private List<AlbumDto> albumList;
}