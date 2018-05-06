package request.album;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FindAllUserAlbumRequest
{
    private String userName;
}
