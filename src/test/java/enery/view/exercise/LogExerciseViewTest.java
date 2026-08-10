package enery.view.exercise;

import java.awt.Component;
import java.awt.Container;
import java.beans.PropertyChangeEvent;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import enery.entity.exercise.ExerciseSearchResult;
import enery.interface_adapter.change_password.LoggedInState;
import enery.interface_adapter.change_password.LoggedInViewModel;
import enery.interface_adapter.log_exercise.LogExerciseController;
import enery.interface_adapter.log_exercise.LogExerciseState;
import enery.interface_adapter.log_exercise.LogExerciseViewModel;
import enery.interface_adapter.search_exercise.SearchExerciseController;
import enery.interface_adapter.search_exercise.SearchExerciseState;
import enery.interface_adapter.search_exercise.SearchExerciseViewModel;
import enery.interface_adapter.shared.ViewManagerModel;
import enery.view.home.HomeView;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LogExerciseViewTest {

    private SearchExerciseViewModel searchViewModel;
    private LogExerciseViewModel logExerciseViewModel;
    private ViewManagerModel viewManagerModel;
    private LoggedInViewModel loggedInViewModel;

    private SearchExerciseState searchState;
    private LogExerciseState logState;
    private LoggedInState loggedInState;

    private LogExerciseView view;

    @BeforeEach
    void setUp() throws Exception {
        searchViewModel =
                mock(SearchExerciseViewModel.class);

        logExerciseViewModel =
                mock(LogExerciseViewModel.class);

        viewManagerModel =
                mock(ViewManagerModel.class);

        loggedInViewModel =
                mock(LoggedInViewModel.class);

        searchState =
                mock(SearchExerciseState.class);

        logState =
                mock(LogExerciseState.class);

        loggedInState =
                mock(LoggedInState.class);

        when(searchViewModel.getState())
                .thenReturn(searchState);

        when(logExerciseViewModel.getState())
                .thenReturn(logState);

        when(loggedInViewModel.getState())
                .thenReturn(loggedInState);

        when(searchState.getResults())
                .thenReturn(List.of());

        when(searchState.getErrorMessage())
                .thenReturn("");

        when(logState.getErrorMessage())
                .thenReturn("");

        SwingUtilities.invokeAndWait(
                () -> view =
                        new LogExerciseView(
                                searchViewModel,
                                logExerciseViewModel,
                                viewManagerModel,
                                loggedInViewModel
                        )
        );
    }

    @Test
    void getViewNameReturnsExpectedName() {
        assertEquals(
                LogExerciseView.VIEW_NAME,
                view.getViewName()
        );
    }

    @Test
    void searchWithoutControllerShowsError()
            throws Exception {

        SwingUtilities.invokeAndWait(
                () -> findButton(
                        view,
                        "Search"
                ).doClick()
        );

        assertTrue(
                containsLabelText(
                        view,
                        "Exercise search is not configured."
                )
        );
    }

    @Test
    void invalidDurationShowsError()
            throws Exception {

        final SearchExerciseController controller =
                mock(SearchExerciseController.class);

        view.setSearchController(
                controller
        );

        final List<JTextField> fields =
                findTextFields(view);

        SwingUtilities.invokeAndWait(() -> {
            fields.get(1)
                    .setText("abc");

            findButton(
                    view,
                    "Search"
            ).doClick();
        });

        assertTrue(
                containsLabelText(
                        view,
                        "Duration must be a number."
                )
        );
    }

    @Test
    void validSearchCallsController()
            throws Exception {

        final SearchExerciseController controller =
                mock(SearchExerciseController.class);

        view.setSearchController(
                controller
        );

        when(loggedInState.getUsername())
                .thenReturn("ethan");

        final List<JTextField> fields =
                findTextFields(view);

        SwingUtilities.invokeAndWait(() -> {
            fields.get(0)
                    .setText("Cycling");

            fields.get(1)
                    .setText("45");

            findButton(
                    view,
                    "Search"
            ).doClick();
        });

        verify(controller).execute(
                "ethan",
                "Cycling",
                45
        );
    }

    @Test
    void logWithoutControllerShowsError()
            throws Exception {

        SwingUtilities.invokeAndWait(
                () -> findButton(
                        view,
                        "Log Exercise"
                ).doClick()
        );

        assertTrue(
                containsLabelText(
                        view,
                        "Exercise logging is not configured."
                )
        );
    }

    @Test
    void noSelectedExerciseShowsError()
            throws Exception {

        final LogExerciseController controller =
                mock(LogExerciseController.class);

        view.setLogController(
                controller
        );

        SwingUtilities.invokeAndWait(
                () -> findButton(
                        view,
                        "Log Exercise"
                ).doClick()
        );

        assertTrue(
                containsLabelText(
                        view,
                        "Select an exercise result first."
                )
        );
    }

    @Test
    void selectedExerciseCallsLogController()
            throws Exception {

        final LogExerciseController controller =
                mock(LogExerciseController.class);

        view.setLogController(controller);

        when(loggedInState.getUsername())
                .thenReturn("ethan");

        final ExerciseSearchResult result =
                mock(ExerciseSearchResult.class);

        when(result.getExerciseName())
                .thenReturn("Cycling");

        when(result.getDurationInMinutes())
                .thenReturn(30);

        when(result.getTotalCalories())
                .thenReturn(250.0);

        final JComboBox<?> box =
                findComboBox(view);

        SwingUtilities.invokeAndWait(
                () -> ((JComboBox<ExerciseSearchResult>) box)
                        .addItem(result)
        );

        SwingUtilities.invokeAndWait(
                () -> findButton(
                        view,
                        "Log Exercise"
                ).doClick()
        );

        verify(controller).execute(
                eq("ethan"),
                eq("Cycling"),
                eq(30),
                eq(250.0),
                any(LocalDate.class)
        );
    }

    @Test
    void selectingExerciseUpdatesCaloriesLabel()
            throws Exception {

        final ExerciseSearchResult result =
                mock(ExerciseSearchResult.class);

        when(result.getTotalCalories())
                .thenReturn(333.5);

        final JComboBox<?> box =
                findComboBox(view);

        SwingUtilities.invokeAndWait(
                () -> ((JComboBox<ExerciseSearchResult>) box)
                        .addItem(result)
        );

        assertTrue(
                containsLabelText(
                        view,
                        "Calories burned: 333.5"
                )
        );
    }

    @Test
    void emptySelectionClearsCaloriesLabel()
            throws Exception {

        final JComboBox<?> box =
                findComboBox(view);

        SwingUtilities.invokeAndWait(
                () -> box.setSelectedItem(null)
        );

        assertTrue(
                containsLabelText(
                        view,
                        "Calories burned: "
                )
        );
    }

    @Test
    void searchStatePopulatesResults()
            throws Exception {

        final ExerciseSearchResult first =
                mock(ExerciseSearchResult.class);

        final ExerciseSearchResult second =
                mock(ExerciseSearchResult.class);

        when(searchState.getResults())
                .thenReturn(
                        List.of(
                                first,
                                second
                        )
                );

        when(searchState.getErrorMessage())
                .thenReturn("");

        SwingUtilities.invokeAndWait(
                () -> view.propertyChange(
                        new PropertyChangeEvent(
                                searchViewModel,
                                "state",
                                null,
                                searchState
                        )
                )
        );

        assertEquals(
                2,
                findComboBox(view)
                        .getItemCount()
        );

        assertTrue(
                containsLabelText(
                        view,
                        "2 exercise matches found."
                )
        );
    }

    @Test
    void searchStateErrorIsDisplayed()
            throws Exception {

        when(searchState.getResults())
                .thenReturn(List.of());

        when(searchState.getErrorMessage())
                .thenReturn(
                        "Search failed"
                );

        SwingUtilities.invokeAndWait(
                () -> view.propertyChange(
                        new PropertyChangeEvent(
                                searchViewModel,
                                "state",
                                null,
                                searchState
                        )
                )
        );

        assertTrue(
                containsLabelText(
                        view,
                        "Search failed"
                )
        );
    }

    @Test
    void logSuccessMessageIsDisplayed()
            throws Exception {

        when(logState.getErrorMessage())
                .thenReturn("");

        when(logState.getSuccessMessage())
                .thenReturn(
                        "Exercise saved."
                );

        SwingUtilities.invokeAndWait(
                () -> view.propertyChange(
                        new PropertyChangeEvent(
                                logExerciseViewModel,
                                "state",
                                null,
                                logState
                        )
                )
        );

        assertTrue(
                containsLabelText(
                        view,
                        "Exercise saved."
                )
        );
    }

    @Test
    void logErrorIsDisplayed()
            throws Exception {

        when(logState.getErrorMessage())
                .thenReturn(
                        "Unable to save exercise."
                );

        SwingUtilities.invokeAndWait(
                () -> view.propertyChange(
                        new PropertyChangeEvent(
                                logExerciseViewModel,
                                "state",
                                null,
                                logState
                        )
                )
        );

        assertTrue(
                containsLabelText(
                        view,
                        "Unable to save exercise."
                )
        );
    }

    @Test
    void unrelatedPropertyChangeIsIgnored()
            throws Exception {

        SwingUtilities.invokeAndWait(
                () -> view.propertyChange(
                        new PropertyChangeEvent(
                                searchViewModel,
                                "other",
                                null,
                                searchState
                        )
                )
        );

        assertEquals(
                0,
                findComboBox(view)
                        .getItemCount()
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

    private static List<JTextField> findTextFields(
            Container container) {

        final List<JTextField> result =
                new java.util.ArrayList<>();

        collect(
                container,
                JTextField.class,
                result
        );

        return result;
    }

    private static JComboBox<?> findComboBox(
            Container container) {

        final List<JComboBox> boxes =
                new java.util.ArrayList<>();

        collect(
                container,
                JComboBox.class,
                boxes
        );

        if (boxes.isEmpty()) {
            throw new AssertionError(
                    "Combo box not found."
            );
        }

        return boxes.get(0);
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
    void collect(
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
                collect(
                        child,
                        type,
                        result
                );
            }
        }
    }
}
