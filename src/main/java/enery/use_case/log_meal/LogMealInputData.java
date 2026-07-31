package enery.use_case.log_meal;

import java.time.LocalDate;

import enery.entity.meal.FoodItem;
import enery.entity.meal.MealType;

/**
 * Input data for logging a meal.
 */
public final class LogMealInputData {

    private final String username;
    private final FoodItem foodItem;
    private final MealType mealType;
    private final double servings;
    private final LocalDate date;

    public LogMealInputData(
            String username,
            FoodItem foodItem,
            MealType mealType,
            double servings,
            LocalDate date) {

        this.username = username;
        this.foodItem = foodItem;
        this.mealType = mealType;
        this.servings = servings;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public FoodItem getFoodItem() {
        return foodItem;
    }

    public MealType getMealType() {
        return mealType;
    }

    public double getServings() {
        return servings;
    }

    public LocalDate getDate() {
        return date;
    }
}
