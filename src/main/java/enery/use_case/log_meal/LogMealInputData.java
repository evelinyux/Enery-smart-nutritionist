package enery.use_case.log_meal;

import java.time.LocalDateTime;

import enery.entity.FoodItem;
import enery.entity.MealType;

/**
 * Input data for logging a meal.
 */
public final class LogMealInputData {

    private final String username;
    private final FoodItem foodItem;
    private final MealType mealType;
    private final double servings;
    private final LocalDateTime dateTime;

    public LogMealInputData(
            String username,
            FoodItem foodItem,
            MealType mealType,
            double servings,
            LocalDateTime dateTime) {

        this.username = username;
        this.foodItem = foodItem;
        this.mealType = mealType;
        this.servings = servings;
        this.dateTime = dateTime;
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
