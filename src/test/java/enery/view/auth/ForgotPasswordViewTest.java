package enery.view.auth;

import java.awt.Component;
import java.awt.Container;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import enery.interface_adapter.forgot_password.ForgotPasswordController;
import enery.interface_adapter.forgot_password.ForgotPasswordState;
import enery.interface_adapter.forgot_password.ForgotPasswordViewModel;
import enery.interface_adapter.shared.ViewManagerModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ForgotPasswordViewTest {

    private ForgotPasswordViewModel viewModel;
    private ViewManagerModel viewManagerModel;
    private ForgotPasswordState state;
    private ForgotPasswordView view;

    @BeforeEach
    void setUp() throws Exception {
        viewModel =
                mock(ForgotPasswordViewModel.class);

        viewManagerModel =
                mock(ViewManagerModel.class);

        state =
                new ForgotPasswordState();

        when(viewModel.getState())
                .thenReturn(state);

        SwingUtilities.invokeAndWait(
                () -> view =
                        new ForgotPasswordView(
                                viewModel,
                                viewManagerModel
                        )
        );
    }

    @Test
    void getViewNameReturnsExpectedName() {
        assertEquals(
                ForgotPasswordViewModel.VIEW_NAME,
                view.getViewName()
        );
    }

    @Test
    void resetWithoutControllerShowsMessage()
            throws Exception {

        SwingUtilities.invokeAndWait(
                () -> findButton(
                        view,
                        "Reset Password"
                ).doClick()
        );

        assertTrue(
                containsLabelText(
                        view,
                        "Forgot password controller is not configured."
                )
        );
    }

    @Test
    void resetCallsControllerWithFieldValues()
            throws Exception {

        final ForgotPasswordController controller =
                mock(ForgotPasswordController.class);

        view.setForgotPasswordController(
                controller
        );

        final JTextField username =
                findUsernameField(view);

        final List<JPasswordField> passwords =
                findComponents(
                        view,
                        JPasswordField.class
                );

        SwingUtilities.invokeAndWait(() -> {
            username.setText("  ethan  ");
            passwords.get(0)
                    .setText("newPass");

            passwords.get(1)
                    .setText("newPass");

            findButton(
                    view,
                    "Reset Password"
            ).doClick();
        });

        verify(controller)
                .execute(
                        "ethan",
                        "newPass",
                        "newPass"
                );
    }

    @Test
    void backButtonNavigatesToLoginAndClearsFields()
            throws Exception {

        final JTextField username =
                findUsernameField(view);

        final List<JPasswordField> passwords =
                findComponents(
                        view,
                        JPasswordField.class
                );

        SwingUtilities.invokeAndWait(() -> {
            username.setText("ethan");

            passwords.get(0)
                    .setText("abc");

            passwords.get(1)
                    .setText("abc");

            findButton(
                    view,
                    "Back to Login"
            ).doClick();
        });

        verify(viewManagerModel)
                .setState(
                        LoginView.VIEW_NAME
                );

        verify(viewManagerModel)
                .firePropertyChanged();

        assertEquals(
                "",
                username.getText()
        );

        assertEquals(
                "",
                new String(
                        passwords.get(0)
                                .getPassword()
                )
        );

        assertEquals(
                "",
                new String(
                        passwords.get(1)
                                .getPassword()
                )
        );
    }

    @Test
    void propertyChangeDisplaysError()
            throws Exception {

        state.setError(
                "User was not found."
        );

        SwingUtilities.invokeAndWait(
                () -> view.propertyChange(
                        new PropertyChangeEvent(
                                viewModel,
                                "state",
                                null,
                                state
                        )
                )
        );

        assertTrue(
                containsLabelText(
                        view,
                        "User was not found."
                )
        );
    }

    @Test
    void propertyChangeDisplaysSuccessAndClearsPasswords()
            throws Exception {

        state.setError(null);

        state.setSuccessMessage(
                "Password reset successfully."
        );

        final List<JPasswordField> passwords =
                findComponents(
                        view,
                        JPasswordField.class
                );

        SwingUtilities.invokeAndWait(() -> {
            passwords.get(0)
                    .setText("abc");

            passwords.get(1)
                    .setText("abc");

            view.propertyChange(
                    new PropertyChangeEvent(
                            viewModel,
                            "state",
                            null,
                            state
                    )
            );
        });

        assertTrue(
                containsLabelText(
                        view,
                        "Password reset successfully."
                )
        );

        assertEquals(
                "",
                new String(
                        passwords.get(0)
                                .getPassword()
                )
        );

        assertEquals(
                "",
                new String(
                        passwords.get(1)
                                .getPassword()
                )
        );
    }

    private static JTextField findUsernameField(
            Container container) {

        final List<JTextField> fields =
                findComponents(
                        container,
                        JTextField.class
                );

        for (JTextField field : fields) {
            if (!(field instanceof JPasswordField)) {
                return field;
            }
        }

        throw new AssertionError(
                "Username field not found."
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
