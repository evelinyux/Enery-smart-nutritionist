package enery.use_case.search_food;

import java.util.List;

import enery.entity.meal.FoodItem;

/**
 * Coordinates food nutrition searches.
 *
 * <p>This class contains application rules only. It does not know whether the
 * data comes from API Ninjas, Edamam, a local database, or a test double.</p>
 */
public final class SearchFoodInteractor
        implements SearchFoodInputBoundary {

    private final NutritionDataAccessInterface dataAccessObject;
    private final SearchFoodOutputBoundary presenter;

    public SearchFoodInteractor(
            NutritionDataAccessInterface dataAccessObject,
            SearchFoodOutputBoundary presenter) {

        this.dataAccessObject = dataAccessObject;
        this.presenter = presenter;
    }

    @Override
    public void execute(SearchFoodInputData inputData) {
        if (inputData.getQuery() == null
                || inputData.getQuery().isBlank()) {

            presenter.prepareFailView(
                    "Enter a food name or description.");
            return;
        }

        try {
            final List<FoodItem> results =
                    dataAccessObject.search(
                            inputData.getQuery().trim());

            if (results.isEmpty()) {
                presenter.prepareFailView(
                        "No matching foods were found.");
            }
            else {
                presenter.prepareSuccessView(
                        new SearchFoodOutputData(results));
            }
        }
        catch (RuntimeException exception) {
            System.out.println(exception.getMessage());
            presenter.prepareFailView(
                    "Unable to search for nutrition information.");
        }
    }
}
