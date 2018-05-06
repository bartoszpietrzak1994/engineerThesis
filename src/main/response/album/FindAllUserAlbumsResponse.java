package response.album;

import lombok.Data;
import lombok.NoArgsConstructor;
import model.album.Album;

import java.util.List;

@Data
@NoArgsConstructor
public class FindAllUserAlbumsResponse
{
    private boolean isSuccessful;
    private String errorMessage;
    private List<Album> albumList;
}
