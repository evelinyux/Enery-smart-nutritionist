package enery.interface_adapter.search_food;

import enery.use_case.search_food.SearchFoodInputBoundary;
import enery.use_case.search_food.SearchFoodInputData;

/**
 * Converts view input into food-search use-case input.
 */
public final class SearchFoodController {

    private final SearchFoodInputBoundary interactor;

    public SearchFoodController(
            SearchFoodInputBoundary interactor) {

        this.interactor = interactor;
    }

    /**
     * Searches for nutrition information.
     *
     * @param query natural-language food description
     */
    public void execute(String query) {
        interactor.execute(
                new SearchFoodInputData(query));
    }
}
