package enery.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NutritionGoalTest {

    @Test
    void storesDailyNutritionTargets() {
        final NutritionGoal goal = new NutritionGoal(
                2200.0,
                140.0,
                250.0,
                70.0
        );

        assertEquals(2200.0, goal.getDailyCalories());
        assertEquals(140.0, goal.getProteinInGrams());
        assertEquals(250.0, goal.getCarbohydratesInGrams());
        assertEquals(70.0, goal.getFatInGrams());
    }

    @Test
    void rejectsNegativeCalories() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new NutritionGoal(
                        -100.0,
                        140.0,
                        250.0,
                        70.0
                )
        );
    }

    @Test
    void rejectsNegativeMacronutrients() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new NutritionGoal(
                        2200.0,
                        -10.0,
                        250.0,
                        70.0
                )
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> new NutritionGoal(
                        2200.0,
                        140.0,
                        -20.0,
                        70.0
                )
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> new NutritionGoal(
                        2200.0,
                        140.0,
                        250.0,
                        -5.0
                )
        );
    }
}
