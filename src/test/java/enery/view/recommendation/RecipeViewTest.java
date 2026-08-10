package enery.view.recommendation;

import java.awt.Component;
import java.awt.Container;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import enery.interface_adapter.recipe.RecipeState;
import enery.interface_adapter.recipe.RecipeViewModel;
import enery.interface_adapter.recommendation.RecommendationViewModel;
import enery.interface_adapter.shared.ViewManagerModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class RecipeViewTest {

    private RecipeViewModel recipeViewModel;
    private ViewManagerModel viewManagerModel;
    private RecipeView view;

    @BeforeEach
    void setUp() throws Exception {
        recipeViewModel =
                mock(RecipeViewModel.class);

        viewManagerModel =
                mock(ViewManagerModel.class);

        SwingUtilities.invokeAndWait(
                () -> view =
                        new RecipeView(
                                recipeViewModel,
                                viewManagerModel
                        )
        );
    }

    @Test
    void constructorRegistersListener() {
        verify(recipeViewModel)
                .addPropertyChangeListener(
                        view
                );
    }

    @Test
    void viewNameConstantHasExpectedValue() {
        assertEquals(
                "recipe view",
                RecipeView.VIEW_NAME
        );
    }

    @Test
    void textAreasAreReadOnly() {
        final List<JTextArea> areas =
                findComponents(
                        view,
                        JTextArea.class
                );

        assertEquals(
                3,
                areas.size()
        );

        for (JTextArea area : areas) {
            assertFalse(
                    area.isEditable()
            );
        }
    }

    @Test
    void backButtonNavigatesToRecommendation()
            throws Exception {

        SwingUtilities.invokeAndWait(
                () -> findButton(
                        view,
                        "Back"
                ).doClick()
        );

        verify(viewManagerModel)
                .setState(
                        RecommendationViewModel.VIEW_NAME
                );

        verify(viewManagerModel)
                .firePropertyChanged();
    }

    @Test
    void successfulStateDisplaysRecipeName()
            throws Exception {

        final RecipeState state =
                createRecipeState();

        SwingUtilities.invokeAndWait(
                () -> view.propertyChange(
                        new PropertyChangeEvent(
                                recipeViewModel,
                                "state",
                                null,
                                state
                        )
                )
        );

        assertTrue(
                containsLabelText(
                        view,
                        "Chicken Pasta"
                )
        );
    }

    @Test
    void successfulStateDisplaysNutritionSummary()
            throws Exception {

        final RecipeState state =
                createRecipeState();

        SwingUtilities.invokeAndWait(
                () -> view.propertyChange(
                        new PropertyChangeEvent(
                                recipeViewModel,
                                "state",
                                null,
                                state
                        )
                )
        );

        assertTrue(
                containsLabelText(
                        view,
                        "Calories: 500 kcal"
                                + " | Protein: 30g"
                                + " | Carbs: 50g"
                                + " | Fat: 15g"
                                + " | Sugar: 5g"
                )
        );
    }

    @Test
    void successfulStateDisplaysDescriptionIngredientsAndInstructions()
            throws Exception {

        final RecipeState state =
                createRecipeState();

        SwingUtilities.invokeAndWait(
                () -> view.propertyChange(
                        new PropertyChangeEvent(
                                recipeViewModel,
                                "state",
                                null,
                                state
                        )
                )
        );

        final List<JTextArea> areas =
                findComponents(
                        view,
                        JTextArea.class
                );

        assertTrue(
                containsTextArea(
                        areas,
                        "A tasty pasta recipe."
                )
        );

        assertTrue(
                containsTextArea(
                        areas,
                        "Chicken\nPasta\nTomato"
                )
        );

        assertTrue(
                containsTextArea(
                        areas,
                        "Cook the pasta and combine ingredients."
                )
        );
    }

    @Test
    void unrelatedPropertyChangeDoesNotUpdateUi()
            throws Exception {

        final RecipeState state =
                createRecipeState();

        SwingUtilities.invokeAndWait(
                () -> view.propertyChange(
                        new PropertyChangeEvent(
                                recipeViewModel,
                                "other",
                                null,
                                state
                        )
                )
        );

        assertFalse(
                containsLabelText(
                        view,
                        "Chicken Pasta"
                )
        );

        verify(
                viewManagerModel,
                never()
        ).firePropertyChanged();
    }

    private static RecipeState createRecipeState() {
        final RecipeState state =
                new RecipeState();

        state.setRecipeName(
                "Chicken Pasta"
        );

        state.setDescription(
                "A tasty pasta recipe."
        );

        state.setCalories(
                "500 kcal"
        );

        state.setProtein(
                "30g"
        );

        state.setCarbs(
                "50g"
        );

        state.setFat(
                "15g"
        );

        state.setSugar(
                "5g"
        );

        state.setIngredients(
                "Chicken\nPasta\nTomato"
        );

        state.setInstructions(
                "Cook the pasta and combine ingredients."
        );

        state.setRecipeError(null);

        return state;
    }

    private static boolean containsTextArea(
            List<JTextArea> areas,
            String text) {

        for (JTextArea area : areas) {
            if (text.equals(
                    area.getText()
            )) {

                return true;
            }
        }

        return false;
    }

    private static JButton findButton(
            Container container,
            String text) {

        for (Component component
                : container.getComponents()) {

            if (component instanceof JButton button
                    && text.equals(
                    button.getText()
            )) {

                return button;
            }

            if (component instanceof Container child) {
                try {
                    return findButton(
                            child,
                            text
                    );
                }
                catch (AssertionError ignored) {
                    // Continue searching.
                }
            }
        }

        throw new AssertionError(
                "Button not found: " + text
        );
    }

    private static boolean containsLabelText(
            Container container,
            String text) {

        for (Component component
                : container.getComponents()) {

            if (component instanceof JLabel label
                    && text.equals(
                    label.getText()
            )) {

                return true;
            }

            if (component instanceof Container child
                    && containsLabelText(
                    child,
                    text
            )) {

                return true;
            }
        }

        return false;
    }

    private static <T extends Component>
    List<T> findComponents(
            Container container,
            Class<T> type) {

        final List<T> result =
                new ArrayList<>();

        collectComponents(
                container,
                type,
                result
        );

        return result;
    }

    private static <T extends Component>
    void collectComponents(
            Container container,
            Class<T> type,
            List<T> result) {

        for (Component component
                : container.getComponents()) {

            if (type.isInstance(component)) {
                result.add(
                        type.cast(component)
                );
            }

            if (component instanceof Container child) {
                collectComponents(
                        child,
                        type,
                        result
                );
            }
        }
    }
}
