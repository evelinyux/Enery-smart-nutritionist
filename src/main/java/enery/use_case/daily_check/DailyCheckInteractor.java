package enery.use_case.daily_check;

import java.time.LocalDate;

import enery.entity.meal.MealType;

/**
 * Interactor for the Daily Check use case.
 * Determines logged vs. missing entries for today's meals and exercise.
 */
public class DailyCheckInteractor implements DailyCheckInputBoundary {

    private final DailyCheckMealDataAccessInterface mealDataAccessObject;
    private final DailyCheckExerciseDataAccessInterface exerciseDataAccessObject;
    private final DailyCheckOutputBoundary presenter;

    public DailyCheckInteractor(
            DailyCheckMealDataAccessInterface mealDataAccessObject,
            DailyCheckExerciseDataAccessInterface exerciseDataAccessObject,
            DailyCheckOutputBoundary presenter) {
        this.mealDataAccessObject = mealDataAccessObject;
        this.exerciseDataAccessObject = exerciseDataAccessObject;
        this.presenter = presenter;
    }

    @Override
    public void execute(DailyCheckInputData inputData) {
        final String username = inputData.getUsername();

        if (username == null || username.isBlank()) {
            presenter.prepareFailView("User is not logged in.");
            return;
        }

        final LocalDate today = LocalDate.now();

        // Query meal logs for today
        final boolean breakfast = mealDataAccessObject.hasMeal(
                username,
                today,
                MealType.BREAKFAST
        );

        final boolean lunch = mealDataAccessObject.hasMeal(
                username,
                today,
                MealType.LUNCH
        );

        final boolean dinner = mealDataAccessObject.hasMeal(
                username,
                today,
                MealType.DINNER
        );

        // Query exercise logs for today
        final boolean exercise = exerciseDataAccessObject.hasExercise(
                username,
                today
        );

        final DailyCheckOutputData outputData = new DailyCheckOutputData(
                breakfast,
                lunch,
                dinner,
                exercise
        );

        presenter.prepareSuccessView(outputData);
    }
}