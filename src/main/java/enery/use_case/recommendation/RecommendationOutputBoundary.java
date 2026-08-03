package enery.use_case.recommendation;

public interface RecommendationOutputBoundary {
    void prepareSuccessView(RecommendationOutputData outputData);
    void prepareFailView(String error);
}
