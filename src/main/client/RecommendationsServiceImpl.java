package client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import request.recommendation.GetRecommendationsRequest;
import response.recommendation.GetRecommendationsResponse;
import service.RecommendationsService;

@Component
final public class RecommendationsServiceImpl implements RecommendationsService
{
    @Autowired
    private Environment environment;

    @Override
    public GetRecommendationsResponse getRecommendations(GetRecommendationsRequest request)
    {
        GetRecommendationsResponse recommendationsResponse = new GetRecommendationsResponse();

        recommendationsResponse.setSuccessful(false);
        recommendationsResponse.setErrorMessage(environment.getProperty("user.get_recommendations_not_available"));

        return recommendationsResponse;
    }
}
