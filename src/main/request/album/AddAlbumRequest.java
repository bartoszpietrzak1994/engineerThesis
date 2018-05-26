package request.album;

import lombok.Data;
import lombok.NoArgsConstructor;
import model.album.AlbumRating;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@Data
public class AddAlbumRequest
{
    @NotNull
    @NotBlank
    private String artist;

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @NotBlank
    private String userName;

    private LocalDate releaseDate;
    private AlbumRating albumRating;
    private byte[] albumCover;

}
