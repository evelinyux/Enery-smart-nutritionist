package enery.data_access;

import enery.entity.DailyLog;
import enery.entity.HealthGoal;
import enery.entity.NutritionGoal;
import enery.entity.User;
import enery.use_case.progress.ProgressDataInterface;

import java.time.LocalDate;

/**
 * Stores meal entries in memory.
 */
public class InMemoryPlanDataAccessObject implements ProgressDataInterface {
    private User user;

    public InMemoryPlanDataAccessObject(InMemoryUserDataAccessObject userDataAccessObject) {
        String username = userDataAccessObject.getCurrentUsername();
        this.user = userDataAccessObject.get(username);
    }


    @Override
    public double getTodaysCalories() {
        DailyLog dailyLog = user.getDailyLog(LocalDate.now());
        return dailyLog.getTotalCaloriesConsumed();
    }

    @Override
    public double getDailyCalorieGoal() {
        NutritionGoal nutritionGoal = user.getNutritionGoal();
        return nutritionGoal.getDailyCalories();
    }
}
