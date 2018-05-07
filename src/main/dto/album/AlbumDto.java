package dto.album;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
final public class AlbumDto
{
    private String albumId;
    private String artist;
    private String title;
    private String releaseDate;
    private String albumRating;
    private byte[] albumCover;

    @Override
    public String toString()
    {
        return String.format("Artist: %s, Title: %s, Release Date: %s, Rating: %s, Id: %s", this.artist, this.title, this
                .releaseDate, this.albumRating == null ? "" : this.albumRating, this.albumId);
    }
}
