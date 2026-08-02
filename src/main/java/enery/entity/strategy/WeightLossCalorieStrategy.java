package enery.entity.strategy;

/**
 * Calculates a calorie target for a weight-loss goal.
 */
public final class WeightLossCalorieStrategy
        implements CalorieTargetStrategy {

    private static final double TARGET_MULTIPLIER = 0.9;

    @Override
    public double calculateTarget(
            double maintenanceCalories) {

        return maintenanceCalories
                * TARGET_MULTIPLIER;
    }
}
