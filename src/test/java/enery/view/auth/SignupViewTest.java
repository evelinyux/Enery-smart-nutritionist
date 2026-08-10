package enery.view.auth;

import java.awt.Component;
import java.awt.Container;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import enery.interface_adapter.signup.SignupController;
import enery.interface_adapter.signup.SignupState;
import enery.interface_adapter.signup.SignupViewModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SignupViewTest {

    private SignupViewModel signupViewModel;
    private SignupState signupState;
    private SignupView view;

    @BeforeEach
    void setUp() throws Exception {
        signupViewModel =
                mock(SignupViewModel.class);

        signupState =
                new SignupState();

        when(signupViewModel.getState())
                .thenReturn(signupState);

        SwingUtilities.invokeAndWait(
                () -> view =
                        new SignupView(
                                signupViewModel
                        )
        );
    }

    @Test
    void getViewNameReturnsSignupViewName() {
        assertEquals(
                "sign up",
                view.getViewName()
        );
    }

    @Test
    void usernameTypingUpdatesState()
            throws Exception {

        final JTextField usernameField =
                findUsernameField(view);

        SwingUtilities.invokeAndWait(
                () -> usernameField.setText(
                        "ethan"
                )
        );

        assertEquals(
                "ethan",
                signupState.getUsername()
        );

        verify(signupViewModel)
                .setState(signupState);
    }

    @Test
    void replacingUsernameUpdatesState()
            throws Exception {

        final JTextField usernameField =
                findUsernameField(view);

        SwingUtilities.invokeAndWait(() -> {
            usernameField.setText("first");
            usernameField.setText("second");
        });

        assertEquals(
                "second",
                signupState.getUsername()
        );
    }

    @Test
    void passwordTypingUpdatesState()
            throws Exception {

        final List<JPasswordField> fields =
                findComponents(
                        view,
                        JPasswordField.class
                );

        SwingUtilities.invokeAndWait(
                () -> fields.get(0)
                        .setText("password123")
        );

        assertEquals(
                "password123",
                signupState.getPassword()
        );

        verify(signupViewModel)
                .setState(signupState);
    }

    @Test
    void repeatPasswordTypingUpdatesState()
            throws Exception {

        final List<JPasswordField> fields =
                findComponents(
                        view,
                        JPasswordField.class
                );

        SwingUtilities.invokeAndWait(
                () -> fields.get(1)
                        .setText("password123")
        );

        assertEquals(
                "password123",
                signupState.getRepeatPassword()
        );

        verify(signupViewModel)
                .setState(signupState);
    }

    @Test
    void replacingPasswordsUpdatesState()
            throws Exception {

        final List<JPasswordField> fields =
                findComponents(
                        view,
                        JPasswordField.class
                );

        SwingUtilities.invokeAndWait(() -> {
            fields.get(0).setText("first");
            fields.get(0).setText("second");

            fields.get(1).setText("first");
            fields.get(1).setText("second");
        });

        assertEquals(
                "second",
                signupState.getPassword()
        );

        assertEquals(
                "second",
                signupState.getRepeatPassword()
        );
    }

    @Test
    void signUpButtonCallsController()
            throws Exception {

        final SignupController controller =
                mock(SignupController.class);

        view.setSignupController(
                controller
        );

        signupState.setUsername(
                "ethan"
        );

        signupState.setPassword(
                "password123"
        );

        signupState.setRepeatPassword(
                "password123"
        );

        SwingUtilities.invokeAndWait(
                () -> findButton(
                        view,
                        SignupViewModel.SIGNUP_BUTTON_LABEL
                ).doClick()
        );

        verify(controller).execute(
                "ethan",
                "password123",
                "password123"
        );
    }

    @Test
    void toLoginButtonCallsController()
            throws Exception {

        final SignupController controller =
                mock(SignupController.class);

        view.setSignupController(
                controller
        );

        SwingUtilities.invokeAndWait(
                () -> findButton(
                        view,
                        SignupViewModel.TO_LOGIN_BUTTON_LABEL
                ).doClick()
        );

        verify(controller)
                .switchToLoginView();
    }

    @Test
    void propertyChangeWithNoErrorDoesNotThrow()
            throws Exception {

        final SignupState updatedState =
                new SignupState();

        updatedState.setUsername(
                "ethan"
        );

        SwingUtilities.invokeAndWait(
                () -> assertDoesNotThrow(
                        () -> view.propertyChange(
                                new PropertyChangeEvent(
                                        signupViewModel,
                                        "state",
                                        signupState,
                                        updatedState
                                )
                        )
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

        final JButton result =
                findButtonOrNull(
                        container,
                        text
                );

        if (result == null) {
            throw new AssertionError(
                    "Button not found: " + text
            );
        }

        return result;
    }

    private static JButton findButtonOrNull(
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
