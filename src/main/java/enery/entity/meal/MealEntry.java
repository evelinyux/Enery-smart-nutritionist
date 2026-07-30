package enery.entity.meal;

import java.time.LocalDateTime;

/**
 * Represents one food item logged as part of a meal.
 */
public final class MealEntry {

    private final FoodItem foodItem;
    private final MealType mealType;
    private final double servings;
    private final LocalDateTime dateTime;

    public MealEntry(
            FoodItem foodItem,
            MealType mealType,
            double servings,
            LocalDateTime dateTime) {

        this.foodItem = foodItem;
        this.mealType = mealType;
        this.servings = servings;
        this.dateTime = dateTime;
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

    public double getTotalCalories() {
        return foodItem.getCalories() * servings;
    }

    public double getTotalProteinInGrams() {
        return foodItem.getProteinInGrams() * servings;
    }

    public double getTotalCarbohydratesInGrams() {
        return foodItem.getCarbohydratesInGrams() * servings;
    }

    public double getTotalFatInGrams() {
        return foodItem.getFatInGrams() * servings;
    }
}
