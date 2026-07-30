package enery.entity.user;

import java.time.LocalDate;

/**
 * Represents a user's active weight-goal plan.
 */
public final class WeightGoalPlan {

    private static final int MIN_DURATION_DAYS = 14;
    private static final int MAX_DURATION_DAYS = 180;

    private static final double MAX_TOTAL_CHANGE_RATIO = 0.15;
    private static final double MAX_WEEKLY_CHANGE_RATIO = 0.0125;

    private final LocalDate startDate;
    private final LocalDate endDate;
    private final int durationDays;

    private final double startingWeightInKilograms;
    private final double targetWeightInKilograms;

    private final HealthGoal healthGoal;

    private final double initialDailyCalorieGoal;
    private double currentDailyCalorieGoal;

    /**
     * Creates a weight-goal plan.
     *
     * @param startDate the first date of the plan
     * @param durationDays number of days in the plan
     * @param startingWeightInKilograms starting weight
     * @param targetWeightInKilograms target weight
     * @param healthGoal the user's selected health goal
     * @param initialDailyCalorieGoal initial calorie goal
     */
    public WeightGoalPlan(
            LocalDate startDate,
            int durationDays,
            double startingWeightInKilograms,
            double targetWeightInKilograms,
            HealthGoal healthGoal,
            double initialDailyCalorieGoal) {

        validatePlan(
                startDate,
                durationDays,
                startingWeightInKilograms,
                targetWeightInKilograms,
                healthGoal,
                initialDailyCalorieGoal
        );

        this.startDate = startDate;
        this.durationDays = durationDays;

        // Both the first and last date are included in the plan.
        this.endDate =
                startDate.plusDays(durationDays - 1L);

        this.startingWeightInKilograms =
                startingWeightInKilograms;

        this.targetWeightInKilograms =
                targetWeightInKilograms;

        this.healthGoal = healthGoal;

        this.initialDailyCalorieGoal =
                initialDailyCalorieGoal;

        this.currentDailyCalorieGoal =
                initialDailyCalorieGoal;
    }

    /**
     * Validates the plan configuration.
     */
    private static void validatePlan(
            LocalDate startDate,
            int durationDays,
            double startingWeightInKilograms,
            double targetWeightInKilograms,
            HealthGoal healthGoal,
            double initialDailyCalorieGoal) {

        if (startDate == null) {
            throw new IllegalArgumentException(
                    "A plan start date is required.");
        }

        if (durationDays < MIN_DURATION_DAYS
                || durationDays > MAX_DURATION_DAYS) {

            throw new IllegalArgumentException(
                    "Plan duration must be between "
                            + MIN_DURATION_DAYS
                            + " and "
                            + MAX_DURATION_DAYS
                            + " days.");
        }

        if (startingWeightInKilograms <= 0
                || targetWeightInKilograms <= 0) {

            throw new IllegalArgumentException(
                    "Starting and target weight must be greater than zero.");
        }

        if (healthGoal == null) {
            throw new IllegalArgumentException(
                    "A health goal is required.");
        }

        if (initialDailyCalorieGoal <= 0) {
            throw new IllegalArgumentException(
                    "The daily calorie goal must be greater than zero.");
        }

        validateGoalDirection(
                startingWeightInKilograms,
                targetWeightInKilograms,
                healthGoal
        );

        validateWeightChangeRate(
                startingWeightInKilograms,
                targetWeightInKilograms,
                durationDays
        );
    }

    /**
     * Checks that target weight and health goal agree.
     */
    private static void validateGoalDirection(
            double startingWeightInKilograms,
            double targetWeightInKilograms,
            HealthGoal healthGoal) {

        if (healthGoal == HealthGoal.LOSE_WEIGHT
                && targetWeightInKilograms
                >= startingWeightInKilograms) {

            throw new IllegalArgumentException(
                    "A weight-loss target must be below the starting weight.");
        }

        if (healthGoal == HealthGoal.GAIN_WEIGHT
                && targetWeightInKilograms
                <= startingWeightInKilograms) {

            throw new IllegalArgumentException(
                    "A weight-gain target must be above the starting weight.");
        }

        if (healthGoal == HealthGoal.MAINTAIN_WEIGHT
                && Double.compare(
                targetWeightInKilograms,
                startingWeightInKilograms) != 0) {

            throw new IllegalArgumentException(
                    "A maintenance target must equal the starting weight.");
        }
    }

    /**
     * Rejects plans with an excessive weight-change rate.
     */
    private static void validateWeightChangeRate(
            double startingWeightInKilograms,
            double targetWeightInKilograms,
            int durationDays) {

        final double weightChange =
                Math.abs(
                        targetWeightInKilograms
                                - startingWeightInKilograms);

        final double totalChangeRatio =
                weightChange
                        / startingWeightInKilograms;

        if (totalChangeRatio > MAX_TOTAL_CHANGE_RATIO) {
            throw new IllegalArgumentException(
                    "A single plan cannot change more than 15% "
                            + "of the starting weight.");
        }

        final double durationInWeeks =
                durationDays / 7.0;

        final double weeklyChangeRatio =
                totalChangeRatio
                        / durationInWeeks;

        if (weeklyChangeRatio > MAX_WEEKLY_CHANGE_RATIO) {
            throw new IllegalArgumentException(
                    "The requested weight change is too large "
                            + "for the selected plan duration.");
        }
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getDurationDays() {
        return durationDays;
    }

    public double getStartingWeightInKilograms() {
        return startingWeightInKilograms;
    }

    public double getTargetWeightInKilograms() {
        return targetWeightInKilograms;
    }

    public HealthGoal getHealthGoal() {
        return healthGoal;
    }

    public double getInitialDailyCalorieGoal() {
        return initialDailyCalorieGoal;
    }

    public double getCurrentDailyCalorieGoal() {
        return currentDailyCalorieGoal;
    }

    /**
     * Updates the current daily calorie goal.
     *
     * @param newDailyCalorieGoal the recalculated goal
     */
    public void updateCurrentDailyCalorieGoal(
            double newDailyCalorieGoal) {

        if (newDailyCalorieGoal <= 0) {
            throw new IllegalArgumentException(
                    "The daily calorie goal must be greater than zero.");
        }

        this.currentDailyCalorieGoal =
                newDailyCalorieGoal;
    }
}