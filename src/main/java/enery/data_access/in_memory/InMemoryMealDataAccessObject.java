package enery.data_access.in_memory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import enery.entity.meal.MealEntry;
import enery.use_case.log_meal.LogMealUserDataAccessInterface;

/**
 * Stores meal entries in memory.
 */
public final class InMemoryMealDataAccessObject
        implements LogMealUserDataAccessInterface {

    private final Map<String, List<MealEntry>> mealsByUsername =
            new HashMap<>();

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
