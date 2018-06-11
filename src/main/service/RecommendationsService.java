package service;

import request.recommendation.GetRecommendationsRequest;
import response.recommendation.GetRecommendationsResponse;

import javax.validation.Valid;

public interface RecommendationsService
{
    GetRecommendationsResponse getRecommendations(@Valid GetRecommendationsRequest request);
}
