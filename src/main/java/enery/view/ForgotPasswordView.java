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

import enery.interface_adapter.ViewManagerModel;
import enery.interface_adapter.forgot_password.ForgotPasswordController;
import enery.interface_adapter.forgot_password.ForgotPasswordState;
import enery.interface_adapter.forgot_password.ForgotPasswordViewModel;

/**
 * View for the Forgot Password Use Case.
 */
public class ForgotPasswordView extends JPanel
        implements PropertyChangeListener {

    private final ForgotPasswordViewModel
            forgotPasswordViewModel;

    private final ViewManagerModel
            viewManagerModel;

    private ForgotPasswordController
            forgotPasswordController;

    private final JTextField usernameField =
            new JTextField(15);

    private final JPasswordField newPasswordField =
            new JPasswordField(15);

    private final JPasswordField confirmPasswordField =
            new JPasswordField(15);

    private final JLabel statusLabel =
            new JLabel();

    public ForgotPasswordView(
            ForgotPasswordViewModel forgotPasswordViewModel,
            ViewManagerModel viewManagerModel) {

        this.forgotPasswordViewModel =
                forgotPasswordViewModel;

        this.viewManagerModel =
                viewManagerModel;

        forgotPasswordViewModel
                .addPropertyChangeListener(this);

        configureView();
    }

    private void configureView() {

        setLayout(
                new BoxLayout(
                        this,
                        BoxLayout.Y_AXIS));

        final JLabel title =
                new JLabel("Forgot Password");

        title.setAlignmentX(
                Component.CENTER_ALIGNMENT);

        final LabelTextPanel usernamePanel =
                new LabelTextPanel(
                        new JLabel("Username"),
                        usernameField
                );

        final LabelTextPanel newPasswordPanel =
                new LabelTextPanel(
                        new JLabel("New Password"),
                        newPasswordField
                );

        final LabelTextPanel confirmPasswordPanel =
                new LabelTextPanel(
                        new JLabel("Confirm Password"),
                        confirmPasswordField
                );

        final JButton resetButton =
                new JButton("Reset Password");

        final JButton backButton =
                new JButton("Back to Login");

        resetButton.addActionListener(
                event -> resetPassword());

        backButton.addActionListener(
                event -> switchToLogin());

        add(title);
        add(usernamePanel);
        add(newPasswordPanel);
        add(confirmPasswordPanel);
        add(resetButton);
        add(backButton);
        add(statusLabel);
    }

    private void resetPassword() {

        if (forgotPasswordController == null) {
            statusLabel.setText(
                    "Forgot password controller "
                            + "is not configured.");
            return;
        }

        final String username =
                usernameField.getText().trim();

        final String newPassword =
                new String(
                        newPasswordField.getPassword());

        final String confirmPassword =
                new String(
                        confirmPasswordField.getPassword());

        forgotPasswordController.execute(
                username,
                newPassword,
                confirmPassword
        );
    }

    private void switchToLogin() {

        clearFields();

        statusLabel.setText("");

        viewManagerModel.setState(
                LoginView.VIEW_NAME);

        viewManagerModel.firePropertyChanged();
    }

    private void clearFields() {
        usernameField.setText("");
        newPasswordField.setText("");
        confirmPasswordField.setText("");
    }

    @Override
    public void propertyChange(
            PropertyChangeEvent event) {

        final ForgotPasswordState state =
                forgotPasswordViewModel.getState();

        if (state.getError() != null
                && !state.getError().isBlank()) {

            statusLabel.setText(
                    state.getError());

            return;
        }

        if (state.getSuccessMessage() != null
                && !state.getSuccessMessage().isBlank()) {

            statusLabel.setText(
                    state.getSuccessMessage());

            newPasswordField.setText("");
            confirmPasswordField.setText("");
        }
    }

    public String getViewName() {
        return ForgotPasswordViewModel.VIEW_NAME;
    }

    public void setForgotPasswordController(
            ForgotPasswordController controller) {

        this.forgotPasswordController =
                controller;
    }
}