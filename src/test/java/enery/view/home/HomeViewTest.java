package enery.view.home;

import java.awt.Component;
import java.awt.Container;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import enery.interface_adapter.change_password.LoggedInState;
import enery.interface_adapter.change_password.LoggedInViewModel;
import enery.interface_adapter.health_assessment.HealthAssessmentViewModel;
import enery.interface_adapter.log_exercise.LogExerciseViewModel;
import enery.interface_adapter.log_meal.LogMealViewModel;
import enery.interface_adapter.logout.LogoutController;
import enery.interface_adapter.profile.ProfileViewModel;
import enery.interface_adapter.progress.ProgressViewModel;
import enery.interface_adapter.recommendation.RecommendationViewModel;
import enery.interface_adapter.shared.ViewManagerModel;
import enery.interface_adapter.tracking.TrackingViewModel;
import enery.view.auth.ChangePasswordView;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class HomeViewTest {

    private LoggedInViewModel loggedInViewModel;
    private ViewManagerModel viewManagerModel;
    private LoggedInState loggedInState;
    private HomeView view;

    @BeforeEach
    void setUp() throws Exception {
        loggedInViewModel =
                mock(LoggedInViewModel.class);

        viewManagerModel =
                mock(ViewManagerModel.class);

        loggedInState =
                mock(LoggedInState.class);

        when(loggedInViewModel.getState())
                .thenReturn(loggedInState);

        when(loggedInState.isProfileComplete())
                .thenReturn(false);

        when(loggedInState.getUsername())
                .thenReturn("ethan");

        SwingUtilities.invokeAndWait(
                () -> view =
                        new HomeView(
                                loggedInViewModel,
                                viewManagerModel
                        )
        );
    }

    @Test
    void getViewNameReturnsHome() {
        assertEquals(
                HomeView.VIEW_NAME,
                view.getViewName()
        );
    }

    @Test
    void incompleteProfileDisablesDependentFeatures() {
        assertFalse(
                findButton(
                        view,
                        "Log Meal"
                ).isEnabled()
        );

        assertFalse(
                findButton(
                        view,
                        "Log Exercise"
                ).isEnabled()
        );

        assertFalse(
                findButton(
                        view,
                        "View Daily Goal Progress"
                ).isEnabled()
        );

        assertFalse(
                findButton(
                        view,
                        "Recommendations"
                ).isEnabled()
        );

        assertFalse(
                findButton(
                        view,
                        "Nutrition and Exercise Tracking"
                ).isEnabled()
        );

        assertFalse(
                findButton(
                        view,
                        "Health Assessment"
                ).isEnabled()
        );
    }

    @Test
    void incompleteProfileStillAllowsEssentialFeatures() {
        assertTrue(
                findButton(
                        view,
                        "Profile"
                ).isEnabled()
        );

        assertTrue(
                findButton(
                        view,
                        "Change Password"
                ).isEnabled()
        );

        assertTrue(
                findButton(
                        view,
                        "Log Out"
                ).isEnabled()
        );
    }

    @Test
    void incompleteProfileShowsNoticeAndTooltip() {
        assertTrue(
                containsLabelText(
                        view,
                        "Please complete your profile "
                                + "to unlock the other features."
                )
        );

        assertEquals(
                "Complete your profile first.",
                findButton(
                        view,
                        "Log Meal"
                ).getToolTipText()
        );
    }

    @Test
    void profileButtonNavigatesToProfile()
            throws Exception {

        SwingUtilities.invokeAndWait(
                () -> findButton(
                        view,
                        "Profile"
                ).doClick()
        );

        verify(viewManagerModel)
                .setState(
                        ProfileViewModel.VIEW_NAME
                );

        verify(viewManagerModel)
                .firePropertyChanged();
    }

    @Test
    void changePasswordButtonNavigates()
            throws Exception {

        SwingUtilities.invokeAndWait(
                () -> findButton(
                        view,
                        "Change Password"
                ).doClick()
        );

        verify(viewManagerModel)
                .setState(
                        ChangePasswordView.VIEW_NAME
                );

        verify(viewManagerModel)
                .firePropertyChanged();
    }

    @Test
    void logoutWithoutControllerDoesNothing()
            throws Exception {

        SwingUtilities.invokeAndWait(
                () -> findButton(
                        view,
                        "Log Out"
                ).doClick()
        );

        verify(
                viewManagerModel,
                never()
        ).firePropertyChanged();
    }

    @Test
    void logoutCallsControllerWithUsername()
            throws Exception {

        final LogoutController controller =
                mock(LogoutController.class);

        view.setLogoutController(
                controller
        );

        when(loggedInState.getUsername())
                .thenReturn("ethan");

        SwingUtilities.invokeAndWait(
                () -> findButton(
                        view,
                        "Log Out"
                ).doClick()
        );

        verify(controller)
                .execute("ethan");
    }

    @Test
    void completedProfileEnablesAllDependentFeatures()
            throws Exception {

        when(loggedInState.isProfileComplete())
                .thenReturn(true);

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

        assertTrue(
                findButton(
                        view,
                        "Log Meal"
                ).isEnabled()
        );

        assertTrue(
                findButton(
                        view,
                        "Log Exercise"
                ).isEnabled()
        );

        assertTrue(
                findButton(
                        view,
                        "View Daily Goal Progress"
                ).isEnabled()
        );

        assertTrue(
                findButton(
                        view,
                        "Recommendations"
                ).isEnabled()
        );

        assertTrue(
                findButton(
                        view,
                        "Nutrition and Exercise Tracking"
                ).isEnabled()
        );

        assertTrue(
                findButton(
                        view,
                        "Health Assessment"
                ).isEnabled()
        );
    }

    @Test
    void completedProfileClearsNoticeAndTooltip()
            throws Exception {

        when(loggedInState.isProfileComplete())
                .thenReturn(true);

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

        assertFalse(
                containsLabelText(
                        view,
                        "Please complete your profile "
                                + "to unlock the other features."
                )
        );

        assertEquals(
                null,
                findButton(
                        view,
                        "Log Meal"
                ).getToolTipText()
        );
    }

    @Test
    void propertyChangeUpdatesWelcomeMessage()
            throws Exception {

        when(loggedInState.getUsername())
                .thenReturn("Ethan");

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

        assertTrue(
                containsLabelText(
                        view,
                        "Welcome, Ethan"
                )
        );
    }

    @Test
    void unrelatedPropertyChangeIsIgnored()
            throws Exception {

        SwingUtilities.invokeAndWait(
                () -> view.propertyChange(
                        new PropertyChangeEvent(
                                loggedInViewModel,
                                "other",
                                null,
                                loggedInState
                        )
                )
        );

        assertFalse(
                containsLabelText(
                        view,
                        "Welcome, Ethan"
                )
        );
    }

    @Test
    void allNavigationButtonsNavigateWhenProfileComplete()
            throws Exception {

        when(loggedInState.isProfileComplete())
                .thenReturn(true);

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

        clickAndVerifyNavigation(
                "Log Meal",
                LogMealViewModel.VIEW_NAME
        );

        clickAndVerifyNavigation(
                "Log Exercise",
                LogExerciseViewModel.VIEW_NAME
        );

        clickAndVerifyNavigation(
                "View Daily Goal Progress",
                ProgressViewModel.VIEW_NAME
        );

        clickAndVerifyNavigation(
                "Recommendations",
                RecommendationViewModel.VIEW_NAME
        );

        clickAndVerifyNavigation(
                "Nutrition and Exercise Tracking",
                TrackingViewModel.VIEW_NAME
        );

        clickAndVerifyNavigation(
                "Health Assessment",
                HealthAssessmentViewModel.VIEW_NAME
        );
    }

    private void clickAndVerifyNavigation(
            String buttonText,
            String viewName) throws Exception {

        SwingUtilities.invokeAndWait(
                () -> findButton(
                        view,
                        buttonText
                ).doClick()
        );

        verify(viewManagerModel)
                .setState(viewName);
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
                    // Keep searching.
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
}
