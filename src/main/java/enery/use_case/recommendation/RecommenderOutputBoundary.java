package enery.use_case.recommendation;

public interface RecommenderOutputBoundary {
    void prepareSuccessView(RecommenderOutputData outputData);
    void prepareFailView(String error);
}
