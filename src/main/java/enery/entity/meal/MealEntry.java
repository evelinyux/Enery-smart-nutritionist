package enery.entity.meal;

import java.time.LocalDate;

/**
 * Represents one food item logged as part of a meal.
 */
public final class MealEntry {

    private final FoodItem foodItem;
    private final MealType mealType;
    private final double servings;
    private final LocalDate date;

    public MealEntry(
            FoodItem foodItem,
            MealType mealType,
            double servings,
            LocalDate date) {

        this.foodItem = foodItem;
        this.mealType = mealType;
        this.servings = servings;
        this.date = date;
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

    public double getTotalFiberInGrams() { return foodItem.getFiberInGrams() * servings; }

    public double getTotalSugarInGrams() { return foodItem.getSugarInGrams() * servings; }

    public double getTotalSodiumInMilligrams() { return foodItem.getSodiumInMilligrams() * servings; }
}
