package enery.entity;

import enery.entity.user.HealthGoal;
import enery.entity.user.WeightGoalPlan;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class WeightGoalPlanTest {

    @Test
    void validWeightLossPlanTest() {
        LocalDate startDate =
                LocalDate.of(2026, 8, 8);

        WeightGoalPlan plan =
                new WeightGoalPlan(
                        startDate,
                        60,
                        70.0,
                        65.0,
                        HealthGoal.LOSE_WEIGHT,
                        2000.0
                );

        assertEquals(
                startDate,
                plan.getStartDate());

        assertEquals(
                LocalDate.of(2026, 10, 6),
                plan.getEndDate());

        assertEquals(
                60,
                plan.getDurationDays());

        assertEquals(
                70.0,
                plan.getStartingWeightInKilograms(),
                0.001);

        assertEquals(
                65.0,
                plan.getTargetWeightInKilograms(),
                0.001);

        assertEquals(
                HealthGoal.LOSE_WEIGHT,
                plan.getHealthGoal());

        assertEquals(
                2000.0,
                plan.getInitialDailyCalorieGoal(),
                0.001);

        assertEquals(
                2000.0,
                plan.getCurrentDailyCalorieGoal(),
                0.001);
    }

    @Test
    void validMaintenancePlanTest() {
        WeightGoalPlan plan =
                new WeightGoalPlan(
                        LocalDate.of(2026, 8, 8),
                        30,
                        70.0,
                        70.0,
                        HealthGoal.MAINTAIN_WEIGHT,
                        2200.0
                );

        assertEquals(
                70.0,
                plan.getTargetWeightInKilograms(),
                0.001);

        assertEquals(
                HealthGoal.MAINTAIN_WEIGHT,
                plan.getHealthGoal());
    }

    @Test
    void validWeightGainPlanTest() {
        WeightGoalPlan plan =
                new WeightGoalPlan(
                        LocalDate.of(2026, 8, 8),
                        60,
                        70.0,
                        74.0,
                        HealthGoal.GAIN_WEIGHT,
                        2400.0
                );

        assertEquals(
                74.0,
                plan.getTargetWeightInKilograms(),
                0.001);

        assertEquals(
                HealthGoal.GAIN_WEIGHT,
                plan.getHealthGoal());
    }

    @Test
    void endDateIncludesFirstAndLastDayTest() {
        LocalDate startDate =
                LocalDate.of(2026, 8, 8);

        WeightGoalPlan plan =
                new WeightGoalPlan(
                        startDate,
                        14,
                        70.0,
                        70.0,
                        HealthGoal.MAINTAIN_WEIGHT,
                        2000.0
                );

        assertEquals(
                startDate.plusDays(13),
                plan.getEndDate());
    }

    @Test
    void minimumDurationIsAllowedTest() {
        assertDoesNotThrow(() ->
                new WeightGoalPlan(
                        LocalDate.now(),
                        14,
                        70.0,
                        70.0,
                        HealthGoal.MAINTAIN_WEIGHT,
                        2000.0
                ));
    }

    @Test
    void maximumDurationIsAllowedTest() {
        assertDoesNotThrow(() ->
                new WeightGoalPlan(
                        LocalDate.now(),
                        180,
                        70.0,
                        70.0,
                        HealthGoal.MAINTAIN_WEIGHT,
                        2000.0
                ));
    }

    @Test
    void nullStartDateTest() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> new WeightGoalPlan(
                                null,
                                30,
                                70.0,
                                70.0,
                                HealthGoal.MAINTAIN_WEIGHT,
                                2000.0
                        ));

        assertEquals(
                "A plan start date is required.",
                exception.getMessage());
    }

    @Test
    void durationTooShortTest() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> new WeightGoalPlan(
                                LocalDate.now(),
                                13,
                                70.0,
                                70.0,
                                HealthGoal.MAINTAIN_WEIGHT,
                                2000.0
                        ));

        assertEquals(
                "Plan duration must be between 14 and 180 days.",
                exception.getMessage());
    }

    @Test
    void durationTooLongTest() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> new WeightGoalPlan(
                                LocalDate.now(),
                                181,
                                70.0,
                                70.0,
                                HealthGoal.MAINTAIN_WEIGHT,
                                2000.0
                        ));

        assertEquals(
                "Plan duration must be between 14 and 180 days.",
                exception.getMessage());
    }

    @Test
    void invalidStartingWeightTest() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> new WeightGoalPlan(
                                LocalDate.now(),
                                30,
                                0.0,
                                70.0,
                                HealthGoal.MAINTAIN_WEIGHT,
                                2000.0
                        ));

        assertEquals(
                "Starting and target weight must be greater than zero.",
                exception.getMessage());
    }

    @Test
    void invalidTargetWeightTest() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> new WeightGoalPlan(
                                LocalDate.now(),
                                30,
                                70.0,
                                0.0,
                                HealthGoal.MAINTAIN_WEIGHT,
                                2000.0
                        ));

        assertEquals(
                "Starting and target weight must be greater than zero.",
                exception.getMessage());
    }

    @Test
    void nullHealthGoalTest() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> new WeightGoalPlan(
                                LocalDate.now(),
                                30,
                                70.0,
                                70.0,
                                null,
                                2000.0
                        ));

        assertEquals(
                "A health goal is required.",
                exception.getMessage());
    }

    @Test
    void invalidInitialDailyCalorieGoalTest() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> new WeightGoalPlan(
                                LocalDate.now(),
                                30,
                                70.0,
                                70.0,
                                HealthGoal.MAINTAIN_WEIGHT,
                                0.0
                        ));

        assertEquals(
                "The daily calorie goal must be greater than zero.",
                exception.getMessage());
    }

    @Test
    void weightLossTargetMustBeLowerTest() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> new WeightGoalPlan(
                                LocalDate.now(),
                                60,
                                70.0,
                                70.0,
                                HealthGoal.LOSE_WEIGHT,
                                2000.0
                        ));

        assertEquals(
                "A weight-loss target must be below the starting weight.",
                exception.getMessage());
    }

    @Test
    void weightGainTargetMustBeHigherTest() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> new WeightGoalPlan(
                                LocalDate.now(),
                                60,
                                70.0,
                                70.0,
                                HealthGoal.GAIN_WEIGHT,
                                2000.0
                        ));

        assertEquals(
                "A weight-gain target must be above the starting weight.",
                exception.getMessage());
    }

    @Test
    void maintenanceTargetMustEqualStartingWeightTest() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> new WeightGoalPlan(
                                LocalDate.now(),
                                60,
                                70.0,
                                69.0,
                                HealthGoal.MAINTAIN_WEIGHT,
                                2000.0
                        ));

        assertEquals(
                "A maintenance target must equal the starting weight.",
                exception.getMessage());
    }

    @Test
    void totalWeightChangeCannotExceedFifteenPercentTest() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> new WeightGoalPlan(
                                LocalDate.now(),
                                180,
                                70.0,
                                55.0,
                                HealthGoal.LOSE_WEIGHT,
                                2000.0
                        ));

        assertEquals(
                "A single plan cannot change more than 15% "
                        + "of the starting weight.",
                exception.getMessage());
    }

    @Test
    void weeklyWeightChangeCannotBeTooLargeTest() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> new WeightGoalPlan(
                                LocalDate.now(),
                                14,
                                70.0,
                                63.0,
                                HealthGoal.LOSE_WEIGHT,
                                2000.0
                        ));

        assertEquals(
                "The requested weight change is too large "
                        + "for the selected plan duration.",
                exception.getMessage());
    }

    @Test
    void updateCurrentDailyCalorieGoalTest() {
        WeightGoalPlan plan =
                new WeightGoalPlan(
                        LocalDate.now(),
                        30,
                        70.0,
                        70.0,
                        HealthGoal.MAINTAIN_WEIGHT,
                        2000.0
                );

        plan.updateCurrentDailyCalorieGoal(
                2100.0);

        assertEquals(
                2000.0,
                plan.getInitialDailyCalorieGoal(),
                0.001);

        assertEquals(
                2100.0,
                plan.getCurrentDailyCalorieGoal(),
                0.001);
    }

    @Test
    void invalidUpdatedDailyCalorieGoalTest() {
        WeightGoalPlan plan =
                new WeightGoalPlan(
                        LocalDate.now(),
                        30,
                        70.0,
                        70.0,
                        HealthGoal.MAINTAIN_WEIGHT,
                        2000.0
                );

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> plan
                                .updateCurrentDailyCalorieGoal(0.0));

        assertEquals(
                "The daily calorie goal must be greater than zero.",
                exception.getMessage());

        assertEquals(
                2000.0,
                plan.getCurrentDailyCalorieGoal(),
                0.001);
    }
}