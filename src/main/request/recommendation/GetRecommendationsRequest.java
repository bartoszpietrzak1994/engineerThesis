package request.recommendation;

import dto.album.AlbumDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
final public class GetRecommendationsRequest
{
    List<AlbumDto> userAlbums;
}
