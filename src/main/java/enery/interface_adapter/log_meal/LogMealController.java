package enery.interface_adapter.log_meal;

import java.time.LocalDate;

import enery.entity.meal.FoodItem;
import enery.entity.meal.MealType;
import enery.use_case.log_meal.LogMealInputBoundary;
import enery.use_case.log_meal.LogMealInputData;

/**
 * Controller for the log-meal use case.
 */
public final class LogMealController {

    private final LogMealInputBoundary logMealInteractor;

    public LogMealController(
            LogMealInputBoundary logMealInteractor) {

        this.logMealInteractor = logMealInteractor;
    }

    public void execute(
            String username,
            FoodItem foodItem,
            MealType mealType,
            double servings,
            LocalDate date) {

        final LogMealInputData inputData =
                new LogMealInputData(
                        username,
                        foodItem,
                        mealType,
                        servings,
                        date
                );

        logMealInteractor.execute(inputData);
    }
}
