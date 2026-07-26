package enery.interface_adapter.search_food;

import enery.interface_adapter.ViewModel;

/**
 * View model shared by views that perform food searches.
 */
public final class SearchFoodViewModel
        extends ViewModel<SearchFoodState> {

    public static final String VIEW_NAME = "search food";

    public SearchFoodViewModel() {
        super(VIEW_NAME);
        setState(new SearchFoodState());
    }
}
