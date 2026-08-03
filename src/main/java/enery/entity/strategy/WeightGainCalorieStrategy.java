package enery.entity.strategy;

/**
 * Calculates a calorie target for a weight-gain goal.
 */
public final class WeightGainCalorieStrategy
        implements CalorieTargetStrategy {

    private static final double TARGET_MULTIPLIER = 1.1;

    @Override
    public double calculateTarget(
            double maintenanceCalories) {

        return maintenanceCalories
                * TARGET_MULTIPLIER;
    }
}
