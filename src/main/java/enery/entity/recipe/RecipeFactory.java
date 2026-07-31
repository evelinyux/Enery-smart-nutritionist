package enery.entity.recipe;

import java.util.List;

public class RecipeFactory{
    public Recipe create(String id, String name, double calories, double protein,
                         double carbs, double fat, List<String> ingredients, String recipeUrl) {
        return new Recipe(id, name, calories, protein, carbs, fat, ingredients, recipeUrl);
    }
}
