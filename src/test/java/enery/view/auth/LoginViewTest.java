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

import enery.interface_adapter.forgot_password.ForgotPasswordViewModel;
import enery.interface_adapter.login.LoginController;
import enery.interface_adapter.login.LoginState;
import enery.interface_adapter.login.LoginViewModel;
import enery.interface_adapter.shared.ViewManagerModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LoginViewTest {

    private LoginViewModel loginViewModel;
    private ViewManagerModel viewManagerModel;
    private LoginState loginState;
    private LoginView view;

    @BeforeEach
    void setUp() throws Exception {
        loginViewModel =
                mock(LoginViewModel.class);

        viewManagerModel =
                mock(ViewManagerModel.class);

        loginState =
                new LoginState();

        when(loginViewModel.getState())
                .thenReturn(loginState);

        SwingUtilities.invokeAndWait(
                () -> view =
                        new LoginView(
                                loginViewModel,
                                viewManagerModel
                        )
        );
    }

    @Test
    void getViewNameReturnsLoginViewName() {
        assertEquals(
                LoginView.VIEW_NAME,
                view.getViewName()
        );
    }

    @Test
    void usernameTypingUpdatesLoginState()
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
                loginState.getUsername()
        );

        verify(loginViewModel)
                .setState(loginState);
    }

    @Test
    void replacingUsernameUpdatesLoginState()
            throws Exception {

        final JTextField usernameField =
                findUsernameField(view);

        SwingUtilities.invokeAndWait(() -> {
            usernameField.setText("first");
            usernameField.setText("second");
        });

        assertEquals(
                "second",
                loginState.getUsername()
        );
    }

    @Test
    void passwordTypingUpdatesLoginState()
            throws Exception {

        final JPasswordField passwordField =
                findComponents(
                        view,
                        JPasswordField.class
                ).get(0);

        SwingUtilities.invokeAndWait(
                () -> passwordField.setText(
                        "secret123"
                )
        );

        assertEquals(
                "secret123",
                loginState.getPassword()
        );

        verify(loginViewModel)
                .setState(loginState);
    }

    @Test
    void replacingPasswordUpdatesLoginState()
            throws Exception {

        final JPasswordField passwordField =
                findComponents(
                        view,
                        JPasswordField.class
                ).get(0);

        SwingUtilities.invokeAndWait(() -> {
            passwordField.setText("old");
            passwordField.setText("new");
        });

        assertEquals(
                "new",
                loginState.getPassword()
        );
    }

    @Test
    void loginButtonWithoutControllerDoesNothing()
            throws Exception {

        SwingUtilities.invokeAndWait(
                () -> findButton(
                        view,
                        "Log In"
                ).doClick()
        );

        // If there is no controller, the production
        // listener deliberately performs no action.
        assertEquals(
                "",
                loginState.getUsername()
        );
    }

    @Test
    void loginButtonCallsControllerWithCurrentState()
            throws Exception {

        final LoginController controller =
                mock(LoginController.class);

        view.setLoginController(controller);

        loginState.setUsername("ethan");
        loginState.setPassword("password123");

        SwingUtilities.invokeAndWait(
                () -> findButton(
                        view,
                        "Log In"
                ).doClick()
        );

        verify(controller).execute(
                "ethan",
                "password123"
        );
    }

    @Test
    void forgotPasswordButtonNavigatesToForgotPassword()
            throws Exception {

        SwingUtilities.invokeAndWait(
                () -> findButton(
                        view,
                        "Forgot Password"
                ).doClick()
        );

        verify(viewManagerModel)
                .setState(
                        ForgotPasswordViewModel.VIEW_NAME
                );

        verify(viewManagerModel)
                .firePropertyChanged();
    }

    @Test
    void cancelWithoutControllerDoesNothing()
            throws Exception {

        SwingUtilities.invokeAndWait(
                () -> findButton(
                        view,
                        "Back to Sign Up"
                ).doClick()
        );

        verify(
                viewManagerModel,
                never()
        ).firePropertyChanged();
    }

    @Test
    void cancelCallsSwitchToSignupView()
            throws Exception {

        final LoginController controller =
                mock(LoginController.class);

        view.setLoginController(controller);

        SwingUtilities.invokeAndWait(
                () -> findButton(
                        view,
                        "Back to Sign Up"
                ).doClick()
        );

        verify(controller)
                .switchToSignupView();
    }

    @Test
    void propertyChangeUpdatesUsernameAndPasswordFields()
            throws Exception {

        final LoginState updatedState =
                new LoginState();

        updatedState.setUsername(
                "new-user"
        );

        updatedState.setPassword(
                "new-password"
        );

        SwingUtilities.invokeAndWait(
                () -> view.propertyChange(
                        new PropertyChangeEvent(
                                loginViewModel,
                                "state",
                                loginState,
                                updatedState
                        )
                )
        );

        final JTextField usernameField =
                findUsernameField(view);

        final JPasswordField passwordField =
                findComponents(
                        view,
                        JPasswordField.class
                ).get(0);

        assertEquals(
                "new-user",
                usernameField.getText()
        );

        assertEquals(
                "new-password",
                new String(
                        passwordField.getPassword()
                )
        );
    }

    @Test
    void propertyChangeDisplaysLoginError()
            throws Exception {

        final LoginState updatedState =
                new LoginState();

        updatedState.setUsername(
                "ethan"
        );

        updatedState.setPassword(
                "bad-password"
        );

        updatedState.setLoginError(
                "Invalid username or password"
        );

        SwingUtilities.invokeAndWait(
                () -> view.propertyChange(
                        new PropertyChangeEvent(
                                loginViewModel,
                                "state",
                                null,
                                updatedState
                        )
                )
        );

        assertTrue(
                containsLabelText(
                        view,
                        "Invalid username or password"
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
