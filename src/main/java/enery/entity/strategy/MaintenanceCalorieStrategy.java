package enery.entity.strategy;

/**
 * Keeps the maintenance calorie estimate unchanged.
 */
public final class MaintenanceCalorieStrategy
        implements CalorieTargetStrategy {

    @Override
    public double calculateTarget(
            double maintenanceCalories) {

        return maintenanceCalories;
    }
}
