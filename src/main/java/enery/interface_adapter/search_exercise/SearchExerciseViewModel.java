package enery.interface_adapter.search_exercise;

import enery.interface_adapter.ViewModel;

/**
 * View model for the exercise-search screen.
 */
public final class SearchExerciseViewModel
        extends ViewModel<SearchExerciseState> {

    public static final String VIEW_NAME = "search exercise";

    public SearchExerciseViewModel() {
        super(VIEW_NAME);
        setState(new SearchExerciseState());
    }
}
