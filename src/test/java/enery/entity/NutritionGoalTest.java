package enery.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NutritionGoalTest {

    @Test
    void storesDailyNutritionTargets() {
        final NutritionGoal goal = new NutritionGoal(
                2200.0
        );

        assertEquals(2200.0, goal.getDailyCalories());
    }

    @Test
    void rejectsNegativeCalories() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new NutritionGoal(
                        -100.0
                )
        );
    }
}
