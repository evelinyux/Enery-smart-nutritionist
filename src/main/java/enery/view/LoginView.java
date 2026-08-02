package enery.view;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import enery.interface_adapter.ViewManagerModel;
import enery.interface_adapter.forgot_password.ForgotPasswordViewModel;
import enery.interface_adapter.login.LoginController;
import enery.interface_adapter.login.LoginState;
import enery.interface_adapter.login.LoginViewModel;

/**
 * The View for when the user is logging into the program.
 */
public class LoginView extends JPanel
        implements PropertyChangeListener {

    public static final String VIEW_NAME = "log in";

    private final LoginViewModel loginViewModel;
    private final ViewManagerModel viewManagerModel;

    private final JTextField usernameInputField =
            new JTextField(15);

    private final JLabel usernameErrorField =
            new JLabel();

    private final JPasswordField passwordInputField =
            new JPasswordField(15);

    private final JButton logIn;
    private final JButton forgotPassword;
    private final JButton cancel;

    private LoginController loginController;

    public LoginView(
            LoginViewModel loginViewModel,
            ViewManagerModel viewManagerModel) {

        this.loginViewModel = loginViewModel;
        this.viewManagerModel = viewManagerModel;

        this.loginViewModel.addPropertyChangeListener(this);

        final JLabel title =
                new JLabel("Login Screen");

        title.setAlignmentX(
                Component.CENTER_ALIGNMENT);

        final LabelTextPanel usernameInfo =
                new LabelTextPanel(
                        new JLabel("Username"),
                        usernameInputField
                );

        final LabelTextPanel passwordInfo =
                new LabelTextPanel(
                        new JLabel("Password"),
                        passwordInputField
                );

        final JPanel buttons =
                new JPanel();

        logIn = new JButton("Log In");
        buttons.add(logIn);

        forgotPassword =
                new JButton("Forgot Password");

        buttons.add(forgotPassword);

        cancel =
                new JButton("Back to Sign Up");

        buttons.add(cancel);

        logIn.addActionListener(event -> {
            if (loginController != null) {
                final LoginState currentState =
                        loginViewModel.getState();

                loginController.execute(
                        currentState.getUsername(),
                        currentState.getPassword()
                );
            }
        });

        forgotPassword.addActionListener(event -> {
            viewManagerModel.setState(
                    ForgotPasswordViewModel.VIEW_NAME
            );

            viewManagerModel.firePropertyChanged();
        });

        cancel.addActionListener(event -> {
            if (loginController != null) {
                loginController.switchToSignupView();
            }
        });

        usernameInputField
                .getDocument()
                .addDocumentListener(
                        new DocumentListener() {

                            private void documentListenerHelper() {
                                final LoginState currentState =
                                        loginViewModel.getState();

                                currentState.setUsername(
                                        usernameInputField.getText()
                                );

                                loginViewModel.setState(
                                        currentState
                                );
                            }

                            @Override
                            public void insertUpdate(
                                    DocumentEvent event) {

                                documentListenerHelper();
                            }

                            @Override
                            public void removeUpdate(
                                    DocumentEvent event) {

                                documentListenerHelper();
                            }

                            @Override
                            public void changedUpdate(
                                    DocumentEvent event) {

                                documentListenerHelper();
                            }
                        }
                );

        passwordInputField
                .getDocument()
                .addDocumentListener(
                        new DocumentListener() {

                            private void documentListenerHelper() {
                                final LoginState currentState =
                                        loginViewModel.getState();

                                currentState.setPassword(
                                        new String(
                                                passwordInputField
                                                        .getPassword()
                                        )
                                );

                                loginViewModel.setState(
                                        currentState
                                );
                            }

                            @Override
                            public void insertUpdate(
                                    DocumentEvent event) {

                                documentListenerHelper();
                            }

                            @Override
                            public void removeUpdate(
                                    DocumentEvent event) {

                                documentListenerHelper();
                            }

                            @Override
                            public void changedUpdate(
                                    DocumentEvent event) {

                                documentListenerHelper();
                            }
                        }
                );

        setLayout(
                new BoxLayout(
                        this,
                        BoxLayout.Y_AXIS
                )
        );

        add(title);
        add(usernameInfo);
        add(usernameErrorField);
        add(passwordInfo);
        add(buttons);
    }

    @Override
    public void propertyChange(
            PropertyChangeEvent event) {

        final LoginState state =
                (LoginState) event.getNewValue();

        setFields(state);

        usernameErrorField.setText(
                state.getLoginError()
        );
    }

    private void setFields(LoginState state) {
        usernameInputField.setText(
                state.getUsername()
        );

        passwordInputField.setText(
                state.getPassword()
        );
    }

    public String getViewName() {
        return VIEW_NAME;
    }

    public void setLoginController(
            LoginController loginController) {

        this.loginController =
                loginController;
    }
}