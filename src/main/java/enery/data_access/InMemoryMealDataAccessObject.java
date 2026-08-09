package enery.data_access;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import enery.entity.meal.MealEntry;
import enery.entity.user.User;
import enery.use_case.log_meal.LogMealUserDataAccessInterface;

/**
 * Stores meal entries in memory.
 */
public final class InMemoryMealDataAccessObject
        implements LogMealUserDataAccessInterface
{

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
}
