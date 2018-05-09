package request.album;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
final public class GetAlbumsGroupedByCriteriaRequest
{
    private String sortingCriteria;
}
