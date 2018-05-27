package request.album;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class FindAllUserAlbumRequest
{
    @NotNull
    @NotBlank
    private String userName;
}
