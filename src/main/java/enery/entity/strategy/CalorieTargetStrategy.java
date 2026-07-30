package enery.entity.strategy;

/**
 * Strategy for adjusting a maintenance calorie value
 * according to a user's health goal.
 */
public interface CalorieTargetStrategy {

    /**
     * Calculates a daily calorie target.
     *
     * @param maintenanceCalories estimated maintenance calories
     * @return daily calorie target
     */
    double calculateTarget(double maintenanceCalories);
}