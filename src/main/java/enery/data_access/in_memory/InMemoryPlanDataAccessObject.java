package enery.data_access.in_memory;

import java.time.LocalDate;
import java.util.List;

import enery.entity.meal.MealEntry;
import enery.entity.user.User;
import enery.entity.user.UserProfile;
import enery.entity.user.WeightGoalPlan;
import enery.use_case.progress.ProgressDataInterface;

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

    @Override
    public UserProfile getUserProfile() {
        final String username = userDataAccessObject.getCurrentUsername();
        final User user = userDataAccessObject.get(username);
        return user.getUserProfile();
    }

    @Override
    public double getTodaysCalories() {
        final String username = userDataAccessObject.getCurrentUsername();
        final List<MealEntry> meals = mealDataAccessObject.getMeals(username, LocalDate.now());

        double calories = 0.0;

        for (MealEntry meal : meals) {
            calories += meal.getTotalCalories();
        }

        return calories;
    }

    @Override
    public double getDailyCalorieGoal() {
        final UserProfile userProfile = getUserProfile();
        final WeightGoalPlan weightGoalPlan = userProfile.getActivePlan();
        return weightGoalPlan.getCurrentDailyCalorieGoal();
    }
}
