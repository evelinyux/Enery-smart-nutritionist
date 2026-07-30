package enery.entity.meal;

import java.util.Locale;

/**
 * Represents the nutritional information for one food item.
 *
 * <p>This entity is independent of any particular external API. A value of
 * {@link Double#NaN} means that a nutrition value was unavailable from the
 * selected data source.</p>
 */
public final class FoodItem {

    private final String name;
    private final double servingSizeInGrams;
    private final double calories;
    private final double proteinInGrams;
    private final double carbohydratesInGrams;
    private final double fatInGrams;
    private final double fiberInGrams;
    private final double sugarInGrams;
    private final double sodiumInMilligrams;

    /**
     * Creates a food item.
     *
     * @param name food name
     * @param servingSizeInGrams serving size in grams
     * @param calories energy in kilocalories
     * @param proteinInGrams protein in grams
     * @param carbohydratesInGrams carbohydrates in grams
     * @param fatInGrams fat in grams
     * @param fiberInGrams fiber in grams
     * @param sugarInGrams sugar in grams
     * @param sodiumInMilligrams sodium in milligrams
     */
    public FoodItem(
            String name,
            double servingSizeInGrams,
            double calories,
            double proteinInGrams,
            double carbohydratesInGrams,
            double fatInGrams,
            double fiberInGrams,
            double sugarInGrams,
            double sodiumInMilligrams) {

        this.name = name;
        this.servingSizeInGrams = servingSizeInGrams;
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

    public double getServingSizeInGrams() {
        return servingSizeInGrams;
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

    /**
     * Reports whether calorie information is available.
     *
     * @return true when calories contain a usable number
     */
    public boolean hasCalories() {
        return !Double.isNaN(calories);
    }

    /**
     * Reports whether protein information is available.
     *
     * @return true when protein contains a usable number
     */
    public boolean hasProtein() {
        return !Double.isNaN(proteinInGrams);
    }

    /**
     * Formats a nutrition value for Swing labels.
     *
     * @param value value to display
     * @param unit measurement unit
     * @return formatted number or "Unavailable"
     */
    public static String formatNutritionValue(
            double value,
            String unit) {

        if (Double.isNaN(value)) {
            return "Unavailable";
        }

        return String.format(
                Locale.US,
                "%.1f %s",
                value,
                unit
        );
    }

    /**
     * Converts an all-uppercase API description into readable title case.
     *
     * @param text original description
     * @return readable description
     */
    private static String formatName(String text) {
        if (text == null || text.isBlank()) {
            return "Unknown food";
        }

        if (!text.equals(text.toUpperCase(java.util.Locale.US))) {
            return text;
        }

        final String[] words =
                text.toLowerCase(
                        java.util.Locale.US).split(" ");

        final StringBuilder result =
                new StringBuilder();

        for (String word : words) {
            if (!word.isBlank()) {
                if (!result.isEmpty()) {
                    result.append(' ');
                }

                result.append(
                        Character.toUpperCase(
                                word.charAt(0)));

                result.append(word.substring(1));
            }
        }

        return result.toString();
    }

    /**
     * Returns the text displayed by Swing combo boxes.
     *
     * @return short food description
     */
    @Override
    public String toString() {
        return String.format(
                java.util.Locale.US,
                "%s — nutrients per %.1f g, %.1f kcal",
                formatName(name),
                servingSizeInGrams,
                calories
        );
    }
}
