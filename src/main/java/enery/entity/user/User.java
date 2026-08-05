package enery.entity.user;

import enery.entity.tracking.DailyLog;

import java.time.LocalDate;
import java.util.Map;

/**
 * The representation of a user in our program.
 */
public interface User {

    /**
     * Returns the username of the user.
     *
     * @return the username of the user
     */
    String getName();

    /**
     * Returns the password of the user.
     *
     * @return the password of the user
     */
    String getPassword();

    /**
     * Updates the password of the user.
     *
     * @param password the new password
     */
    void setPassword(String password);

    /**
     * Returns the Profile of the user.
     *
     * @return the Profile of the user
     */
    UserProfile getUserProfile();

    /**
     * Sets the Profile of the user.
     *
     * @param userProfile the profile to store
     */
    void setUserProfile(UserProfile userProfile);

    /**
     * Returns the NutritionGoal of the user.
     *
     * @return the NutritionGoal of the user
     */
    NutritionGoal getNutritionGoal();

    /**
     * Sets the NutritionGoal of the user.
     *
     * @param nutritionGoal the nutrition goal to store
     */
    void setNutritionGoal(NutritionGoal nutritionGoal);

    /**
     * Retrieves the DailyLog for a specific date.
     * If no log exists for that date, creates a new one.
     *
     * @param date date of the log
     * @return daily log for the date
     */
    DailyLog getDailyLog(LocalDate date);

    /**
     * Returns all recorded daily logs indexed by date.
     *
     * @return all daily logs
     */
    Map<LocalDate, DailyLog> getDailyLogs();
}