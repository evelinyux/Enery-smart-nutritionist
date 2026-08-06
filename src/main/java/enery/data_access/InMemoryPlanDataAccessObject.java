package enery.data_access;

import enery.entity.meal.MealEntry;
import enery.entity.user.User;
import enery.entity.user.UserProfile;
import enery.entity.user.WeightGoalPlan;
import enery.use_case.progress.ProgressDataInterface;

import java.time.LocalDate;
import java.util.List;

/**
 * Stores meal entries in memory.
 */
public class InMemoryPlanDataAccessObject implements ProgressDataInterface {
    private final InMemoryUserDataAccessObject userDataAccessObject;
    private final InMemoryMealDataAccessObject mealDataAccessObject;

    public InMemoryPlanDataAccessObject(InMemoryUserDataAccessObject userDataAccessObject,
                                        InMemoryMealDataAccessObject mealDataAccessObject) {
        this.userDataAccessObject = userDataAccessObject;
        this.mealDataAccessObject = mealDataAccessObject;
    }

    public UserProfile getUserProfile() {
        String username = userDataAccessObject.getCurrentUsername();
        User user = userDataAccessObject.get(username);
        return user.getUserProfile();
    }

    @Override
    public double getTodaysCalories() {
        String username = userDataAccessObject.getCurrentUsername();
        List<MealEntry> meals = mealDataAccessObject.getMeals(username, LocalDate.now());

        double calories = 0.0;

        for (MealEntry meal : meals) {
            calories += meal.getTotalCalories();
        }

        return calories;
    }

    @Override
    public double getDailyCalorieGoal() {
       UserProfile userProfile = getUserProfile();
       WeightGoalPlan weightGoalPlan = userProfile.getActivePlan();
       return weightGoalPlan.getCurrentDailyCalorieGoal();
    }
}
