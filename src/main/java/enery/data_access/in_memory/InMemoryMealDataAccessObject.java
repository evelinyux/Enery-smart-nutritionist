package enery.data_access.in_memory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import enery.entity.meal.MealEntry;
import enery.entity.user.User;
import enery.use_case.log_meal.LogMealUserDataAccessInterface;
import enery.entity.meal.MealType;
import enery.use_case.daily_check.DailyCheckMealDataAccessInterface;

/**
 * Stores meal entries in memory.
 */
public final class InMemoryMealDataAccessObject
        implements LogMealUserDataAccessInterface,
        DailyCheckMealDataAccessInterface {

    private final Map<String, List<MealEntry>> mealsByUsername =
            new HashMap<>();
    private final InMemoryUserDataAccessObject userDataAccessObject;



    public InMemoryMealDataAccessObject(InMemoryUserDataAccessObject userDataAccessObject) {
        this.userDataAccessObject = userDataAccessObject;
    }

    @Override
    public void saveMeal(
            String username,
            MealEntry mealEntry) {

        mealsByUsername
                .computeIfAbsent(
                        username,
                        key -> new ArrayList<>()
                )
                .add(mealEntry);

    }
    @Override
    public User get(String username) {
        return userDataAccessObject.get(username);
    }


    @Override
    public List<MealEntry> getMeals(
            String username,
            LocalDate date) {

        final List<MealEntry> meals =
                mealsByUsername.getOrDefault(
                        username,
                        Collections.emptyList()
                );

        final List<MealEntry> matchingMeals =
                new ArrayList<>();

        for (MealEntry meal : meals) {
            if (meal.getDate()

                    .equals(date)) {

                matchingMeals.add(meal);
            }
        }



        return Collections.unmodifiableList(
                matchingMeals);
    }

    @Override
    public boolean hasMeal(
            String username,
            LocalDate date,
            MealType mealType) {

        final List<MealEntry> meals =
                mealsByUsername.getOrDefault(
                        username,
                        Collections.emptyList()
                );

        for (MealEntry meal : meals) {
            if (meal.getDate().equals(date)
                    && meal.getMealType() == mealType) {
                return true;
            }
        }

        return false;
    }
}
