package enery.entity.recipe;

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
    private final double sugarInGrams;
    private final double carbohydratesInGrams;
    private final double fatInGrams;
    private final List<String> ingredients;
    private final String recipeUrl;
    private final String description;
    private final String instructions;

    public Recipe(
            String id,
            String name,
            double calories,
            double proteinInGrams,
            double sugarInGrams,
            double carbohydratesInGrams,
            double fatInGrams,
            List<String> ingredients,
            String recipeUrl,
            String description,
            String instructions
           ) {

        this.id = id;
        this.name = name;
        this.calories = calories;
        this.proteinInGrams = proteinInGrams;
        this.sugarInGrams = sugarInGrams;
        this.carbohydratesInGrams = carbohydratesInGrams;
        this.fatInGrams = fatInGrams;
        this.ingredients = new ArrayList<>(ingredients);
        this.recipeUrl = recipeUrl;
        this.description = description;
        this.instructions = instructions;
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
    public double getSugarInGrams() {return sugarInGrams;}

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
    public String getDescription() {return description;}
    public String getInstructions() {return instructions;}


}