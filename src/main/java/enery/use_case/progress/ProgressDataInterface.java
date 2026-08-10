package enery.use_case.progress;

import enery.entity.user.UserProfile;

public interface ProgressDataInterface {

    /**
     * Obtains the UserProfile of a given user.
     * @param username the username of the profile.
     * @return the UserProfile of the user currently logged in.
     */
    UserProfile getUserProfile(String username);

    /**
     * Obtains the calories consumed a given user.
     * @param username the username of the profile.
     * @return the calories consumed.
     */
    double getTodaysCalories(String username);

    /**
     * Obtains the daily calorie goal of a given user set by their plan.
     * @param username the username of the profile.
     * @return the daily calorie goal.
     */
    double getDailyCalorieGoal(String username);

    /**
     * Obtains the protein, in g, consumed a given user.
     * @param username the username of the profile.
     * @return the calories consumed.
     */
    double getTodaysProtein(String username);

    /**
     * Obtains the carbohydrates, in g, consumed a given user.
     * @param username the username of the profile.
     * @return the calories consumed.
     */
    double getTodaysCarbohydrates(String username);

    /**
     * Obtains the fats, in g, consumed a given user.
     * @param username the username of the profile.
     * @return the calories consumed.
     */
    double getTodaysFats(String username);

    /**
     * Obtains the total calorie goal of a given user set by their plan.
     * @param username the username of the profile.
     * @return the daily calorie goal.
     */
    double getTotalCalorieGoal(String username);

    /**
     * Obtains the total calories consumed by a given user, over the duration of their whole plan.
     * @param username the username of the profile.
     * @return the calories consumed.
     */
    double getTotalCaloriesConsumed(String username);

    /**
     * Obtains how many days a given user is into their plan.
     * @param username the username of the profile.
     * @return how many days of their plan their into.
     */
    int getDaysSoFar(String username);
}
