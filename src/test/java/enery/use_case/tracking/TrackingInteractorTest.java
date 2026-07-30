package enery.use_case.tracking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import enery.entity.exercise.ExerciseEntry;
import enery.entity.meal.FoodItem;
import enery.entity.meal.MealEntry;
import enery.entity.meal.MealType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class TrackingInteractorTest {

    @Test
    void calculatesWeeklyTotals() {
        final LocalDate monday =
                LocalDate.of(2026, 7, 20);

        final LocalDate sunday =
                LocalDate.of(2026, 7, 26);

        final FoodItem apple = new FoodItem(
                "Apple",
                100.0,
                95.0,
                0.5,
                25.0,
                0.3,
                4.4,
                19.0,
                2.0
        );

        final MealEntry mondayMeal =
                new MealEntry(
                        apple,
                        MealType.BREAKFAST,
                        2.0,
                        LocalDateTime.of(
                                2026,
                                7,
                                20,
                                8,
                                0)
                );

        final MealEntry tuesdayMeal =
                new MealEntry(
                        apple,
                        MealType.SNACK,
                        1.0,
                        LocalDateTime.of(
                                2026,
                                7,
                                21,
                                15,
                                0)
                );

        final ExerciseEntry mondayExercise =
                new ExerciseEntry(
                        "Cycling",
                        30,
                        100.0,
                        LocalDateTime.of(
                                2026,
                                7,
                                20,
                                18,
                                0)
                );

        final TrackingDataAccessInterface repository =
                new TrackingDataAccessInterface() {
                    @Override
                    public List<MealEntry> getMeals(
                            String username,
                            LocalDate startDate,
                            LocalDate endDate) {

                        assertEquals("Paul", username);
                        assertEquals(monday, startDate);
                        assertEquals(sunday, endDate);

                        return List.of(
                                mondayMeal,
                                tuesdayMeal);
                    }

                    @Override
                    public List<ExerciseEntry> getExercises(
                            String username,
                            LocalDate startDate,
                            LocalDate endDate) {

                        assertEquals("Paul", username);
                        assertEquals(monday, startDate);
                        assertEquals(sunday, endDate);

                        return List.of(mondayExercise);
                    }
                };

        final TrackingOutputBoundary presenter =
                new TrackingOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            TrackingOutputData outputData) {

                        assertEquals(
                                TrackingPeriod.WEEKLY,
                                outputData.getPeriod());

                        assertEquals(
                                monday,
                                outputData.getStartDate());

                        assertEquals(
                                sunday,
                                outputData.getEndDate());

                        assertEquals(
                                285.0,
                                outputData
                                        .getCaloriesConsumed());

                        assertEquals(
                                100.0,
                                outputData
                                        .getCaloriesBurned());

                        assertEquals(
                                185.0,
                                outputData.getNetCalories());

                        assertEquals(
                                1.5,
                                outputData.getProteinInGrams());

                        assertEquals(
                                75.0,
                                outputData
                                        .getCarbohydratesInGrams());

                        assertEquals(
                                0.9,
                                outputData.getFatInGrams(),
                                0.0001);

                        assertEquals(
                                285.0 / 7.0,
                                outputData
                                        .getAverageCaloriesConsumed(),
                                0.0001);

                        assertEquals(
                                100.0 / 7.0,
                                outputData
                                        .getAverageCaloriesBurned(),
                                0.0001);

                        assertEquals(
                                185.0 / 7.0,
                                outputData
                                        .getAverageNetCalories(),
                                0.0001);

                        assertEquals(
                                7,
                                outputData
                                        .getDailySummaries()
                                        .size());

                        assertEquals(
                                190.0,
                                outputData
                                        .getDailySummaries()
                                        .get(0)
                                        .getCaloriesConsumed());

                        assertEquals(
                                100.0,
                                outputData
                                        .getDailySummaries()
                                        .get(0)
                                        .getCaloriesBurned());

                        assertEquals(
                                95.0,
                                outputData
                                        .getDailySummaries()
                                        .get(1)
                                        .getCaloriesConsumed());
                    }

                    @Override
                    public void prepareFailView(
                            String errorMessage) {

                        fail(
                                "Unexpected tracking failure: "
                                        + errorMessage);
                    }
                };

        final TrackingInputBoundary interactor =
                new TrackingInteractor(
                        repository,
                        presenter
                );

        interactor.execute(
                new TrackingInputData(
                        "Paul",
                        LocalDate.of(2026, 7, 23),
                        TrackingPeriod.WEEKLY
                )
        );
    }

    @Test
    void calculatesDailyTotals() {
        final LocalDate date =
                LocalDate.of(2026, 7, 26);

        final FoodItem apple = new FoodItem(
                "Apple",
                100.0,
                95.0,
                0.5,
                25.0,
                0.3,
                4.4,
                19.0,
                2.0
        );

        final MealEntry meal =
                new MealEntry(
                        apple,
                        MealType.BREAKFAST,
                        2.0,
                        LocalDateTime.of(
                                2026,
                                7,
                                26,
                                8,
                                0)
                );

        final TrackingDataAccessInterface repository =
                new TrackingDataAccessInterface() {
                    @Override
                    public List<MealEntry> getMeals(
                            String username,
                            LocalDate startDate,
                            LocalDate endDate) {

                        assertEquals(date, startDate);
                        assertEquals(date, endDate);
                        return List.of(meal);
                    }

                    @Override
                    public List<ExerciseEntry> getExercises(
                            String username,
                            LocalDate startDate,
                            LocalDate endDate) {

                        return List.of();
                    }
                };

        final TrackingOutputBoundary presenter =
                new TrackingOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            TrackingOutputData outputData) {

                        assertEquals(
                                TrackingPeriod.DAILY,
                                outputData.getPeriod());

                        assertEquals(
                                190.0,
                                outputData
                                        .getCaloriesConsumed());

                        assertEquals(
                                190.0,
                                outputData
                                        .getAverageCaloriesConsumed());

                        assertEquals(
                                1,
                                outputData
                                        .getDailySummaries()
                                        .size());
                    }

                    @Override
                    public void prepareFailView(
                            String errorMessage) {

                        fail(
                                "Unexpected tracking failure.");
                    }
                };

        final TrackingInputBoundary interactor =
                new TrackingInteractor(
                        repository,
                        presenter
                );

        interactor.execute(
                new TrackingInputData(
                        "Paul",
                        date,
                        TrackingPeriod.DAILY
                )
        );
    }
}
