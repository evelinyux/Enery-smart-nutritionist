package enery.view.home;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import enery.interface_adapter.shared.ViewManagerModel;
import enery.interface_adapter.change_password.ChangePasswordController;
import enery.interface_adapter.change_password.LoggedInState;
import enery.interface_adapter.change_password.LoggedInViewModel;
import enery.interface_adapter.health_assessment.HealthAssessmentViewModel;
import enery.interface_adapter.log_exercise.LogExerciseViewModel;
import enery.interface_adapter.log_meal.LogMealViewModel;
import enery.interface_adapter.logout.LogoutController;
import enery.interface_adapter.profile.ProfileViewModel;
import enery.interface_adapter.progress.ProgressViewModel;
import enery.interface_adapter.tracking.TrackingViewModel;
import enery.view.auth.ChangePasswordView;

/**
 * Main navigation screen displayed after login.
 */
public final class HomeView extends JPanel
        implements PropertyChangeListener {

    public static final String VIEW_NAME = "home";

    private static final int SMALL_GAP = 5;
    private static final int MEDIUM_GAP = 10;

    private final LoggedInViewModel
            loggedInViewModel;

    private final ViewManagerModel
            viewManagerModel;

    private final JLabel usernameLabel =
            new JLabel();

    private final JLabel profileNoticeLabel =
            new JLabel();

    private final JButton profileButton =
            new JButton("Profile");

    private final JButton mealButton =
            new JButton("Log Meal");

    private final JButton exerciseButton =
            new JButton("Log Exercise");

    private final JButton progressButton =
            new JButton(
                    "View Daily Goal Progress"
            );

    private final JButton trackingButton =
            new JButton(
                    "Nutrition and Exercise Tracking"
            );

    private final JButton healthAssessmentButton =
            new JButton("Health Assessment");

    private final JButton changePasswordButton =
            new JButton("Change Password");

    private final JButton logoutButton =
            new JButton("Log Out");

    private LogoutController logoutController;

    private ChangePasswordController
            changePasswordController;

    public HomeView(
            LoggedInViewModel loggedInViewModel,
            ViewManagerModel viewManagerModel) {

        this.loggedInViewModel =
                loggedInViewModel;

        this.viewManagerModel =
                viewManagerModel;

        loggedInViewModel
                .addPropertyChangeListener(this);

        configureComponents();
        configureListeners();
        configureLayout();

        /*
         * Default to locked until login tells us whether
         * the user's profile exists.
         */
        updateFeatureAvailability(
                loggedInViewModel
                        .getState()
                        .isProfileComplete()
        );
    }

    private void configureComponents() {

        usernameLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        profileNoticeLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        profileButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        mealButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        exerciseButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        progressButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        trackingButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        healthAssessmentButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        changePasswordButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        logoutButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );
    }

    private void configureListeners() {

        profileButton.addActionListener(
                event ->
                        switchView(
                                ProfileViewModel.VIEW_NAME
                        )
        );

        mealButton.addActionListener(
                event ->
                        switchView(
                                LogMealViewModel.VIEW_NAME
                        )
        );

        exerciseButton.addActionListener(
                event ->
                        switchView(
                                LogExerciseViewModel.VIEW_NAME
                        )
        );

        progressButton.addActionListener(
                event ->
                        switchView(
                                ProgressViewModel.VIEW_NAME
                        )
        );

        trackingButton.addActionListener(
                event ->
                        switchView(
                                TrackingViewModel.VIEW_NAME
                        )
        );

        healthAssessmentButton.addActionListener(
                event ->
                        switchView(
                                HealthAssessmentViewModel
                                        .VIEW_NAME
                        )
        );

        changePasswordButton.addActionListener(
                event ->
                        switchView(
                                ChangePasswordView.VIEW_NAME
                        )
        );

        logoutButton.addActionListener(
                event -> {

                    final LoggedInState state =
                            loggedInViewModel
                                    .getState();

                    if (logoutController != null) {
                        logoutController.execute(
                                state.getUsername()
                        );
                    }
                }
        );
    }

    private void configureLayout() {

        setLayout(
                new BoxLayout(
                        this,
                        BoxLayout.Y_AXIS
                )
        );

        final JLabel title =
                new JLabel(
                        "ENERY Smart Nutritionist"
                );

        title.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        add(Box.createVerticalStrut(
                MEDIUM_GAP
        ));

        add(title);

        add(Box.createVerticalStrut(
                SMALL_GAP
        ));

        add(usernameLabel);

        add(Box.createVerticalStrut(
                SMALL_GAP
        ));

        add(profileNoticeLabel);

        add(Box.createVerticalStrut(
                MEDIUM_GAP
        ));

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

        add(changePasswordButton);
        add(Box.createVerticalStrut(SMALL_GAP));

        add(logoutButton);
    }

    /**
     * Enables or disables features that depend on
     * profile information.
     *
     * @param profileComplete whether profile exists
     */
    private void updateFeatureAvailability(
            boolean profileComplete) {

        mealButton.setEnabled(
                profileComplete
        );

        exerciseButton.setEnabled(
                profileComplete
        );

        progressButton.setEnabled(
                profileComplete
        );

        trackingButton.setEnabled(
                profileComplete
        );

        healthAssessmentButton.setEnabled(
                profileComplete
        );

        /*
         * These features must remain available.
         */
        profileButton.setEnabled(true);
        changePasswordButton.setEnabled(true);
        logoutButton.setEnabled(true);

        if (profileComplete) {

            profileNoticeLabel.setText("");

            mealButton.setToolTipText(null);
            exerciseButton.setToolTipText(null);
            progressButton.setToolTipText(null);
            trackingButton.setToolTipText(null);
            healthAssessmentButton
                    .setToolTipText(null);
        }
        else {

            profileNoticeLabel.setText(
                    "Please complete your profile "
                            + "to unlock the other features."
            );

            final String tooltip =
                    "Complete your profile first.";

            mealButton.setToolTipText(
                    tooltip
            );

            exerciseButton.setToolTipText(
                    tooltip
            );

            progressButton.setToolTipText(
                    tooltip
            );

            trackingButton.setToolTipText(
                    tooltip
            );

            healthAssessmentButton
                    .setToolTipText(
                            tooltip
                    );
        }
    }

    private void switchView(
            String viewName) {

        viewManagerModel.setState(
                viewName
        );

        viewManagerModel
                .firePropertyChanged();
    }

    @Override
    public void propertyChange(
            PropertyChangeEvent event) {

        if ("state".equals(
                event.getPropertyName())) {

            final LoggedInState state =
                    loggedInViewModel
                            .getState();

            usernameLabel.setText(
                    "Welcome, "
                            + state.getUsername()
            );

            updateFeatureAvailability(
                    state.isProfileComplete()
            );
        }
    }

    public String getViewName() {
        return VIEW_NAME;
    }

    public void setLogoutController(
            LogoutController controller) {

        this.logoutController =
                controller;
    }

    public void setChangePasswordController(
            ChangePasswordController controller) {

        this.changePasswordController =
                controller;
    }
}
