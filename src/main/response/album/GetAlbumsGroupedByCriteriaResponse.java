package response.album;

import dto.album.AlbumDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
final public class GetAlbumsGroupedByCriteriaResponse
{
    private boolean isSuccessful;
    private String errorMessage;
    private List<AlbumDto> albums;
}
