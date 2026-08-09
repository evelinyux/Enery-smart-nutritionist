package enery.interface_adapter.profile;

import enery.entity.user.UserProfile;
import enery.entity.user.WeightGoalPlan;
import enery.interface_adapter.shared.ViewManagerModel;
import enery.interface_adapter.change_password.LoggedInState;
import enery.interface_adapter.change_password.LoggedInViewModel;
import enery.use_case.profile.ProfileOutputBoundary;
import enery.use_case.profile.ProfileOutputData;
import enery.view.home.HomeView;

/**
 * Presenter for the Profile Use Case.
 */
public class ProfilePresenter implements ProfileOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final ProfileViewModel profileViewModel;
    private final LoggedInViewModel loggedInViewModel;

    /**
     * Constructor used by existing tests.
     *
     * @param viewManagerModel the view manager model
     * @param profileViewModel the profile view model
     */
    public ProfilePresenter(
            ViewManagerModel viewManagerModel,
            ProfileViewModel profileViewModel) {

        this(
                viewManagerModel,
                profileViewModel,
                null
        );
    }

    /**
     * Constructor used by the application.
     *
     * @param viewManagerModel the view manager model
     * @param profileViewModel the profile view model
     * @param loggedInViewModel the logged-in view model
     */
    public ProfilePresenter(
            ViewManagerModel viewManagerModel,
            ProfileViewModel profileViewModel,
            LoggedInViewModel loggedInViewModel) {

        this.viewManagerModel = viewManagerModel;
        this.profileViewModel = profileViewModel;
        this.loggedInViewModel = loggedInViewModel;
    }

    @Override
    public void prepareSuccessView(ProfileOutputData outputData) {

        final ProfileState state =
                profileViewModel.getState();

        final UserProfile profile =
                outputData.getUserProfile();

        // Basic user/profile information.
        state.setUsername(outputData.getUsername());

        state.setAge(profile.getAge());

        state.setHeightInCentimetres(
                profile.getHeightInCentimetres()
        );

        state.setWeightInKilograms(
                profile.getWeightInKilograms()
        );

        state.setSex(profile.getSex());

        state.setActivityLevel(
                profile.getActivityLevel()
        );

        state.setHealthGoal(
                profile.getHealthGoal()
        );

        state.setDietaryRestrictions(
                profile.getDietaryRestrictions()
        );

        /*
         * Populate weight-plan information when
         * the user has an active plan.
         */
        final WeightGoalPlan plan =
                profile.getActivePlan();

        if (plan != null) {

            state.setTargetWeightInKilograms(
                    plan.getTargetWeightInKilograms()
            );

            state.setDurationDays(
                    plan.getDurationDays()
            );

            state.setStartDate(
                    plan.getStartDate().toString()
            );

            state.setEndDate(
                    plan.getEndDate().toString()
            );

            state.setDailyCalorieGoal(
                    plan.getCurrentDailyCalorieGoal()
            );
        }
        else {

            state.setTargetWeightInKilograms(0.0);

            state.setDurationDays(0);

            state.setStartDate("");

            state.setEndDate("");

            state.setDailyCalorieGoal(null);
        }

        // Clear any previous error.
        state.setProfileError(null);

        state.setSuccessMessage(
                "Profile and plan saved successfully."
        );

        profileViewModel.setState(state);
        profileViewModel.firePropertyChanged();

        /*
         * NEW BEHAVIOUR:
         *
         * Once the profile has successfully been
         * created/saved, mark the logged-in user as
         * having a completed profile.
         *
         * HomeView can use this value to enable the
         * other feature buttons.
         */
        if (loggedInViewModel != null) {

            final LoggedInState loggedInState =
                    loggedInViewModel.getState();

            loggedInState.setProfileComplete(true);

            loggedInViewModel.setState(
                    loggedInState
            );

            loggedInViewModel.firePropertyChanged();
        }
    }

    @Override
    public void prepareFailView(String error) {

        final ProfileState state =
                profileViewModel.getState();

        state.setProfileError(error);

        state.setSuccessMessage(null);

        profileViewModel.setState(state);
        profileViewModel.firePropertyChanged();
    }

    @Override
    public void switchToHomeView() {

        viewManagerModel.setState(
                HomeView.VIEW_NAME
        );

        viewManagerModel.firePropertyChanged();
    }
}
