package enery.use_case.search_exercise;

import java.util.List;

import enery.entity.exercise.ExerciseSearchResult;
import enery.entity.user.UserProfile;

/**
 * Interactor for searching exercise calorie information.
 */
public final class SearchExerciseInteractor
        implements SearchExerciseInputBoundary {

    private static final double POUNDS_PER_KILOGRAM =
            2.2046226218;

    private final CaloriesBurnedDataAccessInterface
            caloriesDataAccess;

    private final SearchExerciseUserDataAccessInterface
            userDataAccess;

    private final SearchExerciseOutputBoundary presenter;

    public SearchExerciseInteractor(
            CaloriesBurnedDataAccessInterface caloriesDataAccess,
            SearchExerciseUserDataAccessInterface userDataAccess,
            SearchExerciseOutputBoundary presenter) {

        this.caloriesDataAccess = caloriesDataAccess;
        this.userDataAccess = userDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(SearchExerciseInputData inputData) {

        if (inputData.getUsername() == null
                || inputData.getUsername().isBlank()) {

            presenter.prepareFailView(
                    "A logged-in user is required."
            );
            return;
        }

        if (inputData.getExerciseName() == null
                || inputData.getExerciseName().isBlank()) {

            presenter.prepareFailView(
                    "Please enter an exercise."
            );
            return;
        }

        if (inputData.getDurationInMinutes() <= 0) {
            presenter.prepareFailView(
                    "Duration must be greater than 0."
            );
            return;
        }

        final UserProfile profile =
                userDataAccess.getUserProfile(
                        inputData.getUsername()
                );

        if (profile == null) {
            presenter.prepareFailView(
                    "Please complete your profile "
                            + "before searching exercises."
            );
            return;
        }

        final double weightInKilograms =
                profile.getWeightInKilograms();

        final double weightInPounds =
                weightInKilograms
                        * POUNDS_PER_KILOGRAM;

        try {
            final List<ExerciseSearchResult> results =
                    caloriesDataAccess.search(
                            inputData.getExerciseName(),
                            weightInPounds,
                            inputData.getDurationInMinutes()
                    );

            final SearchExerciseOutputData outputData =
                    new SearchExerciseOutputData(results);

            presenter.prepareSuccessView(outputData);
        }
        catch (RuntimeException exception) {
            presenter.prepareFailView(
                    "Unable to retrieve exercise information."
            );
        }
    }
}
