package enery.entity;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for WeightGoalPlan.
 */
class WeightGoalPlanTest {

    private static final LocalDate START_DATE =
            LocalDate.of(2026, 7, 30);

    private static final double INITIAL_CALORIE_GOAL =
            2100.0;

    private static final double TOLERANCE =
            0.001;

    @Test
    void validWeightLossPlanTest() {
        final WeightGoalPlan plan =
                new WeightGoalPlan(
                        START_DATE,
                        70,
                        65.0,
                        62.0,
                        HealthGoal.LOSE_WEIGHT,
                        INITIAL_CALORIE_GOAL
                );

        assertEquals(
                START_DATE,
                plan.getStartDate());

        assertEquals(
                START_DATE.plusDays(69),
                plan.getEndDate());

        assertEquals(
                70,
                plan.getDurationDays());

        assertEquals(
                65.0,
                plan.getStartingWeightInKilograms(),
                TOLERANCE);

        assertEquals(
                62.0,
                plan.getTargetWeightInKilograms(),
                TOLERANCE);

        assertEquals(
                HealthGoal.LOSE_WEIGHT,
                plan.getHealthGoal());

        assertEquals(
                INITIAL_CALORIE_GOAL,
                plan.getInitialDailyCalorieGoal(),
                TOLERANCE);

        assertEquals(
                INITIAL_CALORIE_GOAL,
                plan.getCurrentDailyCalorieGoal(),
                TOLERANCE);
    }

    @Test
    void validMaintenancePlanTest() {
        final WeightGoalPlan plan =
                new WeightGoalPlan(
                        START_DATE,
                        30,
                        65.0,
                        65.0,
                        HealthGoal.MAINTAIN_WEIGHT,
                        INITIAL_CALORIE_GOAL
                );

        assertEquals(
                65.0,
                plan.getTargetWeightInKilograms(),
                TOLERANCE);
    }

    @Test
    void durationTooShortTest() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new WeightGoalPlan(
                        START_DATE,
                        7,
                        65.0,
                        64.0,
                        HealthGoal.LOSE_WEIGHT,
                        INITIAL_CALORIE_GOAL
                )
        );
    }

    @Test
    void durationTooLongTest() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new WeightGoalPlan(
                        START_DATE,
                        181,
                        65.0,
                        64.0,
                        HealthGoal.LOSE_WEIGHT,
                        INITIAL_CALORIE_GOAL
                )
        );
    }

    @Test
    void excessiveTotalWeightChangeTest() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new WeightGoalPlan(
                        START_DATE,
                        180,
                        65.0,
                        55.0,
                        HealthGoal.LOSE_WEIGHT,
                        INITIAL_CALORIE_GOAL
                )
        );
    }

    @Test
    void excessiveWeeklyWeightChangeTest() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new WeightGoalPlan(
                        START_DATE,
                        14,
                        65.0,
                        60.0,
                        HealthGoal.LOSE_WEIGHT,
                        INITIAL_CALORIE_GOAL
                )
        );
    }

    @Test
    void weightLossDirectionMismatchTest() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new WeightGoalPlan(
                        START_DATE,
                        60,
                        65.0,
                        66.0,
                        HealthGoal.LOSE_WEIGHT,
                        INITIAL_CALORIE_GOAL
                )
        );
    }

    @Test
    void weightGainDirectionMismatchTest() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new WeightGoalPlan(
                        START_DATE,
                        60,
                        65.0,
                        64.0,
                        HealthGoal.GAIN_WEIGHT,
                        INITIAL_CALORIE_GOAL
                )
        );
    }

    @Test
    void maintenanceDirectionMismatchTest() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new WeightGoalPlan(
                        START_DATE,
                        60,
                        65.0,
                        64.0,
                        HealthGoal.MAINTAIN_WEIGHT,
                        INITIAL_CALORIE_GOAL
                )
        );
    }

    @Test
    void updateCurrentDailyCalorieGoalTest() {
        final WeightGoalPlan plan =
                new WeightGoalPlan(
                        START_DATE,
                        70,
                        65.0,
                        62.0,
                        HealthGoal.LOSE_WEIGHT,
                        INITIAL_CALORIE_GOAL
                );

        plan.updateCurrentDailyCalorieGoal(
                2050.0);

        assertEquals(
                INITIAL_CALORIE_GOAL,
                plan.getInitialDailyCalorieGoal(),
                TOLERANCE);

        assertEquals(
                2050.0,
                plan.getCurrentDailyCalorieGoal(),
                TOLERANCE);
    }

    @Test
    void invalidUpdatedDailyCalorieGoalTest() {
        final WeightGoalPlan plan =
                new WeightGoalPlan(
                        START_DATE,
                        70,
                        65.0,
                        62.0,
                        HealthGoal.LOSE_WEIGHT,
                        INITIAL_CALORIE_GOAL
                );

        assertThrows(
                IllegalArgumentException.class,
                () -> plan.updateCurrentDailyCalorieGoal(
                        0.0)
        );
    }
}