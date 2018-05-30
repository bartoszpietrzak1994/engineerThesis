package request.album;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
final public class GetAlbumsOrderedByCriteriaRequest
{
    @NotNull
    @NotBlank
    private String sortingCriteria;
}
