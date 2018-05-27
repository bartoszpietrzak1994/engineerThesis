package response.album;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import response.GenericResponse;

@NoArgsConstructor
public class GetAlbumCoverResponse extends GenericResponse
{
    @Getter
    @Setter
    private byte[] albumCover;

    public GetAlbumCoverResponse(byte[] albumCover)
    {
        this.setSuccessful(true);
        this.setAlbumCover(albumCover);
    }
}
