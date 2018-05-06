package response.album;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetAlbumCoverResponse
{
    private boolean isSuccessful;
    private String errorMessage;

    private byte[] albumCover;
}
