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

    private List<MealEntry> getMealEntries() {
        final String username = userDataAccessObject.getCurrentUsername();
        return mealDataAccessObject.getMeals(username, LocalDate.now());
    }

    @Override
    public double getTodaysCalories() {
        final List<MealEntry> meals = getMealEntries();

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

    @Override
    public double getTodaysProtein() {
        final List<MealEntry> meals = getMealEntries();

        double protein = 0.0;

        for (MealEntry meal : meals) {
            protein += meal.getTotalProteinInGrams();
        }

        return protein;
    }

    @Override
    public double getTodaysCarbohydrates() {
        final List<MealEntry> meals = getMealEntries();

        double carbohydrates = 0.0;

        for (MealEntry meal : meals) {
            carbohydrates  += meal.getTotalCarbohydratesInGrams();
        }

        return carbohydrates;
    }

    @Override
    public double getTodaysFats() {
        final List<MealEntry> meals = getMealEntries();

        double fats = 0.0;

        for (MealEntry meal : meals) {
            fats += meal.getTotalFatInGrams();
        }

        return fats;
    }
}
