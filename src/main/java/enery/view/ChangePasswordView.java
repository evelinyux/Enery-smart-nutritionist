package enery.view;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import enery.interface_adapter.ViewManagerModel;
import enery.interface_adapter.change_password.ChangePasswordController;
import enery.interface_adapter.change_password.LoggedInState;
import enery.interface_adapter.change_password.LoggedInViewModel;

/**
 * The view for changing the current user's password.
 */
public final class ChangePasswordView extends JPanel
        implements PropertyChangeListener {

    public static final String VIEW_NAME = "change password";

    private final LoggedInViewModel loggedInViewModel;
    private final ViewManagerModel viewManagerModel;

    private final JPasswordField newPasswordField =
            new JPasswordField(15);

    private final JPasswordField confirmPasswordField =
            new JPasswordField(15);

    private final JLabel statusLabel =
            new JLabel();

    private ChangePasswordController changePasswordController;

    /**
     * Creates the Change Password screen.
     *
     * @param loggedInViewModel view model for the logged-in user
     * @param viewManagerModel model used to switch screens
     */
    public ChangePasswordView(
            LoggedInViewModel loggedInViewModel,
            ViewManagerModel viewManagerModel) {

        this.loggedInViewModel = loggedInViewModel;
        this.viewManagerModel = viewManagerModel;

        loggedInViewModel.addPropertyChangeListener(this);

        configureView();
    }

    /**
     * Creates and arranges the Change Password screen.
     */
    private void configureView() {
        setLayout(
                new BoxLayout(this, BoxLayout.Y_AXIS));

        final JLabel title =
                new JLabel("Change Password");

        title.setAlignmentX(
                Component.CENTER_ALIGNMENT);

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

        final JButton changePasswordButton =
                new JButton("Change Password");

        final JButton backButton =
                new JButton("Back to Home");

        changePasswordButton.addActionListener(
                event -> changePassword());

        backButton.addActionListener(
                event -> switchToHomeView());

        add(title);
        add(newPasswordPanel);
        add(confirmPasswordPanel);
        add(changePasswordButton);
        add(backButton);
        add(statusLabel);
    }

    /**
     * Validates the password fields and executes the use case.
     */
    private void changePassword() {
        if (changePasswordController == null) {
            statusLabel.setText(
                    "Change-password controller "
                            + "is not configured.");
            return;
        }

        final String newPassword =
                new String(
                        newPasswordField.getPassword());

        final String confirmedPassword =
                new String(
                        confirmPasswordField.getPassword());

        if (newPassword.isBlank()) {
            statusLabel.setText(
                    "New password cannot be empty.");
            return;
        }

        if (!newPassword.equals(confirmedPassword)) {
            statusLabel.setText(
                    "The passwords do not match.");
            return;
        }

        final LoggedInState state =
                loggedInViewModel.getState();

        if (state.getUsername() == null
                || state.getUsername().isBlank()) {

            statusLabel.setText(
                    "No user is currently logged in.");
            return;
        }

        statusLabel.setText("");

        /*
         * The controller expects password first
         * and username second.
         */
        changePasswordController.execute(
                newPassword,
                state.getUsername()
        );
    }

    /**
     * Returns to the home screen.
     */
    private void switchToHomeView() {
        clearPasswordFields();

        viewManagerModel.setState(
                HomeView.VIEW_NAME);

        viewManagerModel.firePropertyChanged();
    }

    /**
     * Clears both password fields.
     */
    private void clearPasswordFields() {
        newPasswordField.setText("");
        confirmPasswordField.setText("");
    }

    /**
     * Displays the result of the Change Password Use Case.
     *
     * @param event property-change event
     */
    @Override
    public void propertyChange(
            PropertyChangeEvent event) {

        if ("password".equals(
                event.getPropertyName())) {

            clearPasswordFields();

            statusLabel.setText(
                    "Password changed successfully.");
        }
    }

    /**
     * Returns the CardLayout name of this view.
     *
     * @return Change Password view name
     */
    public String getViewName() {
        return VIEW_NAME;
    }

    /**
     * Sets the Change Password controller.
     *
     * @param controller Change Password controller
     */
    public void setChangePasswordController(
            ChangePasswordController controller) {

        this.changePasswordController =
                controller;
    }
}