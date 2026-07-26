package enery.entity;

/**
 * Represents the nutritional information for one food item.
 *
 * This entity is independent of any particular external API.
 */
public final class FoodItem {

    private final String name;
    private final double calories;
    private final double proteinInGrams;
    private final double carbohydratesInGrams;
    private final double fatInGrams;
    private final double fiberInGrams;
    private final double sugarInGrams;
    private final double sodiumInMilligrams;

    public FoodItem(
            String name,
            double calories,
            double proteinInGrams,
            double carbohydratesInGrams,
            double fatInGrams,
            double fiberInGrams,
            double sugarInGrams,
            double sodiumInMilligrams) {

        this.name = name;
        this.calories = calories;
        this.proteinInGrams = proteinInGrams;
        this.carbohydratesInGrams = carbohydratesInGrams;
        this.fatInGrams = fatInGrams;
        this.fiberInGrams = fiberInGrams;
        this.sugarInGrams = sugarInGrams;
        this.sodiumInMilligrams = sodiumInMilligrams;
    }

    public String getName() {
        return name;
    }

    public double getCalories() {
        return calories;
    }

    public double getProteinInGrams() {
        return proteinInGrams;
    }

    public double getCarbohydratesInGrams() {
        return carbohydratesInGrams;
    }

    public double getFatInGrams() {
        return fatInGrams;
    }

    public double getFiberInGrams() {
        return fiberInGrams;
    }

    public double getSugarInGrams() {
        return sugarInGrams;
    }

    public double getSodiumInMilligrams() {
        return sodiumInMilligrams;
    }
}
