package enery.view;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import enery.interface_adapter.ViewManagerModel;
import enery.interface_adapter.change_password.ChangePasswordController;
import enery.interface_adapter.change_password.LoggedInState;
import enery.interface_adapter.change_password.LoggedInViewModel;
import enery.interface_adapter.logout.LogoutController;
import enery.interface_adapter.log_meal.LogMealViewModel;

/**
 * The main screen displayed after a user successfully logs in.
 */
public final class HomeView extends JPanel
        implements PropertyChangeListener {

    public static final String VIEW_NAME = "home";

    private final LoggedInViewModel loggedInViewModel;
    private final ViewManagerModel viewManagerModel;

    private final JLabel usernameLabel = new JLabel();

    private LogoutController logoutController;
    private ChangePasswordController changePasswordController;

    public HomeView(
            LoggedInViewModel loggedInViewModel,
            ViewManagerModel viewManagerModel) {

        this.loggedInViewModel = loggedInViewModel;
        this.viewManagerModel = viewManagerModel;

        loggedInViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("ENERY Smart Nutritionist");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JButton profileButton = new JButton("Profile");
        final JButton loggingButton = new JButton("Log Food and Exercise");
        final JButton trackingButton = new JButton("View Progress");
        final JButton logoutButton = new JButton("Log Out");

        /*
         * These buttons are temporarily disabled until their corresponding
         * views and view models are implemented.
         */
        profileButton.setEnabled(false);
        loggingButton.setEnabled(true);
        trackingButton.setEnabled(false);

        loggingButton.addActionListener(event -> {
            viewManagerModel.setState(LogMealViewModel.VIEW_NAME);
            viewManagerModel.firePropertyChanged();
        });

        logoutButton.addActionListener(event -> {
            final LoggedInState currentState = loggedInViewModel.getState();

            if (logoutController != null) {
                logoutController.execute(currentState.getUsername());
            }
        });

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(title);
        add(usernameLabel);
        add(profileButton);
        add(loggingButton);
        add(trackingButton);
        add(logoutButton);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if ("state".equals(event.getPropertyName())) {
            final LoggedInState state =
                    (LoggedInState) event.getNewValue();

            usernameLabel.setText(
                    "Welcome, " + state.getUsername());
        }
    }

    public String getViewName() {
        return VIEW_NAME;
    }

    public void setLogoutController(
            LogoutController logoutController) {
        this.logoutController = logoutController;
    }

    public void setChangePasswordController(
            ChangePasswordController changePasswordController) {
        this.changePasswordController = changePasswordController;
    }
}