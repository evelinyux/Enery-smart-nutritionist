package enery.view.tracking;

import java.awt.Component;
import java.awt.Container;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

import enery.interface_adapter.change_password.LoggedInState;
import enery.interface_adapter.change_password.LoggedInViewModel;
import enery.interface_adapter.progress.ProgressController;
import enery.interface_adapter.progress.ProgressState;
import enery.interface_adapter.progress.ProgressViewModel;
import enery.interface_adapter.shared.ViewManagerModel;
import enery.view.home.HomeView;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProgressViewTest {

    private ProgressViewModel progressViewModel;
    private ViewManagerModel viewManagerModel;
    private LoggedInViewModel loggedInViewModel;

    private ProgressState progressState;
    private LoggedInState loggedInState;

    private ProgressView view;

    @BeforeEach
    void setUp() throws Exception {
        progressViewModel =
                mock(ProgressViewModel.class);

        viewManagerModel =
                mock(ViewManagerModel.class);

        loggedInViewModel =
                mock(LoggedInViewModel.class);

        progressState =
                mock(ProgressState.class);

        loggedInState =
                mock(LoggedInState.class);

        when(progressViewModel.getState())
                .thenReturn(progressState);

        when(loggedInViewModel.getState())
                .thenReturn(loggedInState);

        when(progressState.getProgressError())
                .thenReturn(null);

        SwingUtilities.invokeAndWait(
                () -> view =
                        new ProgressView(
                                progressViewModel,
                                viewManagerModel,
                                loggedInViewModel
                        )
        );
    }

    @Test
    void getViewNameReturnsProgress() {
        assertEquals(
                "progress",
                view.getViewName()
        );
    }

    @Test
    void initialProgressComponentsAreHidden() {
        final List<JProgressBar> bars =
                findComponents(
                        view,
                        JProgressBar.class
                );

        assertEquals(2, bars.size());

        assertFalse(
                bars.get(0).isVisible()
        );

        assertFalse(
                bars.get(1).isVisible()
        );

        assertFalse(
                findButton(
                        view,
                        "Recommend a meal"
                ).isVisible()
        );
    }

    @Test
    void updateWithoutControllerShowsError()
            throws Exception {

        SwingUtilities.invokeAndWait(
                () -> findButton(
                        view,
                        "View Plan Progress"
                ).doClick()
        );

        assertTrue(
                containsLabelText(
                        view,
                        "Progress is not configured"
                )
        );
    }

    @Test
    void updateCallsControllerForLoggedInUser()
            throws Exception {

        final ProgressController controller =
                mock(ProgressController.class);

        view.setProgressController(
                controller
        );

        when(loggedInState.getUsername())
                .thenReturn("ethan");

        when(progressState.getProgressError())
                .thenReturn(null);

        SwingUtilities.invokeAndWait(
                () -> findButton(
                        view,
                        "View Plan Progress"
                ).doClick()
        );

        verify(controller)
                .execute("ethan");

        verify(progressViewModel)
                .firePropertyChanged("data");
    }

    @Test
    void updateDoesNotFireDataWhenProgressHasError()
            throws Exception {

        final ProgressController controller =
                mock(ProgressController.class);

        view.setProgressController(
                controller
        );

        when(loggedInState.getUsername())
                .thenReturn("ethan");

        when(progressState.getProgressError())
                .thenReturn("failure");

        SwingUtilities.invokeAndWait(
                () -> findButton(
                        view,
                        "View Plan Progress"
                ).doClick()
        );

        verify(controller)
                .execute("ethan");
    }

    @Test
    void successfulUpdateChangesButtonText()
            throws Exception {

        final ProgressController controller =
                mock(ProgressController.class);

        view.setProgressController(
                controller
        );

        when(loggedInState.getUsername())
                .thenReturn("ethan");

        when(progressState.getProgressError())
                .thenReturn(null);

        final JButton button =
                findButton(
                        view,
                        "View Plan Progress"
                );

        SwingUtilities.invokeAndWait(
                button::doClick
        );

        assertEquals(
                "Update Plan Progress",
                button.getText()
        );
    }

    @Test
    void errorPropertyChangeDisplaysError()
            throws Exception {

        when(progressState.getProgressError())
                .thenReturn(
                        "Unable to load progress."
                );

        SwingUtilities.invokeAndWait(
                () -> view.propertyChange(
                        new PropertyChangeEvent(
                                progressViewModel,
                                "error",
                                null,
                                progressState
                        )
                )
        );

        assertTrue(
                containsLabelText(
                        view,
                        "Unable to load progress."
                )
        );
    }

    @Test
    void unrelatedPropertyChangeDoesNothing()
            throws Exception {

        SwingUtilities.invokeAndWait(
                () -> view.propertyChange(
                        new PropertyChangeEvent(
                                progressViewModel,
                                "other",
                                null,
                                progressState
                        )
                )
        );

        final List<JProgressBar> bars =
                findComponents(
                        view,
                        JProgressBar.class
                );

        assertFalse(
                bars.get(0).isVisible()
        );
    }

    @Test
    void dataPropertyChangeShowsDailyProgressBelowGoal()
            throws Exception {

        prepareProgressStateBelowGoal();

        SwingUtilities.invokeAndWait(
                () -> view.propertyChange(
                        new PropertyChangeEvent(
                                progressViewModel,
                                "data",
                                null,
                                progressState
                        )
                )
        );

        final List<JProgressBar> bars =
                findComponents(
                        view,
                        JProgressBar.class
                );

        assertTrue(
                bars.get(0).isVisible()
        );

        assertEquals(
                50,
                bars.get(0).getValue()
        );

        assertTrue(
                bars.get(1).isVisible()
        );

        assertEquals(
                50,
                bars.get(1).getValue()
        );

        assertTrue(
                findButton(
                        view,
                        "Recommend a meal"
                ).isVisible()
        );

        assertTrue(
                containsLabelContaining(
                        view,
                        "50%"
                )
        );
    }

    @Test
    void dataPropertyChangeHidesRecommendationAfterGoal()
            throws Exception {

        prepareProgressStateAboveGoal();

        SwingUtilities.invokeAndWait(
                () -> view.propertyChange(
                        new PropertyChangeEvent(
                                progressViewModel,
                                "data",
                                null,
                                progressState
                        )
                )
        );

        assertFalse(
                findButton(
                        view,
                        "Recommend a meal"
                ).isVisible()
        );

        assertTrue(
                containsLabelContaining(
                        view,
                        "achieved your Daily Goal"
                )
        );
    }

    @Test
    void dataPropertyChangeDisplaysNutrients()
            throws Exception {

        prepareProgressStateBelowGoal();

        SwingUtilities.invokeAndWait(
                () -> view.propertyChange(
                        new PropertyChangeEvent(
                                progressViewModel,
                                "data",
                                null,
                                progressState
                        )
                )
        );

        final JTextPane textPane =
                findComponent(
                        view,
                        JTextPane.class
                );

        assertTrue(
                textPane.isVisible()
        );

        assertTrue(
                textPane.getText()
                        .contains(
                                "Protein: 80.0g"
                        )
        );

        assertTrue(
                textPane.getText()
                        .contains(
                                "Carbohydrates: 200.0g"
                        )
        );

        assertTrue(
                textPane.getText()
                        .contains(
                                "Fats: 60.0g"
                        )
        );
    }

    @Test
    void dataPropertyChangeDisplaysPlanProgress()
            throws Exception {

        prepareProgressStateBelowGoal();

        SwingUtilities.invokeAndWait(
                () -> view.propertyChange(
                        new PropertyChangeEvent(
                                progressViewModel,
                                "data",
                                null,
                                progressState
                        )
                )
        );

        assertTrue(
                containsLabelContaining(
                        view,
                        "Over the course of 5 days"
                )
        );

        assertTrue(
                containsLabelContaining(
                        view,
                        "8000.0 kcal"
                )
        );
    }

    @Test
    void recommendationButtonNavigates()
            throws Exception {

        prepareProgressStateBelowGoal();

        SwingUtilities.invokeAndWait(
                () -> view.propertyChange(
                        new PropertyChangeEvent(
                                progressViewModel,
                                "data",
                                null,
                                progressState
                        )
                )
        );

        SwingUtilities.invokeAndWait(
                () -> findButton(
                        view,
                        "Recommend a meal"
                ).doClick()
        );

        verify(viewManagerModel)
                .setState(
                        "recommendation"
                );

        verify(viewManagerModel)
                .firePropertyChanged();
    }

    @Test
    void homeButtonNavigatesHome()
            throws Exception {

        SwingUtilities.invokeAndWait(
                () -> findButton(
                        view,
                        "Home"
                ).doClick()
        );

        verify(viewManagerModel)
                .setState(
                        HomeView.VIEW_NAME
                );

        verify(viewManagerModel)
                .firePropertyChanged();
    }

    private void prepareProgressStateBelowGoal() {
        when(progressState.getProgressError())
                .thenReturn(null);

        when(progressState.getTodaysCalories())
                .thenReturn(1000.0);

        when(progressState.getDailyCalorieGoal())
                .thenReturn(2000.0);

        when(progressState.getDailyProgressPercent())
                .thenReturn(0.5);

        when(progressState.getTodaysProtein())
                .thenReturn(80.0);

        when(progressState.getTodaysCarboydrates())
                .thenReturn(200.0);

        when(progressState.getTodaysFats())
                .thenReturn(60.0);

        when(progressState.getDaysSoFar())
                .thenReturn(5);

        when(progressState.getTotalCaloriesConsumed())
                .thenReturn(8000.0);

        when(progressState.getTotalCalorieGoal())
                .thenReturn(10000.0);

        when(progressState.getTotalProgressPercent())
                .thenReturn(0.8);
    }

    private void prepareProgressStateAboveGoal() {
        when(progressState.getProgressError())
                .thenReturn(null);

        when(progressState.getTodaysCalories())
                .thenReturn(2200.0);

        when(progressState.getDailyCalorieGoal())
                .thenReturn(2000.0);

        when(progressState.getDailyProgressPercent())
                .thenReturn(1.1);

        when(progressState.getTodaysProtein())
                .thenReturn(100.0);

        when(progressState.getTodaysCarboydrates())
                .thenReturn(250.0);

        when(progressState.getTodaysFats())
                .thenReturn(70.0);

        when(progressState.getDaysSoFar())
                .thenReturn(3);

        when(progressState.getTotalCaloriesConsumed())
                .thenReturn(6000.0);

        when(progressState.getTotalCalorieGoal())
                .thenReturn(6000.0);

        when(progressState.getTotalProgressPercent())
                .thenReturn(1.0);
    }

    private static JButton findButton(
            Container container,
            String text) {

        for (Component component :
                container.getComponents()) {

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

        for (Component component :
                container.getComponents()) {

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

    private static boolean containsLabelContaining(
            Container container,
            String text) {

        for (Component component :
                container.getComponents()) {

            if (component instanceof JLabel label
                    && label.getText() != null
                    && label.getText()
                    .contains(text)) {

                return true;
            }

            if (component instanceof Container child
                    && containsLabelContaining(
                    child,
                    text
            )) {

                return true;
            }
        }

        return false;
    }

    private static <T extends Component>
    T findComponent(
            Container container,
            Class<T> type) {

        final List<T> components =
                findComponents(
                        container,
                        type
                );

        if (components.isEmpty()) {
            throw new AssertionError(
                    "Component not found: "
                            + type.getSimpleName()
            );
        }

        return components.get(0);
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

        for (Component component :
                container.getComponents()) {

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
