package enery.view.auth;

import java.awt.Component;
import java.awt.Container;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;

import enery.interface_adapter.change_password.ChangePasswordController;
import enery.interface_adapter.change_password.LoggedInState;
import enery.interface_adapter.change_password.LoggedInViewModel;
import enery.interface_adapter.shared.ViewManagerModel;
import enery.view.home.HomeView;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ChangePasswordViewTest {

    private LoggedInViewModel loggedInViewModel;
    private ViewManagerModel viewManagerModel;
    private LoggedInState loggedInState;
    private ChangePasswordView view;

    @BeforeEach
    void setUp() throws Exception {
        loggedInViewModel =
                mock(LoggedInViewModel.class);

        viewManagerModel =
                mock(ViewManagerModel.class);

        loggedInState =
                new LoggedInState();

        when(loggedInViewModel.getState())
                .thenReturn(loggedInState);

        SwingUtilities.invokeAndWait(
                () -> view =
                        new ChangePasswordView(
                                loggedInViewModel,
                                viewManagerModel
                        )
        );
    }

    @Test
    void getViewNameReturnsExpectedName() {
        assertEquals(
                ChangePasswordView.VIEW_NAME,
                view.getViewName()
        );
    }

    @Test
    void changePasswordWithoutControllerShowsError()
            throws Exception {

        SwingUtilities.invokeAndWait(
                () -> findButton(
                        view,
                        "Change Password"
                ).doClick()
        );

        assertTrue(
                containsLabelText(
                        view,
                        "Change-password controller is not configured."
                )
        );
    }

    @Test
    void blankPasswordShowsValidationMessage()
            throws Exception {

        final ChangePasswordController controller =
                mock(ChangePasswordController.class);

        view.setChangePasswordController(
                controller
        );

        final List<JPasswordField> fields =
                findComponents(
                        view,
                        JPasswordField.class
                );

        SwingUtilities.invokeAndWait(() -> {
            fields.get(0).setText("");
            fields.get(1).setText("");

            findButton(
                    view,
                    "Change Password"
            ).doClick();
        });

        assertTrue(
                containsLabelText(
                        view,
                        "New password cannot be empty."
                )
        );
    }

    @Test
    void mismatchedPasswordsShowValidationMessage()
            throws Exception {

        final ChangePasswordController controller =
                mock(ChangePasswordController.class);

        view.setChangePasswordController(
                controller
        );

        final List<JPasswordField> fields =
                findComponents(
                        view,
                        JPasswordField.class
                );

        SwingUtilities.invokeAndWait(() -> {
            fields.get(0).setText("abc123");
            fields.get(1).setText("different");

            findButton(
                    view,
                    "Change Password"
            ).doClick();
        });

        assertTrue(
                containsLabelText(
                        view,
                        "The passwords do not match."
                )
        );
    }

    @Test
    void missingLoggedInUserShowsValidationMessage()
            throws Exception {

        final ChangePasswordController controller =
                mock(ChangePasswordController.class);

        view.setChangePasswordController(
                controller
        );

        loggedInState.setUsername("");

        final List<JPasswordField> fields =
                findComponents(
                        view,
                        JPasswordField.class
                );

        SwingUtilities.invokeAndWait(() -> {
            fields.get(0).setText("abc123");
            fields.get(1).setText("abc123");

            findButton(
                    view,
                    "Change Password"
            ).doClick();
        });

        assertTrue(
                containsLabelText(
                        view,
                        "No user is currently logged in."
                )
        );
    }

    @Test
    void validInputCallsController()
            throws Exception {

        final ChangePasswordController controller =
                mock(ChangePasswordController.class);

        view.setChangePasswordController(
                controller
        );

        loggedInState.setUsername(
                "ethan"
        );

        final List<JPasswordField> fields =
                findComponents(
                        view,
                        JPasswordField.class
                );

        SwingUtilities.invokeAndWait(() -> {
            fields.get(0).setText("newPassword");
            fields.get(1).setText("newPassword");

            findButton(
                    view,
                    "Change Password"
            ).doClick();
        });

        verify(controller).execute(
                "newPassword",
                "ethan"
        );
    }

    @Test
    void backButtonReturnsToHome()
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
    void passwordPropertyChangeShowsSuccessMessage()
            throws Exception {

        final List<JPasswordField> fields =
                findComponents(
                        view,
                        JPasswordField.class
                );

        SwingUtilities.invokeAndWait(() -> {
            fields.get(0).setText("something");
            fields.get(1).setText("something");

            view.propertyChange(
                    new PropertyChangeEvent(
                            loggedInViewModel,
                            "password",
                            null,
                            "changed"
                    )
            );
        });

        assertEquals(
                "",
                new String(
                        fields.get(0)
                                .getPassword()
                )
        );

        assertEquals(
                "",
                new String(
                        fields.get(1)
                                .getPassword()
                )
        );

        assertTrue(
                containsLabelText(
                        view,
                        "Password changed successfully."
                )
        );
    }

    @Test
    void unrelatedPropertyChangeDoesNothing()
            throws Exception {

        SwingUtilities.invokeAndWait(
                () -> view.propertyChange(
                        new PropertyChangeEvent(
                                loggedInViewModel,
                                "username",
                                null,
                                "ethan"
                        )
                )
        );

        assertTrue(
                !containsLabelText(
                        view,
                        "Password changed successfully."
                )
        );
    }

    private static JButton findButton(
            Container container,
            String text) {

        for (Component component :
                container.getComponents()) {

            if (component instanceof JButton button
                    && text.equals(button.getText())) {

                return button;
            }

            if (component instanceof Container child) {
                final JButton result =
                        findButtonOrNull(
                                child,
                                text
                        );

                if (result != null) {
                    return result;
                }
            }
        }

        throw new AssertionError(
                "Button not found: " + text
        );
    }

    private static JButton findButtonOrNull(
            Container container,
            String text) {

        for (Component component :
                container.getComponents()) {

            if (component instanceof JButton button
                    && text.equals(button.getText())) {

                return button;
            }

            if (component instanceof Container child) {
                final JButton nested =
                        findButtonOrNull(
                                child,
                                text
                        );

                if (nested != null) {
                    return nested;
                }
            }
        }

        return null;
    }

    private static boolean containsLabelText(
            Container container,
            String text) {

        for (Component component :
                container.getComponents()) {

            if (component instanceof JLabel label
                    && text.equals(label.getText())) {

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
