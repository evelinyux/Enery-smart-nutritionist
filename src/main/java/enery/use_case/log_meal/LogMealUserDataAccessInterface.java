package enery.use_case.log_meal;

import java.time.LocalDate;
import java.util.List;

import enery.entity.meal.MealEntry;
import enery.entity.user.User;

/**
 * Data-access operations required by the log-meal use case.
 */
public interface LogMealUserDataAccessInterface {

    void saveMeal(String username, MealEntry mealEntry);

    List<MealEntry> getMeals(String username, LocalDate date);

    // Allows the LogMealinteractor to fetch the User
    User get(String username);

}
