package enery.interface_adapter.log_meal;

import enery.entity.meal.FoodItem;
import enery.entity.meal.MealType;

/**
 * Stores the current state of the log-meal view.
 */
public final class LogMealState {

    private String username = "";
    private FoodItem foodItem;
    private MealType mealType = MealType.BREAKFAST;
    private double servings = 1.0;
    private String errorMessage = "";
    private String successMessage = "";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public FoodItem getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(FoodItem foodItem) {
        this.foodItem = foodItem;
    }

    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }

    public double getServings() {
        return servings;
    }

    public void setServings(double servings) {
        this.servings = servings;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }
}
