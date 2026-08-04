package enery.use_case.search_exercise;

import java.util.List;

import enery.entity.exercise.ExerciseSearchResult;

/**
 * Interactor for searching exercise calorie information.
 */
public final class SearchExerciseInteractor
        implements SearchExerciseInputBoundary {

    private final CaloriesBurnedDataAccessInterface dataAccessObject;
    private final SearchExerciseOutputBoundary presenter;

    public SearchExerciseInteractor(
            CaloriesBurnedDataAccessInterface dataAccessObject,
            SearchExerciseOutputBoundary presenter) {

        this.dataAccessObject = dataAccessObject;
        this.presenter = presenter;
    }

    @Override
    public void execute(SearchExerciseInputData inputData) {
        if (inputData.getActivityQuery() == null
                || inputData.getActivityQuery().isBlank()) {

            presenter.prepareFailView(
                    "Enter an exercise name.");
            return;
        }

        if (inputData.getWeightInPounds() < 50.0
                || inputData.getWeightInPounds() > 500.0) {

            presenter.prepareFailView(
                    "Weight must be between 50 and 500 pounds.");
            return;
        }

        if (inputData.getDurationInMinutes() <= 0) {
            presenter.prepareFailView(
                    "Duration must be greater than zero.");
            return;
        }

        try {
            final List<ExerciseSearchResult> results =
                    dataAccessObject.search(
                            inputData.getActivityQuery(),
                            inputData.getWeightInPounds(),
                            inputData.getDurationInMinutes()
                    );

            if (results.isEmpty()) {
                presenter.prepareFailView(
                        "No matching exercises were found.");
            }
            else {
                presenter.prepareSuccessView(
                        new SearchExerciseOutputData(results));
            }
        }
        catch (RuntimeException exception) {
            presenter.prepareFailView(
                    "Unable to search exercises right now.");
        }
    }
}
