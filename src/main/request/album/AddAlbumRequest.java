package request.album;

import lombok.Data;
import lombok.NoArgsConstructor;
import model.album.AlbumRating;

import java.sql.Date;

@NoArgsConstructor
@Data
public class AddAlbumRequest
{
    private String artist;
    private String title;
    private Date releaseDate;
    private AlbumRating albumRating;
    private byte[] albumCover;
}
