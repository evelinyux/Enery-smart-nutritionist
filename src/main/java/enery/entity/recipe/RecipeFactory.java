package enery.entity.recipe;

import java.util.List;

/**
 * Creates recipe entities.
 */
public final class RecipeFactory {

    public Recipe create(
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
            String instructions) {

        return new Recipe(
                id,
                name,
                calories,
                proteinInGrams,
                sugarInGrams,
                carbohydratesInGrams,
                fatInGrams,
                ingredients,
                recipeUrl,
                description,
                instructions
        );
    }
}