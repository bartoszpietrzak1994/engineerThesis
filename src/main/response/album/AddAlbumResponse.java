package response.album;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import response.GenericResponse;

@NoArgsConstructor
public class AddAlbumResponse extends GenericResponse
{
    @Getter
    @Setter
    private String albumId;

    public AddAlbumResponse(String albumId)
    {
        this.setSuccessful(true);
        this.setAlbumId(albumId);
    }
}
