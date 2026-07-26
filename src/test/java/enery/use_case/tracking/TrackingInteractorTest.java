package enery.use_case.tracking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import enery.entity.ExerciseEntry;
import enery.entity.FoodItem;
import enery.entity.MealEntry;
import enery.entity.MealType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class TrackingInteractorTest {

    @Test
    void calculatesDailyTotals() {
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

        final LocalDate date =
                LocalDate.of(2026, 7, 26);

        final MealEntry meal = new MealEntry(
                apple,
                MealType.BREAKFAST,
                2.0,
                LocalDateTime.of(
                        2026, 7, 26, 8, 0)
        );

        final ExerciseEntry exercise =
                new ExerciseEntry(
                        "Cycling",
                        30,
                        100.0,
                        LocalDateTime.of(
                                2026, 7, 26, 18, 0)
                );

        final TrackingDataAccessInterface repository =
                new TrackingDataAccessInterface() {
                    @Override
                    public List<MealEntry> getMeals(
                            String username,
                            LocalDate requestedDate) {

                        return List.of(meal);
                    }

                    @Override
                    public List<ExerciseEntry> getExercises(
                            String username,
                            LocalDate requestedDate) {

                        return List.of(exercise);
                    }
                };

        final TrackingOutputBoundary presenter =
                new TrackingOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            TrackingOutputData outputData) {

                        assertEquals(
                                date,
                                outputData.getDate());
                        assertEquals(
                                190.0,
                                outputData.getCaloriesConsumed());
                        assertEquals(
                                100.0,
                                outputData.getCaloriesBurned());
                        assertEquals(
                                90.0,
                                outputData.getNetCalories());
                        assertEquals(
                                1.0,
                                outputData.getProteinInGrams());
                        assertEquals(
                                50.0,
                                outputData
                                        .getCarbohydratesInGrams());
                        assertEquals(
                                0.6,
                                outputData.getFatInGrams(),
                                0.0001);
                    }

                    @Override
                    public void prepareFailView(
                            String errorMessage) {

                        fail("Unexpected tracking failure.");
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
                        date
                )
        );
    }
}