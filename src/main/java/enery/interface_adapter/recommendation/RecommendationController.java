package enery.interface_adapter.recommendation;

import enery.use_case.recommendation.RecommendationInputBoundary;
import enery.use_case.recommendation.RecommendationInputData;

/**
 * Controller for the Recommendation use case.
 * Converts UI input actions into execution calls on the Recommendation Interactor.
 */
public class RecommendationController {

    private final RecommendationInputBoundary recommendationInteractor;

    public RecommendationController(RecommendationInputBoundary recommendationInteractor) {
        this.recommendationInteractor = recommendationInteractor;
    }

    /**
     * Triggers the recommendation generation pipeline for a specific user.
     *
     * @param username The username of the currently logged-in user.
     */
    public void execute(String username) {
        final RecommendationInputData inputData = new RecommendationInputData(username);
        recommendationInteractor.execute(inputData);
    }
}