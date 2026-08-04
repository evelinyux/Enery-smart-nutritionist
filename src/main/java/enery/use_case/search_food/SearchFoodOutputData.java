package enery.use_case.search_food;

import java.util.List;

import enery.entity.meal.FoodItem;

/**
 * Output data containing food-search results.
 */
public final class SearchFoodOutputData {

    private final List<FoodItem> results;

    public SearchFoodOutputData(List<FoodItem> results) {
        this.results = List.copyOf(results);
    }

    public List<FoodItem> getResults() {
        return results;
    }
}
