package enery.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a recipe candidate returned from external recipe discovery APIs.
 */
public final class Recipe {

    private final String id;
    private final String name;
    private final double calories;
    private final double proteinInGrams;
    private final double carbohydratesInGrams;
    private final double fatInGrams;
    private final List<String> ingredients;
    private final String recipeUrl;

    public Recipe(
            String id,
            String name,
            double calories,
            double proteinInGrams,
            double carbohydratesInGrams,
            double fatInGrams,
            List<String> ingredients,
            String recipeUrl) {

        this.id = id;
        this.name = name;
        this.calories = calories;
        this.proteinInGrams = proteinInGrams;
        this.carbohydratesInGrams = carbohydratesInGrams;
        this.fatInGrams = fatInGrams;
        this.ingredients = new ArrayList<>(ingredients);
        this.recipeUrl = recipeUrl;
    }

    public String getId() {
        return id;
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

    public List<String> getIngredients() {
        return Collections.unmodifiableList(ingredients);
    }

    public String getRecipeUrl() {
        return recipeUrl;
    }
}