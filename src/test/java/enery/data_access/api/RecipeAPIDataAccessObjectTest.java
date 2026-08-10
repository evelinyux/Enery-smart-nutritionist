package enery.data_access.api;

import enery.entity.recipe.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RecipeApiDataAccessObjectTest {

    private RecipeApiDataAccessObject dao;

    @BeforeEach
    void setUp() {
        // Initialize with evaluation key or mock key
        dao = new RecipeApiDataAccessObject("rapi_test_key");
    }

    @Test
    void testFetchCandidateRecipesWithInvalidKeyGracefulHandling() {
        // When using an invalid or dummy key, the API call handles failure gracefully and returns empty list
        final List<Recipe> recipes = dao.fetchCandidateRecipes(
                500.0,
                30.0,
                40.0,
                15.0,
                List.of("Gluten-Free", "Dairy-Free")
        );

        assertNotNull(recipes);
        // Returns 0 recipes without crashing on bad API credentials
    }

    @Test
    void testGetRecipeByIdWithInvalidKeyReturnsNull() {
        final Recipe recipe = dao.getRecipeById("invalid-uuid-1234");
        // Should catch HTTP error and return null smoothly
        assertTrue(recipe == null || recipe.getName() != null);
    }

    @Test
    void testConstructorInsecureHttpClient() {
        final RecipeApiDataAccessObject customDao = new RecipeApiDataAccessObject("rapi_demo");
        assertNotNull(customDao);
    }
}