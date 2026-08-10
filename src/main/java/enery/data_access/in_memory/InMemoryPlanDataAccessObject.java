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
    public UserProfile getUserProfile(String username) {
        final User user = userDataAccessObject.get(username);
        return user.getUserProfile();
    }

    private List<MealEntry> getMealEntries(String username, LocalDate date) {
        return mealDataAccessObject.getMeals(username, date);
    }

    @Override
    public double getTodaysCalories(String username) {
        final List<MealEntry> meals = getMealEntries(username,  LocalDate.now());

        double calories = 0.0;

        for (MealEntry meal : meals) {
            calories += meal.getTotalCalories();
        }

        return calories;
    }

    @Override
    public double getDailyCalorieGoal(String username) {
        final UserProfile userProfile = getUserProfile(username);
        final WeightGoalPlan weightGoalPlan = userProfile.getActivePlan();
        return weightGoalPlan.getCurrentDailyCalorieGoal();
    }

    @Override
    public double getTodaysProtein(String username) {
        final List<MealEntry> meals = getMealEntries(username, LocalDate.now());

        double protein = 0.0;

        for (MealEntry meal : meals) {
            protein += meal.getTotalProteinInGrams();
        }

        return protein;
    }

    @Override
    public double getTodaysCarbohydrates(String username) {
        final List<MealEntry> meals = getMealEntries(username,  LocalDate.now());

        double carbohydrates = 0.0;

        for (MealEntry meal : meals) {
            carbohydrates  += meal.getTotalCarbohydratesInGrams();
        }

        return carbohydrates;
    }

    @Override
    public double getTodaysFats(String username) {
        final List<MealEntry> meals = getMealEntries(username, LocalDate.now());

        double fats = 0.0;

        for (MealEntry meal : meals) {
            fats += meal.getTotalFatInGrams();
        }

        return fats;
    }

    @Override
    public double getTotalCalorieGoal(String username) {
        final UserProfile userProfile = getUserProfile(username);
        final WeightGoalPlan weightGoalPlan = userProfile.getActivePlan();
        final double initialCalorieGoal = weightGoalPlan.getCurrentDailyCalorieGoal();
        final int duration = weightGoalPlan.getDurationDays();
        return initialCalorieGoal * duration;
    }

    @Override
    public double getTotalCaloriesConsumed(String username) {
        final UserProfile userProfile = getUserProfile(username);
        final LocalDate startDate = userProfile.getActivePlan().getStartDate();
        final LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalDate date = startDate;
        double totalCalories = 0.0;

        while (date.isBefore(tomorrow)) {
            List<MealEntry> meals = getMealEntries(username, date);
            for (MealEntry meal : meals) {
                totalCalories += meal.getTotalCalories();
            }
            date = date.plusDays(1);
        }

        return totalCalories;
    }

    @Override
    public int getDaysSoFar(String username) {
        final UserProfile userProfile = getUserProfile(username);
        final LocalDate startDate = userProfile.getActivePlan().getStartDate();
        return startDate.datesUntil(LocalDate.now()).toArray().length + 1;
    }
}
