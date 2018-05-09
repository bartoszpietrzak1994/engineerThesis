package request.album;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
final public class GetAlbumsOrderedByCriteriaRequest
{
    private String sortingCriteria;
}
