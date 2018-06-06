package service;

import org.apache.commons.lang3.RandomStringUtils;
import request.recommendation.GetRecommendationsRequest;
import response.recommendation.GetRecommendationsResponse;

import java.util.Arrays;

public class TestRecommendationsService implements RecommendationsService
{
    @Override
    public GetRecommendationsResponse getRecommendations(GetRecommendationsRequest request)
    {
        return new GetRecommendationsResponse(Arrays.asList(
                RandomStringUtils.randomAlphabetic(5),
                RandomStringUtils.randomAlphabetic(5),
                RandomStringUtils.randomAlphabetic(5),
                RandomStringUtils.randomAlphabetic(5),
                RandomStringUtils.randomAlphabetic(5))
        );
    }
}
