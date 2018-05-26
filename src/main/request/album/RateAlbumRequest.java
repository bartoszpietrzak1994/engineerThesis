package request.album;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class RateAlbumRequest
{
    @NotNull
    @NotBlank
    private String albumId;

    @NotNull
    @NotBlank
    private String albumRating;
}
