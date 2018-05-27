package response.recommendation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import response.GenericResponse;

import java.util.List;

@NoArgsConstructor
final public class GetRecommendationsResponse extends GenericResponse
{
    @Getter
    private List<String> recommendedArtists;

    public GetRecommendationsResponse(List<String> recommendedArtists)
    {
        this.setSuccessful(true);
        this.recommendedArtists = recommendedArtists;
    }
}
