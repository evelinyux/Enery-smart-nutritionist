package enery.interface_adapter.recipe;

import enery.entity.recipe.Recipe;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeInterfaceAdapterTest {

    @Test
    void recipeStateStartsWithExpectedDefaults() {
        final RecipeState state =
                new RecipeState();

        assertEquals("", state.getId());
        assertEquals("", state.getRecipeName());
        assertEquals("", state.getDescription());
        assertEquals("", state.getRecipeUrl());

        assertEquals("", state.getCalories());
        assertEquals("", state.getProtein());
        assertEquals("", state.getCarbs());
        assertEquals("", state.getFat());
        assertEquals("", state.getSugar());

        assertEquals("", state.getIngredients());
        assertEquals("", state.getInstructions());

        assertNull(state.getRecipeError());
    }

    @Test
    void recipeStateStoresAllValues() {
        final RecipeState state =
                new RecipeState();

        state.setId("123");
        state.setRecipeName("Pasta");
        state.setDescription("A pasta recipe");
        state.setRecipeUrl("https://example.com");

        state.setCalories("500 kcal");
        state.setProtein("20.0g");
        state.setCarbs("60.0g");
        state.setFat("15.0g");
        state.setSugar("5.0g");

        state.setIngredients("Pasta\nTomato");
        state.setInstructions("Cook it");
        state.setRecipeError("error");

        assertEquals("123", state.getId());
        assertEquals("Pasta", state.getRecipeName());
        assertEquals(
                "A pasta recipe",
                state.getDescription()
        );

        assertEquals(
                "https://example.com",
                state.getRecipeUrl()
        );

        assertEquals(
                "500 kcal",
                state.getCalories()
        );

        assertEquals(
                "20.0g",
                state.getProtein()
        );

        assertEquals(
                "60.0g",
                state.getCarbs()
        );

        assertEquals(
                "15.0g",
                state.getFat()
        );

        assertEquals(
                "5.0g",
                state.getSugar()
        );

        assertEquals(
                "Pasta\nTomato",
                state.getIngredients()
        );

        assertEquals(
                "Cook it",
                state.getInstructions()
        );

        assertEquals(
                "error",
                state.getRecipeError()
        );
    }

    @Test
    void recipeStateCopyConstructorCopiesEverything() {
        final RecipeState original =
                new RecipeState();

        original.setId("42");
        original.setRecipeName("Soup");
        original.setDescription("Description");
        original.setRecipeUrl("url");

        original.setCalories("100 kcal");
        original.setProtein("5.0g");
        original.setCarbs("10.0g");
        original.setFat("2.0g");
        original.setSugar("1.0g");

        original.setIngredients("ingredient");
        original.setInstructions("instruction");
        original.setRecipeError("error");

        final RecipeState copy =
                new RecipeState(original);

        assertEquals(original.getId(), copy.getId());
        assertEquals(
                original.getRecipeName(),
                copy.getRecipeName()
        );

        assertEquals(
                original.getDescription(),
                copy.getDescription()
        );

        assertEquals(
                original.getRecipeUrl(),
                copy.getRecipeUrl()
        );

        assertEquals(
                original.getCalories(),
                copy.getCalories()
        );

        assertEquals(
                original.getProtein(),
                copy.getProtein()
        );

        assertEquals(
                original.getCarbs(),
                copy.getCarbs()
        );

        assertEquals(
                original.getFat(),
                copy.getFat()
        );

        assertEquals(
                original.getSugar(),
                copy.getSugar()
        );

        assertEquals(
                original.getIngredients(),
                copy.getIngredients()
        );

        assertEquals(
                original.getInstructions(),
                copy.getInstructions()
        );

        assertEquals(
                original.getRecipeError(),
                copy.getRecipeError()
        );
    }

    @Test
    void recipeViewModelStartsWithEmptyState() {
        final RecipeViewModel viewModel =
                new RecipeViewModel();

        assertNotNull(viewModel.getState());

        assertEquals(
                RecipeViewModel.VIEW_NAME,
                viewModel.getViewName()
        );
    }

    @Test
    void recipeViewModelSetStateNotifiesListener() {
        final RecipeViewModel viewModel =
                new RecipeViewModel();

        final List<PropertyChangeEvent> events =
                new ArrayList<>();

        viewModel.addPropertyChangeListener(
                events::add
        );

        final RecipeState newState =
                new RecipeState();

        newState.setRecipeName("Pizza");

        viewModel.setState(newState);

        assertSame(
                newState,
                viewModel.getState()
        );

        assertEquals(1, events.size());

        assertEquals(
                "state",
                events.get(0).getPropertyName()
        );
    }

    @Test
    void recipeViewModelFirePropertyChangedNotifiesListener() {
        final RecipeViewModel viewModel =
                new RecipeViewModel();

        final List<PropertyChangeEvent> events =
                new ArrayList<>();

        viewModel.addPropertyChangeListener(
                events::add
        );

        viewModel.firePropertyChanged();

        assertEquals(1, events.size());

        assertEquals(
                "state",
                events.get(0).getPropertyName()
        );
    }

    @Test
    void presenterFormatsRecipeWithIngredients() {
        final RecipeViewModel viewModel =
                new RecipeViewModel();

        final RecipePresenter presenter =
                new RecipePresenter(viewModel);

        final Recipe recipe =
                mock(Recipe.class);

        when(recipe.getId())
                .thenReturn("123");

        when(recipe.getName())
                .thenReturn("Chicken Pasta");

        when(recipe.getDescription())
                .thenReturn("A tasty meal");

        when(recipe.getRecipeUrl())
                .thenReturn(
                        "https://example.com/recipe"
                );

        when(recipe.getCalories())
                .thenReturn(450.4);

        when(recipe.getProteinInGrams())
                .thenReturn(30.25);

        when(recipe.getCarbohydratesInGrams())
                .thenReturn(50.75);

        when(recipe.getFatInGrams())
                .thenReturn(12.25);

        when(recipe.getSugarInGrams())
                .thenReturn(5.75);

        when(recipe.getIngredients())
                .thenReturn(
                        List.of(
                                "Chicken",
                                "Pasta",
                                "Tomato"
                        )
                );

        when(recipe.getInstructions())
                .thenReturn(
                        "Cook everything."
                );

        presenter.prepareRecipeView(recipe);

        final RecipeState state =
                viewModel.getState();

        assertEquals("123", state.getId());

        assertEquals(
                "Chicken Pasta",
                state.getRecipeName()
        );

        assertEquals(
                "A tasty meal",
                state.getDescription()
        );

        assertEquals(
                "https://example.com/recipe",
                state.getRecipeUrl()
        );

        assertEquals(
                "450 kcal",
                state.getCalories()
        );

        assertEquals(
                "30.3g",
                state.getProtein()
        );

        assertEquals(
                "50.8g",
                state.getCarbs()
        );

        assertEquals(
                "12.3g",
                state.getFat()
        );

        assertEquals(
                "5.8g",
                state.getSugar()
        );

        assertEquals(
                "• Chicken\n• Pasta\n• Tomato",
                state.getIngredients()
        );

        assertEquals(
                "Cook everything.",
                state.getInstructions()
        );

        assertNull(state.getRecipeError());
    }

    @Test
    void presenterHandlesEmptyIngredients() {
        final RecipeViewModel viewModel =
                new RecipeViewModel();

        final RecipePresenter presenter =
                new RecipePresenter(viewModel);

        final Recipe recipe =
                mock(Recipe.class);

        when(recipe.getIngredients())
                .thenReturn(List.of());

        when(recipe.getInstructions())
                .thenReturn("Instructions");

        presenter.prepareRecipeView(recipe);

        assertEquals(
                "No ingredients provided.",
                viewModel.getState().getIngredients()
        );
    }

    @Test
    void presenterHandlesNullIngredientsAndInstructions() {
        final RecipeViewModel viewModel =
                new RecipeViewModel();

        final RecipePresenter presenter =
                new RecipePresenter(viewModel);

        final Recipe recipe =
                mock(Recipe.class);

        when(recipe.getIngredients())
                .thenReturn(null);

        when(recipe.getInstructions())
                .thenReturn(null);

        presenter.prepareRecipeView(recipe);

        final RecipeState state =
                viewModel.getState();

        assertEquals(
                "No ingredients provided.",
                state.getIngredients()
        );

        assertEquals(
                "No instructions available.",
                state.getInstructions()
        );
    }

    @Test
    void presenterFailureSetsErrorWithoutDestroyingState() {
        final RecipeViewModel viewModel =
                new RecipeViewModel();

        final RecipeState original =
                new RecipeState();

        original.setRecipeName("Existing Recipe");

        viewModel.setState(original);

        final RecipePresenter presenter =
                new RecipePresenter(viewModel);

        presenter.prepareFailView(
                "Could not load recipe"
        );

        final RecipeState state =
                viewModel.getState();

        assertEquals(
                "Existing Recipe",
                state.getRecipeName()
        );

        assertEquals(
                "Could not load recipe",
                state.getRecipeError()
        );
    }
}
