package enery.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DailyLogTest {

    @Test
    void calculatesDailyTotals() {
        final DailyLog dailyLog =
                new DailyLog(LocalDate.of(2026, 7, 26));

        final FoodItem rice = new FoodItem(
                "Rice",
                100.0,
                200.0,
                4.0,
                45.0,
                0.5,
                1.0,
                0.0,
                5.0
        );

        final FoodItem chicken = new FoodItem(
                "Chicken breast",
                100.0,
                250.0,
                40.0,
                0.0,
                8.0,
                0.0,
                0.0,
                100.0
        );

        dailyLog.addMealEntry(new MealEntry(
                rice,
                MealType.LUNCH,
                1.5,
                LocalDateTime.of(2026, 7, 26, 12, 0)
        ));

        dailyLog.addMealEntry(new MealEntry(
                chicken,
                MealType.DINNER,
                1.0,
                LocalDateTime.of(2026, 7, 26, 18, 0)
        ));

        dailyLog.addExerciseEntry(new ExerciseEntry(
                "Cycling",
                45,
                300.0,
                LocalDateTime.of(2026, 7, 26, 16, 0)
        ));

        assertEquals(550.0, dailyLog.getTotalCaloriesConsumed());
        assertEquals(46.0, dailyLog.getTotalProteinInGrams());
        assertEquals(67.5, dailyLog.getTotalCarbohydratesInGrams());
        assertEquals(8.75, dailyLog.getTotalFatInGrams());
        assertEquals(300.0, dailyLog.getTotalCaloriesBurned());
        assertEquals(250.0, dailyLog.getNetCalories());
    }

    @Test
    void returnedEntryListsCannotBeModifiedDirectly() {
        final DailyLog dailyLog = new DailyLog(LocalDate.now());

        assertThrows(
                UnsupportedOperationException.class,
                () -> dailyLog.getMealEntries().clear()
        );

        assertThrows(
                UnsupportedOperationException.class,
                () -> dailyLog.getExerciseEntries().clear()
        );
    }
}
