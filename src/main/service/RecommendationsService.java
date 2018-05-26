package service;

import request.recommendation.GetRecommendationsRequest;
import response.recommendation.GetRecommendationsResponse;

public interface RecommendationsService
{
    GetRecommendationsResponse getRecommendations(GetRecommendationsRequest request);
}
