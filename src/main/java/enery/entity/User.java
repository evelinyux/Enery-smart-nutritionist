package enery.entity;

import java.time.LocalDate;
import java.util.Map;

/**
 * The representation of a user in our program.
 */
public interface User {

    /**
     * Returns the username of the user.
     * @return the username of the user.
     */
    String getName();

    /**
     * Returns the password of the user.
     * @return the password of the user.
     */
    String getPassword();

    /**
     * Returns the Profile of the user.
     * @return the Profile of the user.
     */
    UserProfile getUserProfile();

    /**
     * Sets the Profile of the user.
     */
    void setUserProfile(UserProfile userProfile);

    /**
     * Returns the NutritionGoal of the user.
     * @return the NutritionGoal of the user.
     */
    NutritionGoal getNutritionGoal();

    /**
     * Sets the NutritionGoal of the user.
     */
    void setNutritionGoal(NutritionGoal nutritionGoal);

    /**
     * Retrieves the DailyLog for a specific date.
     * If no log exists for that date, creates a new one.
     */
    DailyLog getDailyLog(LocalDate date);

    /**
     * Returns all recorded daily logs indexed by date.
     */
    Map<LocalDate, DailyLog> getDailyLogs();
}
