package enery.view.health_assessment;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import enery.interface_adapter.change_password.LoggedInState;
import enery.interface_adapter.change_password.LoggedInViewModel;
import enery.interface_adapter.health_assessment.HealthAssessmentController;
import enery.interface_adapter.health_assessment.HealthAssessmentState;
import enery.interface_adapter.health_assessment.HealthAssessmentViewModel;
import enery.interface_adapter.shared.ViewManagerModel;
import enery.use_case.tracking.TrackingOutputData;
import enery.view.home.HomeView;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class HealthAssessmentViewTest {

    private HealthAssessmentViewModel viewModel;
    private HealthAssessmentController controller;
    private ViewManagerModel viewManagerModel;
    private LoggedInViewModel loggedInViewModel;

    private HealthAssessmentState state;
    private LoggedInState loggedInState;

    private HealthAssessmentView view;

    @BeforeEach
    void setUp() throws Exception {
        viewModel =
                mock(HealthAssessmentViewModel.class);

        controller =
                mock(HealthAssessmentController.class);

        viewManagerModel =
                mock(ViewManagerModel.class);

        loggedInViewModel =
                mock(LoggedInViewModel.class);

        state =
                mock(HealthAssessmentState.class);

        loggedInState =
                mock(LoggedInState.class);

        when(viewModel.getState())
                .thenReturn(state);

        when(loggedInViewModel.getState())
                .thenReturn(loggedInState);

        SwingUtilities.invokeAndWait(
                () -> view =
                        new HealthAssessmentView(
                                viewModel,
                                controller,
                                viewManagerModel,
                                loggedInViewModel
                        )
        );
    }

    @Test
    void viewNameIsHealthAssessment() {
        assertEquals(
                "health assessment",
                view.viewName
        );
    }

    @Test
    void assessButtonCallsController()
            throws Exception {

        final TrackingOutputData trackingData =
                mock(TrackingOutputData.class);

        when(loggedInState.getUsername())
                .thenReturn("ethan");

        when(state.getTrackingData())
                .thenReturn(trackingData);

        SwingUtilities.invokeAndWait(
                () -> findButton(
                        view,
                        "Assess"
                ).doClick()
        );

        verify(controller)
                .execute(
                        "ethan",
                        trackingData
                );
    }

    @Test
    void successfulPropertyChangeDisplaysScoreAndSeverity()
            throws Exception {

        final HealthAssessmentState updated =
                mock(HealthAssessmentState.class);

        when(updated.getAssessmentError())
                .thenReturn(null);

        when(updated.getDietScore())
                .thenReturn(85);

        when(updated.getSeverity())
                .thenReturn("Good");

        when(updated.getDietInsights())
                .thenReturn(null);

        when(updated.getDietActionableItems())
                .thenReturn(null);

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
                        "Overall Score: 85"
                )
        );

        assertTrue(
                containsLabelText(
                        view,
                        "Severity: Good"
                )
        );
    }

    @Test
    void successfulPropertyChangeDisplaysInsights()
            throws Exception {

        final HealthAssessmentState updated =
                mock(HealthAssessmentState.class);

        when(updated.getAssessmentError())
                .thenReturn(null);

        when(updated.getDietScore())
                .thenReturn(75);

        when(updated.getSeverity())
                .thenReturn("Moderate");

        when(updated.getDietInsights())
                .thenReturn(
                        List.of(
                                "Eat more vegetables",
                                "Reduce sodium"
                        )
                );

        when(updated.getDietActionableItems())
                .thenReturn(
                        List.of(
                                "Add one vegetable serving",
                                "Choose lower-sodium foods"
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

        final List<JTextArea> areas =
                findComponents(
                        view,
                        JTextArea.class
                );

        assertEquals(
                2,
                areas.size()
        );

        assertTrue(
                containsTextAreaText(
                        areas,
                        "Eat more vegetables\nReduce sodium"
                )
        );

        assertTrue(
                containsTextAreaText(
                        areas,
                        "Add one vegetable serving\n"
                                + "Choose lower-sodium foods"
                )
        );
    }

    @Test
    void nullInsightListsDoNotThrow()
            throws Exception {

        final HealthAssessmentState updated =
                mock(HealthAssessmentState.class);

        when(updated.getAssessmentError())
                .thenReturn(null);

        when(updated.getDietScore())
                .thenReturn(50);

        when(updated.getSeverity())
                .thenReturn("Needs Improvement");

        when(updated.getDietInsights())
                .thenReturn(null);

        when(updated.getDietActionableItems())
                .thenReturn(null);

        SwingUtilities.invokeAndWait(
                () -> view.propertyChange(
                        new PropertyChangeEvent(
                                viewModel,
                                "state",
                                null,
                                updated
                        )
                )
        );

        assertTrue(
                containsLabelText(
                        view,
                        "Severity: Needs Improvement"
                )
        );
    }

    @Test
    void backButtonReturnsHome()
            throws Exception {

        when(state.getUserId())
                .thenReturn("ethan");

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
    void backButtonResetsViewModelState()
            throws Exception {

        when(state.getUserId())
                .thenReturn("ethan");

        SwingUtilities.invokeAndWait(
                () -> findButton(
                        view,
                        "Back to Home"
                ).doClick()
        );

        final ArgumentCaptor<HealthAssessmentState> captor =
                ArgumentCaptor.forClass(
                        HealthAssessmentState.class
                );

        verify(viewModel)
                .setState(
                        captor.capture()
                );

        assertEquals(
                "ethan",
                captor.getValue()
                        .getUserId()
        );
    }

    @Test
    void backButtonClearsDisplayedAssessment()
            throws Exception {

        final HealthAssessmentState updated =
                mock(HealthAssessmentState.class);

        when(updated.getAssessmentError())
                .thenReturn(null);

        when(updated.getDietScore())
                .thenReturn(90);

        when(updated.getSeverity())
                .thenReturn("Excellent");

        when(updated.getDietInsights())
                .thenReturn(
                        List.of("Insight")
                );

        when(updated.getDietActionableItems())
                .thenReturn(
                        List.of("Action")
                );

        when(viewModel.getState())
                .thenReturn(updated);

        SwingUtilities.invokeAndWait(() -> {
            view.propertyChange(
                    new PropertyChangeEvent(
                            viewModel,
                            "state",
                            null,
                            updated
                    )
            );

            findButton(
                    view,
                    "Back to Home"
            ).doClick();
        });

        assertTrue(
                containsLabelText(
                        view,
                        "Overall Score: "
                )
        );

        assertTrue(
                containsLabelText(
                        view,
                        "Severity: "
                )
        );

        final List<JTextArea> areas =
                findComponents(
                        view,
                        JTextArea.class
                );

        for (JTextArea area : areas) {
            assertEquals(
                    "",
                    area.getText()
            );
        }
    }

    @Test
    void actionPerformedMethodAcceptsEvent() {
        view.actionPerformed(
                new ActionEvent(
                        view,
                        ActionEvent.ACTION_PERFORMED,
                        "test"
                )
        );
    }

    private static boolean containsTextAreaText(
            List<JTextArea> areas,
            String text) {

        for (JTextArea area : areas) {
            if (text.equals(area.getText())) {
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
