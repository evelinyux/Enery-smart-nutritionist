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
    private final String password;

    private UserProfile userProfile;
    private NutritionGoal nutritionGoal;
    private final Map<LocalDate, DailyLog> dailyLogs = new HashMap<>();

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
    public void setNutritionGoal(NutritionGoal nutritionGoal) {
        this.nutritionGoal = nutritionGoal;
    }

    @Override
    public DailyLog getDailyLog(LocalDate date) {
        // computeIfAbsent checks if a log for 'date' exists in the map.
        // If it doesn't, it automatically creates a new DailyLog(date) and stores it
        return dailyLogs.computeIfAbsent(date, DailyLog::new);
    }

    @Override
    public Map<LocalDate, DailyLog> getDailyLogs() {
        return Collections.unmodifiableMap(dailyLogs);
    }
}


