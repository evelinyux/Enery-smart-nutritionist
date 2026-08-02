package enery.entity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple implementation of the User interface.
 */
public class CommonUser implements User {

    private final String name;

    private String password;

    private UserProfile userProfile;
    private NutritionGoal nutritionGoal;

    private final Map<LocalDate, DailyLog> dailyLogs =
            new HashMap<>();

    /**
     * Creates a user.
     *
     * @param name username
     * @param password password
     */
    public CommonUser(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public UserProfile getUserProfile() {
        return userProfile;
    }

    @Override
    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    @Override
    public NutritionGoal getNutritionGoal() {
        return nutritionGoal;
    }

    @Override
    public void setNutritionGoal(
            NutritionGoal nutritionGoal) {

        this.nutritionGoal = nutritionGoal;
    }

    @Override
    public DailyLog getDailyLog(LocalDate date) {
        return dailyLogs.computeIfAbsent(
                date,
                DailyLog::new
        );
    }

    @Override
    public Map<LocalDate, DailyLog> getDailyLogs() {
        return Collections.unmodifiableMap(
                dailyLogs
        );
    }
}