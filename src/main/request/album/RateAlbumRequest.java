package request.album;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RateAlbumRequest
{
    private String albumId;
    private String albumRating;
}