package enery.use_case.daily_check;

import enery.entity.meal.MealEntry;
import enery.entity.exercise.ExerciseEntry;

import java.util.List;

public final class DailyCheckInteractor implements DailyCheckInputBoundary {

    private final DailyCheckDataAccessInterface dataAccessObject;
    private final DailyCheckOutputBoundary presenter;

    public DailyCheckInteractor(
            DailyCheckDataAccessInterface dataAccessObject,
            DailyCheckOutputBoundary presenter) {
        this.dataAccessObject = dataAccessObject;
        this.presenter = presenter;
    }

    @Override
    public void execute(DailyCheckInputData inputData) {
        final String username = inputData.getUsername();

        if (username == null || username.isBlank()) {
            presenter.prepareFailView("User is not logged in.");
            return;
        }

        // Fetch today's records using your teammate's DAO methods
        final List<MealEntry> todayMeals = dataAccessObject.getTodayMeals(username);
        final List<ExerciseEntry> todayExercises = dataAccessObject.getTodayExercises(username);

        boolean breakfast = false;
        boolean lunch = false;
        boolean dinner = false;

        if (todayMeals != null) {
            for (MealEntry meal : todayMeals) {
                final String type = meal.getMealType(); // e.g. "Breakfast", "Lunch", "Dinner"
                if ("Breakfast".equalsIgnoreCase(type)) {
                    breakfast = true;
                } else if ("Lunch".equalsIgnoreCase(type)) {
                    lunch = true;
                } else if ("Dinner".equalsIgnoreCase(type)) {
                    dinner = true;
                }
            }
        }

        final boolean exercise = todayExercises != null && !todayExercises.isEmpty();

        final DailyCheckOutputData outputData = new DailyCheckOutputData(
                breakfast,
                lunch,
                dinner,
                exercise
        );

        presenter.prepareSuccessView(outputData);
    }
}