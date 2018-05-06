package request.album;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetAlbumCoverRequest
{
    private String albumId;
}
