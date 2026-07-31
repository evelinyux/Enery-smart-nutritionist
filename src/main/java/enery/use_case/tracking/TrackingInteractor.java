package enery.use_case.tracking;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import enery.entity.exercise.ExerciseEntry;
import enery.entity.meal.MealEntry;

/**
 * Calculates daily or weekly nutrition and exercise totals.
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
    public void execute(TrackingInputData inputData) {
        if (inputData.getUsername() == null
                || inputData.getUsername().isBlank()) {

            presenter.prepareFailView(
                    "A logged-in user is required.");
            return;
        }

        if (inputData.getSelectedDate() == null) {
            presenter.prepareFailView(
                    "A date is required.");
            return;
        }

        if (inputData.getPeriod() == null) {
            presenter.prepareFailView(
                    "A tracking period is required.");
            return;
        }

        final LocalDate startDate =
                calculateStartDate(
                        inputData.getSelectedDate(),
                        inputData.getPeriod()
                );

        final LocalDate endDate =
                calculateEndDate(
                        startDate,
                        inputData.getPeriod()
                );

        final List<MealEntry> meals =
                dataAccessObject.getMeals(
                        inputData.getUsername(),
                        startDate,
                        endDate
                );

        final List<ExerciseEntry> exercises =
                dataAccessObject.getExercises(
                        inputData.getUsername(),
                        startDate,
                        endDate
                );

        final List<DailyTrackingSummary> dailySummaries =
                createDailySummaries(
                        startDate,
                        endDate,
                        meals,
                        exercises
                );

        double caloriesConsumed = 0.0;
        double protein = 0.0;
        double carbohydrates = 0.0;
        double fat = 0.0;
        double fiber = 0.0;
        double sugar = 0.0;
        double sodium = 0.0;

        for (MealEntry meal : meals) {
            caloriesConsumed += meal.getTotalCalories();
            protein += meal.getTotalProteinInGrams();
            carbohydrates += meal.getTotalCarbohydratesInGrams();
            fat += meal.getTotalFatInGrams();

            fiber += meal.getTotalFiberInGrams();
            sugar += meal.getTotalSugarInGrams();
            sodium += meal.getTotalSodiumInMilligrams();
        }

        double caloriesBurned = 0.0;

        for (ExerciseEntry exercise : exercises) {
            caloriesBurned += exercise.getCaloriesBurned();
        }

        final double netCalories =
                caloriesConsumed - caloriesBurned;

        final int numberOfDays =
                inputData.getPeriod() == TrackingPeriod.WEEKLY
                        ? 7
                        : 1;

        presenter.prepareSuccessView(
                new TrackingOutputData(
                        inputData.getPeriod(),
                        startDate,
                        endDate,
                        caloriesConsumed,
                        caloriesBurned,
                        netCalories,
                        protein,
                        carbohydrates,
                        fat,
                        fiber,
                        sugar,
                        sodium,
                        caloriesConsumed / numberOfDays,
                        caloriesBurned / numberOfDays,
                        netCalories / numberOfDays,
                        dailySummaries
                )
        );
    }

    /**
     * Calculates the first day of the requested period.
     */
    private static LocalDate calculateStartDate(
            LocalDate selectedDate,
            TrackingPeriod period) {

        if (period == TrackingPeriod.WEEKLY) {
            return selectedDate.with(
                    TemporalAdjusters.previousOrSame(
                            DayOfWeek.MONDAY));
        }

        return selectedDate;
    }

    /**
     * Calculates the final day of the requested peritod.
     */
    private static LocalDate calculateEndDate(
            LocalDate startDate,
            TrackingPeriod period) {

        if (period == TrackingPeriod.WEEKLY) {
            return startDate.plusDays(6);
        }

        return startDate;
    }

    /**
     * Creates one calorie summary for every date in the requested period.
     *
     * @param startDate first date in the period
     * @param endDate final date in the period
     * @param meals meals recorded during the period
     * @param exercises exercises recorded during the period
     * @return daily calorie summaries
     */
    private static List<DailyTrackingSummary> createDailySummaries(
            LocalDate startDate,
            LocalDate endDate,
            List<MealEntry> meals,
            List<ExerciseEntry> exercises) {

        final List<DailyTrackingSummary> summaries =
                new ArrayList<>();

        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            final LocalDate summaryDate = currentDate;

            double consumed = 0.0;

            for (MealEntry meal : meals) {
                if (meal.getDate().equals(summaryDate)) {
                    consumed += meal.getTotalCalories();
                }
            }

            double burned = 0.0;

            for (ExerciseEntry exercise : exercises) {
                if (exercise.getDate().equals(summaryDate)) {
                    burned += exercise.getCaloriesBurned();
                }
            }

            summaries.add(
                    new DailyTrackingSummary(
                            summaryDate,
                            consumed,
                            burned,
                            consumed - burned
                    )
            );

            currentDate = currentDate.plusDays(1);
        }

        return List.copyOf(summaries);
    }
}
