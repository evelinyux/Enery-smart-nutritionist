package enery.use_case.search_food;

/**
 * Output boundary for food searches.
 */
public interface SearchFoodOutputBoundary {

    void prepareSuccessView(SearchFoodOutputData outputData);

    void prepareFailView(String errorMessage);
}
