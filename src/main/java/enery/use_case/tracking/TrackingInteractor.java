package enery.use_case.tracking;

import java.util.List;

import enery.entity.ExerciseEntry;
import enery.entity.MealEntry;

/**
 * Calculates daily nutrition and exercise totals.
 */
public final class TrackingInteractor
        implements TrackingInputBoundary {

    private final TrackingDataAccessInterface dataAccessObject;
    private final TrackingOutputBoundary presenter;

    public TrackingInteractor(
            TrackingDataAccessInterface dataAccessObject,
            TrackingOutputBoundary presenter) {

        this.dataAccessObject = dataAccessObject;
        this.presenter = presenter;
    }

    @Override
    public void execute(
            TrackingInputData inputData) {

        if (inputData.getUsername() == null
                || inputData.getUsername().isBlank()) {

            presenter.prepareFailView(
                    "A logged-in user is required.");
            return;
        }

        if (inputData.getDate() == null) {
            presenter.prepareFailView(
                    "A date is required.");
            return;
        }

        final List<MealEntry> meals =
                dataAccessObject.getMeals(
                        inputData.getUsername(),
                        inputData.getDate()
                );

        final List<ExerciseEntry> exercises =
                dataAccessObject.getExercises(
                        inputData.getUsername(),
                        inputData.getDate()
                );

        double caloriesConsumed = 0.0;
        double protein = 0.0;
        double carbohydrates = 0.0;
        double fat = 0.0;

        for (MealEntry meal : meals) {
            caloriesConsumed += meal.getTotalCalories();
            protein += meal.getTotalProteinInGrams();
            carbohydrates +=
                    meal.getTotalCarbohydratesInGrams();
            fat += meal.getTotalFatInGrams();
        }

        double caloriesBurned = 0.0;

        for (ExerciseEntry exercise : exercises) {
            caloriesBurned +=
                    exercise.getCaloriesBurned();
        }

        final double netCalories =
                caloriesConsumed - caloriesBurned;

        presenter.prepareSuccessView(
                new TrackingOutputData(
                        inputData.getDate(),
                        caloriesConsumed,
                        caloriesBurned,
                        netCalories,
                        protein,
                        carbohydrates,
                        fat
                )
        );
    }
}
