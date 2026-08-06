package enery.use_case.progress;

import enery.entity.user.UserProfile;

public interface ProgressDataInterface {

    /**
     * Obtains the UserProfile of the user currently logged in.
     * @return the UserProfile of the user currently logged in.
     */
    UserProfile getUserProfile();

    /**
     * Obtains the calories consumed by the user today.
     * @return the calories consumed.
     */
    double getTodaysCalories();

    /**
     * Obtains the daily calorie goal set by the user's plan.
     * @return the daily calorie goal.
     */
    double getDailyCalorieGoal();
}
