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
import enery.interface_adapter.log_exercise.LogExerciseViewModel;
import enery.interface_adapter.log_meal.LogMealViewModel;
import enery.interface_adapter.logout.LogoutController;
import enery.interface_adapter.tracking.TrackingViewModel;

/**
 * Main navigation screen displayed after a successful login.
 */
public final class HomeView extends JPanel
        implements PropertyChangeListener {

    public static final String VIEW_NAME = "home";

    private final LoggedInViewModel loggedInViewModel;
    private final ViewManagerModel viewManagerModel;

    private final JLabel usernameLabel = new JLabel();
    private final JButton profileButton = new JButton("Profile");
    private final JButton mealButton = new JButton("Log Meal");
    private final JButton exerciseButton = new JButton("Log Exercise");
    private final JButton trackingButton = new JButton("View Progress");
    private final JButton logoutButton = new JButton("Log Out");

    private LogoutController logoutController;
    private ChangePasswordController changePasswordController;

    /**
     * Creates the home screen and connects its navigation buttons.
     *
     * @param loggedInViewModel view model containing the active username
     * @param viewManagerModel model used to switch between application screens
     */
    public HomeView(
            LoggedInViewModel loggedInViewModel,
            ViewManagerModel viewManagerModel) {

        this.loggedInViewModel = loggedInViewModel;
        this.viewManagerModel = viewManagerModel;

        loggedInViewModel.addPropertyChangeListener(this);

        final JLabel title =
                new JLabel("ENERY Smart Nutritionist");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // The profile feature has not been connected yet.
        profileButton.setEnabled(false);

        mealButton.addActionListener(event ->
                switchView(LogMealViewModel.VIEW_NAME));

        exerciseButton.addActionListener(event ->
                switchView(LogExerciseViewModel.VIEW_NAME));

        trackingButton.addActionListener(event ->
                switchView(TrackingViewModel.VIEW_NAME));

        logoutButton.addActionListener(event -> {
            final LoggedInState state =
                    loggedInViewModel.getState();

            if (logoutController != null) {
                logoutController.execute(state.getUsername());
            }
        });

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(title);
        add(usernameLabel);
        add(profileButton);
        add(mealButton);
        add(exerciseButton);
        add(trackingButton);
        add(logoutButton);
    }

    /**
     * Changes the currently displayed card.
     *
     * @param viewName name of the view to display
     */
    private void switchView(String viewName) {
        viewManagerModel.setState(viewName);
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Updates the welcome label when the logged-in state changes.
     *
     * @param event property-change event from the logged-in view model
     */
    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if ("state".equals(event.getPropertyName())) {
            final LoggedInState state =
                    (LoggedInState) event.getNewValue();

            usernameLabel.setText(
                    "Welcome, " + state.getUsername());
        }
    }

    /**
     * Returns the unique CardLayout name of this view.
     *
     * @return home view name
     */
    public String getViewName() {
        return VIEW_NAME;
    }

    /**
     * Sets the logout controller.
     *
     * @param controller logout controller
     */
    public void setLogoutController(
            LogoutController controller) {

        this.logoutController = controller;
    }

    /**
     * Sets the change-password controller.
     *
     * <p>The current home screen does not yet display a change-password
     * button, but this setter preserves the existing authentication use case
     * wiring.</p>
     *
     * @param controller change-password controller
     */
    public void setChangePasswordController(
            ChangePasswordController controller) {

        this.changePasswordController = controller;
    }
}
