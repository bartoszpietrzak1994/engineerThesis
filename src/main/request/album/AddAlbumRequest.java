package request.album;

import lombok.Data;
import lombok.NoArgsConstructor;
import model.album.AlbumRating;

import java.time.LocalDate;

@NoArgsConstructor
@Data
public class AddAlbumRequest
{
    private String artist;
    private String title;
    private LocalDate releaseDate;
    private AlbumRating albumRating;
    private byte[] albumCover;
    private String userName;
}
