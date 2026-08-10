package enery.view.recommendation;

import java.awt.Component;
import java.awt.Container;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import enery.entity.recipe.Recipe;
import enery.interface_adapter.change_password.LoggedInState;
import enery.interface_adapter.change_password.LoggedInViewModel;
import enery.interface_adapter.recipe.RecipePresenter;
import enery.interface_adapter.recipe.RecipeViewModel;
import enery.interface_adapter.recommendation.RecommendationController;
import enery.interface_adapter.recommendation.RecommendationState;
import enery.interface_adapter.recommendation.RecommendationViewModel;
import enery.interface_adapter.shared.ViewManagerModel;
import enery.view.home.HomeView;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RecommendationViewTest {

    private RecommendationViewModel viewModel;
    private LoggedInViewModel loggedInViewModel;
    private RecipePresenter recipePresenter;
    private ViewManagerModel viewManagerModel;

    private RecommendationState state;
    private LoggedInState loggedInState;

    private RecommendationView view;

    @BeforeEach
    void setUp() throws Exception {
        viewModel =
                mock(RecommendationViewModel.class);

        loggedInViewModel =
                mock(LoggedInViewModel.class);

        recipePresenter =
                mock(RecipePresenter.class);

        viewManagerModel =
                mock(ViewManagerModel.class);

        state =
                mock(RecommendationState.class);

        loggedInState =
                mock(LoggedInState.class);

        when(viewModel.getState())
                .thenReturn(state);

        when(loggedInViewModel.getState())
                .thenReturn(loggedInState);

        when(state.getRecommendedRecipes())
                .thenReturn(List.of());

        when(state.getRecommendedExercises())
                .thenReturn(List.of());

        when(state.getRecommendationError())
                .thenReturn(null);

        when(viewModel.isStale())
                .thenReturn(false);

        SwingUtilities.invokeAndWait(
                () -> view =
                        new RecommendationView(
                                viewModel,
                                loggedInViewModel,
                                recipePresenter,
                                viewManagerModel
                        )
        );
    }

    @Test
    void emptyStateShowsNoRecommendations() {
        assertTrue(
                containsLabelText(
                        view,
                        "No recipe recommendations available."
                )
        );

        assertTrue(
                containsLabelText(
                        view,
                        "No workout recommendations available."
                )
        );
    }

    @Test
    void backButtonNavigatesHome()
            throws Exception {

        SwingUtilities.invokeAndWait(
                () -> findButton(
                        view,
                        "Back to Home"
                ).doClick()
        );

        verify(viewManagerModel)
                .setState(
                        HomeView.VIEW_NAME
                );

        verify(viewManagerModel)
                .firePropertyChanged();
    }

    @Test
    void fetchRecommendationsWithoutControllerDoesNothing() {
        view.fetchRecommendations();

        verify(
                viewManagerModel,
                never()
        ).firePropertyChanged();
    }

    @Test
    void fetchRecommendationsWithBlankUsernameDoesNothing() {
        final RecommendationController controller =
                mock(RecommendationController.class);

        view.setRecommendationController(
                controller
        );

        when(loggedInState.getUsername())
                .thenReturn("");

        view.fetchRecommendations();

        verify(
                controller,
                never()
        ).execute(
                org.mockito.ArgumentMatchers.anyString()
        );
    }

    @Test
    void fetchRecommendationsWithUsernameCallsController() {
        final RecommendationController controller =
                mock(RecommendationController.class);

        view.setRecommendationController(
                controller
        );

        when(loggedInState.getUsername())
                .thenReturn("ethan");

        view.fetchRecommendations();

        verify(controller)
                .execute("ethan");
    }

    @Test
    void stateUpdateDisplaysRecipesAndWorkouts()
            throws Exception {

        final Recipe first =
                mock(Recipe.class);

        final Recipe second =
                mock(Recipe.class);

        when(first.getName())
                .thenReturn(
                        "Chicken Pasta"
                );

        when(second.getName())
                .thenReturn(
                        "Vegetable Soup"
                );

        final RecommendationState updated =
                mock(RecommendationState.class);

        when(updated.getRecommendationError())
                .thenReturn(null);

        when(updated.getRecommendedRecipes())
                .thenReturn(
                        List.of(
                                first,
                                second
                        )
                );

        when(updated.getRecommendedExercises())
                .thenReturn(
                        List.of(
                                "Cycling",
                                "Walking"
                        )
                );

        SwingUtilities.invokeAndWait(
                () -> view.propertyChange(
                        new PropertyChangeEvent(
                                viewModel,
                                "state",
                                state,
                                updated
                        )
                )
        );

        assertTrue(
                containsButtonContaining(
                        view,
                        "Chicken Pasta"
                )
        );

        assertTrue(
                containsButtonContaining(
                        view,
                        "Vegetable Soup"
                )
        );

        assertTrue(
                containsLabelText(
                        view,
                        "1. Cycling"
                )
        );

        assertTrue(
                containsLabelText(
                        view,
                        "2. Walking"
                )
        );

        verify(viewModel)
                .setStale(false);
    }

    @Test
    void onlyFirstThreeRecipesAreShown()
            throws Exception {

        final Recipe one =
                recipe("One");

        final Recipe two =
                recipe("Two");

        final Recipe three =
                recipe("Three");

        final Recipe four =
                recipe("Four");

        final RecommendationState updated =
                mock(RecommendationState.class);

        when(updated.getRecommendationError())
                .thenReturn(null);

        when(updated.getRecommendedRecipes())
                .thenReturn(
                        List.of(
                                one,
                                two,
                                three,
                                four
                        )
                );

        when(updated.getRecommendedExercises())
                .thenReturn(List.of());

        SwingUtilities.invokeAndWait(
                () -> view.propertyChange(
                        new PropertyChangeEvent(
                                viewModel,
                                "state",
                                state,
                                updated
                        )
                )
        );

        assertTrue(
                containsButtonContaining(
                        view,
                        "One"
                )
        );

        assertTrue(
                containsButtonContaining(
                        view,
                        "Two"
                )
        );

        assertTrue(
                containsButtonContaining(
                        view,
                        "Three"
                )
        );

        assertTrue(
                !containsButtonContaining(
                        view,
                        "Four"
                )
        );
    }

    @Test
    void onlyFirstThreeWorkoutsAreShown()
            throws Exception {

        final RecommendationState updated =
                mock(RecommendationState.class);

        when(updated.getRecommendationError())
                .thenReturn(null);

        when(updated.getRecommendedRecipes())
                .thenReturn(List.of());

        when(updated.getRecommendedExercises())
                .thenReturn(
                        List.of(
                                "One",
                                "Two",
                                "Three",
                                "Four"
                        )
                );

        SwingUtilities.invokeAndWait(
                () -> view.propertyChange(
                        new PropertyChangeEvent(
                                viewModel,
                                "state",
                                state,
                                updated
                        )
                )
        );

        assertTrue(
                containsLabelText(
                        view,
                        "1. One"
                )
        );

        assertTrue(
                containsLabelText(
                        view,
                        "2. Two"
                )
        );

        assertTrue(
                containsLabelText(
                        view,
                        "3. Three"
                )
        );

        assertTrue(
                !containsLabelText(
                        view,
                        "4. Four"
                )
        );
    }

    @Test
    void clickingRecipeCallsPresenterAndNavigates()
            throws Exception {

        final Recipe recipe =
                recipe(
                        "Chicken Pasta"
                );

        final RecommendationState updated =
                mock(RecommendationState.class);

        when(updated.getRecommendationError())
                .thenReturn(null);

        when(updated.getRecommendedRecipes())
                .thenReturn(
                        List.of(recipe)
                );

        when(updated.getRecommendedExercises())
                .thenReturn(List.of());

        SwingUtilities.invokeAndWait(
                () -> view.propertyChange(
                        new PropertyChangeEvent(
                                viewModel,
                                "state",
                                state,
                                updated
                        )
                )
        );

        final JButton recipeButton =
                findButtonContaining(
                        view,
                        "Chicken Pasta"
                );

        SwingUtilities.invokeAndWait(
                recipeButton::doClick
        );

        verify(recipePresenter)
                .prepareRecipeView(recipe);

        verify(viewManagerModel)
                .setState(
                        RecipeViewModel.VIEW_NAME
                );

        verify(viewManagerModel)
                .firePropertyChanged();
    }

    @Test
    void unrelatedPropertyChangeDoesNotUpdateStaleFlag()
            throws Exception {

        final RecommendationState updated =
                mock(RecommendationState.class);

        SwingUtilities.invokeAndWait(
                () -> view.propertyChange(
                        new PropertyChangeEvent(
                                viewModel,
                                "other",
                                state,
                                updated
                        )
                )
        );

        verify(
                viewModel,
                never()
        ).setStale(false);
    }

    @Test
    void loggedInViewModelStateChangeCanFetchRecommendations()
            throws Exception {

        final RecommendationController controller =
                mock(RecommendationController.class);

        view.setRecommendationController(
                controller
        );

        when(loggedInState.getUsername())
                .thenReturn("ethan");

        when(viewModel.getState())
                .thenReturn(state);

        when(state.getRecommendedRecipes())
                .thenReturn(List.of());

        SwingUtilities.invokeAndWait(
                () -> view.propertyChange(
                        new PropertyChangeEvent(
                                loggedInViewModel,
                                "state",
                                null,
                                loggedInState
                        )
                )
        );

        verify(controller)
                .execute("ethan");
    }

    private static Recipe recipe(
            String name) {

        final Recipe recipe =
                mock(Recipe.class);

        when(recipe.getName())
                .thenReturn(name);

        return recipe;
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
                    // Continue.
                }
            }
        }

        throw new AssertionError(
                "Button not found: " + text
        );
    }

    private static JButton findButtonContaining(
            Container container,
            String text) {

        for (Component component
                : container.getComponents()) {

            if (component instanceof JButton button
                    && button.getText() != null
                    && button.getText()
                    .contains(text)) {

                return button;
            }

            if (component instanceof Container child) {
                try {
                    return findButtonContaining(
                            child,
                            text
                    );
                }
                catch (AssertionError ignored) {
                    // Continue.
                }
            }
        }

        throw new AssertionError(
                "Button not found containing: "
                        + text
        );
    }

    private static boolean containsButtonContaining(
            Container container,
            String text) {

        for (Component component
                : container.getComponents()) {

            if (component instanceof JButton button
                    && button.getText() != null
                    && button.getText()
                    .contains(text)) {

                return true;
            }

            if (component instanceof Container child
                    && containsButtonContaining(
                    child,
                    text
            )) {

                return true;
            }
        }

        return false;
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
}
