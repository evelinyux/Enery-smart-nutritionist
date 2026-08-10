package enery.entity.recipe;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RecipeTest {

    @Test
    void testRecipeConstructorAndGetters() {
        final List<String> ingredients = List.of("1 cup Rice", "200g Chicken");
        final Recipe recipe = new Recipe(
                "r123",
                "Chicken Rice",
                500.0,
                40.0,
                5.0,
                50.0,
                12.0,
                ingredients,
                "https://recipe-api.com/recipes/r123",
                "A simple chicken and rice dish.",
                "1. Cook rice.\n2. Grill chicken."
        );

        assertEquals("r123", recipe.getId());
        assertEquals("Chicken Rice", recipe.getName());
        assertEquals(500.0, recipe.getCalories());
        assertEquals(40.0, recipe.getProteinInGrams());
        assertEquals(5.0, recipe.getSugarInGrams());
        assertEquals(50.0, recipe.getCarbohydratesInGrams());
        assertEquals(12.0, recipe.getFatInGrams());
        assertEquals(2, recipe.getIngredients().size());
        assertEquals("1 cup Rice", recipe.getIngredients().get(0));
        assertEquals("https://recipe-api.com/recipes/r123", recipe.getRecipeUrl());
        assertEquals("A simple chicken and rice dish.", recipe.getDescription());
        assertEquals("1. Cook rice.\n2. Grill chicken.", recipe.getInstructions());
    }

    @Test
    void testRecipeWithEmptyIngredientsAndInstructions() {
        final Recipe recipe = new Recipe(
                "r999",
                "Empty Recipe",
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                List.of(),
                "",
                "",
                ""
        );

        assertEquals("r999", recipe.getId());
        assertTrue(recipe.getIngredients().isEmpty());
        assertEquals("", recipe.getInstructions());
    }
}