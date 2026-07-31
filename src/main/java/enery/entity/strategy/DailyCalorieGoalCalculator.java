package enery.entity.strategy;

import enery.entity.user.ActivityLevel;
import enery.entity.user.HealthGoal;
import enery.entity.user.UserProfile;

/**
 * Calculates an estimated daily calorie goal from a user profile.
 */
public final class DailyCalorieGoalCalculator {

    private static final double MALE_ADJUSTMENT = 5.0;
    private static final double FEMALE_ADJUSTMENT = -161.0;

    private static final double UNKNOWN_ADJUSTMENT =
            (MALE_ADJUSTMENT + FEMALE_ADJUSTMENT) / 2.0;

    /**
     * Calculates the estimated daily calorie goal.
     *
     * @param userProfile the user's profile
     * @return estimated calories per day
     */
    public Double calculate(UserProfile userProfile) {

        final double basalMetabolicRate =
                calculateBasalMetabolicRate(userProfile);

        final double maintenanceCalories =
                basalMetabolicRate
                        * getActivityMultiplier(
                        userProfile.getActivityLevel());

        final CalorieTargetStrategy strategy =
                getCalorieTargetStrategy(
                        userProfile.getHealthGoal());

        return strategy.calculateTarget(
                maintenanceCalories);
    }

    /**
     * Calculates the user's estimated basal metabolic rate.
     *
     * @param userProfile the user's profile
     * @return estimated basal metabolic rate
     */
    private double calculateBasalMetabolicRate(
            UserProfile userProfile) {

        final double sexAdjustment;

        switch (userProfile.getSex()) {
            case MALE:
                sexAdjustment = MALE_ADJUSTMENT;
                break;

            case FEMALE:
                sexAdjustment = FEMALE_ADJUSTMENT;
                break;

            case UNKNOWN:
                sexAdjustment = UNKNOWN_ADJUSTMENT;
                break;

            default:
                throw new IllegalArgumentException(
                        "Unsupported sex option.");
        }

        return 10.0
                * userProfile.getWeightInKilograms()
                + 6.25
                * userProfile.getHeightInCentimetres()
                - 5.0
                * userProfile.getAge()
                + sexAdjustment;
    }

    /**
     * Returns the multiplier for the user's activity level.
     *
     * @param activityLevel the user's activity level
     * @return activity multiplier
     */
    private double getActivityMultiplier(
            ActivityLevel activityLevel) {

        switch (activityLevel) {
            case SEDENTARY:
                return 1.2;

            case LIGHTLY_ACTIVE:
                return 1.375;

            case MODERATELY_ACTIVE:
                return 1.55;

            case VERY_ACTIVE:
                return 1.725;

            case EXTREMELY_ACTIVE:
                return 1.9;

            default:
                throw new IllegalArgumentException(
                        "Unsupported activity level.");
        }
    }

    /**
     * Selects the strategy matching the user's health goal.
     *
     * @param healthGoal the user's health goal
     * @return calorie-target strategy
     */
    private CalorieTargetStrategy getCalorieTargetStrategy(
            HealthGoal healthGoal) {

        switch (healthGoal) {
            case LOSE_WEIGHT:
                return new WeightLossCalorieStrategy();

            case MAINTAIN_WEIGHT:
                return new MaintenanceCalorieStrategy();

            case GAIN_WEIGHT:
                return new WeightGainCalorieStrategy();

            default:
                throw new IllegalArgumentException(
                        "Unsupported health goal.");
        }
    }
}
