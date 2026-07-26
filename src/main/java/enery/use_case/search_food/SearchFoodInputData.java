package enery.use_case.search_food;

/**
 * Input data for searching food nutrition information.
 */
public final class SearchFoodInputData {

    private final String query;

    public SearchFoodInputData(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
