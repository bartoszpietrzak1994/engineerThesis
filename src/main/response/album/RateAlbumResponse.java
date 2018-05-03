package response.album;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RateAlbumResponse
{
    private boolean isSuccessful;
    private String errorMessage;
}
