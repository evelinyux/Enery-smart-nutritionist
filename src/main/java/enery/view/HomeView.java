package enery.view;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import enery.interface_adapter.ViewManagerModel;
import enery.interface_adapter.change_password.ChangePasswordController;
import enery.interface_adapter.change_password.LoggedInState;
import enery.interface_adapter.change_password.LoggedInViewModel;
import enery.interface_adapter.log_exercise.LogExerciseViewModel;
import enery.interface_adapter.log_meal.LogMealViewModel;
import enery.interface_adapter.logout.LogoutController;
import enery.interface_adapter.profile.ProfileViewModel;
import enery.interface_adapter.progress.ProgressViewModel;
import enery.interface_adapter.tracking.TrackingViewModel;
import enery.interface_adapter.health_assessment.HealthAssessmentViewModel;

/**
 * Main navigation screen displayed after a successful login.
 */
public final class HomeView extends JPanel
        implements PropertyChangeListener {

    public static final String VIEW_NAME = "home";

    private static final int SMALL_GAP = 5;
    private static final int MEDIUM_GAP = 10;

    private final LoggedInViewModel loggedInViewModel;
    private final ViewManagerModel viewManagerModel;

    private final JLabel usernameLabel = new JLabel();

    private final JButton profileButton =
            new JButton("Profile");

    private final JButton mealButton =
            new JButton("Log Meal");

    private final JButton exerciseButton =
            new JButton("Log Exercise");

    private final JButton progressButton =
            new JButton("View Daily Goal Progress");

    private final JButton trackingButton =
            new JButton("Nutrition and Exercise Tracking");

    private final JButton changePasswordButton =
            new JButton("Change Password");

    private final JButton logoutButton =
            new JButton("Log Out");

    private final JButton healthAssessmentButton =
            new JButton("Health Assessment");

    private LogoutController logoutController;

    /*
     * The current HomeView does not display a change-password form,
     * but AppBuilder still injects this controller.
     */
    private ChangePasswordController changePasswordController;

    /**
     * Creates the home screen and connects its navigation buttons.
     *
     * @param loggedInViewModel view model containing the active username
     * @param viewManagerModel model used to switch between screens
     */
    public HomeView(
            LoggedInViewModel loggedInViewModel,
            ViewManagerModel viewManagerModel) {

        this.loggedInViewModel = loggedInViewModel;
        this.viewManagerModel = viewManagerModel;

        loggedInViewModel.addPropertyChangeListener(this);

        configureComponents();
        configureListeners();
        configureLayout();
    }

    /**
     * Configures component alignment.
     */
    private void configureView() {
        setLayout(
                new BoxLayout(this, BoxLayout.Y_AXIS));

        final JLabel title =
                new JLabel("ENERY Smart Nutritionist");

        title.setAlignmentX(
                Component.CENTER_ALIGNMENT);

        final JSeparator accountSeparator =
                new JSeparator();

        accountSeparator.setMaximumSize(
                accountSeparator.getPreferredSize());

        add(title);
        add(usernameLabel);

        /*
         * Main application features.
         */
        add(profileButton);
        add(mealButton);
        add(exerciseButton);
        add(trackingButton);
        add(healthAssessmentButton);

        /*
         * Account actions.
         */
        add(Box.createVerticalStrut(20));
        add(logoutButton);

        add(Box.createVerticalStrut(15));
        add(accountSeparator);
        add(Box.createVerticalStrut(15));

        add(changePasswordButton);

    }
    private void configureComponents() {
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        profileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mealButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exerciseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        progressButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        trackingButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        healthAssessmentButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    /**
     * Connects buttons to their actions.
     */
    private void configureListeners() {
        profileButton.addActionListener(event ->
                switchView(ProfileViewModel.VIEW_NAME));

        mealButton.addActionListener(event ->
                switchView(LogMealViewModel.VIEW_NAME));

        exerciseButton.addActionListener(event ->
                switchView(LogExerciseViewModel.VIEW_NAME));

        progressButton.addActionListener(event ->
                switchView(ProgressViewModel.VIEW_NAME));

        healthAssessmentButton.addActionListener(event ->
                switchView(
                        HealthAssessmentViewModel.VIEW_NAME));

        changePasswordButton.addActionListener(event ->
                switchView(
                        ChangePasswordView.VIEW_NAME));
        trackingButton.addActionListener(event ->
                switchView(TrackingViewModel.VIEW_NAME));

        logoutButton.addActionListener(event -> {
            final LoggedInState state =
                    loggedInViewModel.getState();


            if (logoutController != null) {
                logoutController.execute(state.getUsername());
            }
        });
    }

    /**
     * Creates the visible Home screen layout.
     */
    private void configureLayout() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        final JLabel title =
                new JLabel("ENERY Smart Nutritionist");

        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalStrut(MEDIUM_GAP));
        add(title);
        add(Box.createVerticalStrut(SMALL_GAP));
        add(usernameLabel);
        add(Box.createVerticalStrut(MEDIUM_GAP));

        add(profileButton);
        add(Box.createVerticalStrut(SMALL_GAP));

        add(mealButton);
        add(Box.createVerticalStrut(SMALL_GAP));

        add(exerciseButton);
        add(Box.createVerticalStrut(SMALL_GAP));

        add(progressButton);
        add(Box.createVerticalStrut(SMALL_GAP));

        add(trackingButton);
        add(Box.createVerticalStrut(SMALL_GAP));

        add(healthAssessmentButton);
        add(Box.createVerticalStrut(SMALL_GAP));

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
     * Updates the welcome label when login state changes.
     *
     * @param event property-change event
     */
    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if ("state".equals(event.getPropertyName())) {
            final LoggedInState state =
                    loggedInViewModel.getState();

            usernameLabel.setText(
                    "Welcome, " + state.getUsername()
            );
        }
    }

    /**
     * Returns the CardLayout name of this view.
     *
     * @return Home view name
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
     * <p>The controller remains wired for compatibility with the current
     * AppBuilder, although this screen does not yet contain password fields.</p>
     *
     * @param controller change-password controller
     */
    public void setChangePasswordController(
            ChangePasswordController controller) {

        this.changePasswordController = controller;
    }
}
