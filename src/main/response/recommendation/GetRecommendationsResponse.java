package response.recommendation;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
final public class GetRecommendationsResponse
{
    private boolean isSuccessful;
    private String errorMessage;
    private List<String> recommendedArtists;
}
