package request.album;

import lombok.Data;
import lombok.NoArgsConstructor;
import model.album.AlbumRating;

@NoArgsConstructor
@Data
public class RateAlbumRequest
{
    private Long albumId;
    private AlbumRating albumRating;
}
