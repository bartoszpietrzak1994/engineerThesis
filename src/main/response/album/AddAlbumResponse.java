package response.album;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddAlbumResponse
{
    private boolean isSuccessful;
    private String errorMessage;
}
