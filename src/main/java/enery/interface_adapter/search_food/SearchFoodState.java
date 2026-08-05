package enery.interface_adapter.search_food;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import enery.entity.meal.FoodItem;

/**
 * Stores the current state of a food search.
 *
 * <p>This state can be reused by meal logging, recommendations,
 * meal planning, or any other view that needs nutrition results.</p>
 */
public final class SearchFoodState {

    private String query = "";
    private List<FoodItem> results = new ArrayList<>();
    private String errorMessage = "";

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<FoodItem> getResults() {
        return Collections.unmodifiableList(results);
    }

    public void setResults(List<FoodItem> results) {
        this.results = new ArrayList<>(results);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
