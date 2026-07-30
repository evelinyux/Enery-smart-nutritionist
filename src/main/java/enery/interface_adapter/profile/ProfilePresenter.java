package enery.interface_adapter.profile;

import enery.entity.user.UserProfile;
import enery.entity.user.WeightGoalPlan;
import enery.interface_adapter.ViewManagerModel;
import enery.interface_adapter.change_password.LoggedInViewModel;
import enery.use_case.profile.ProfileOutputBoundary;
import enery.use_case.profile.ProfileOutputData;

/**
 * The Presenter for the Profile Use Case.
 */
public class ProfilePresenter
        implements ProfileOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final ProfileViewModel profileViewModel;

    public ProfilePresenter(
            ViewManagerModel viewManagerModel,
            ProfileViewModel profileViewModel) {

        this.viewManagerModel = viewManagerModel;
        this.profileViewModel = profileViewModel;
    }

    @Override
    public void prepareSuccessView(
            ProfileOutputData outputData) {

        final ProfileState profileState =
                profileViewModel.getState();

        final UserProfile userProfile =
                outputData.getUserProfile();

        profileState.setUsername(
                outputData.getUsername());

        profileState.setAge(
                userProfile.getAge());

        profileState.setHeightInCentimetres(
                userProfile.getHeightInCentimetres());

        profileState.setWeightInKilograms(
                userProfile.getWeightInKilograms());

        profileState.setSex(
                userProfile.getSex());

        profileState.setActivityLevel(
                userProfile.getActivityLevel());

        profileState.setHealthGoal(
                userProfile.getHealthGoal());

        profileState.setDietaryRestrictions(
                userProfile.getDietaryRestrictions());

        updatePlanState(
                profileState,
                userProfile.getActivePlan());

        profileState.setProfileError(null);

        profileState.setSuccessMessage(
                "Profile and plan saved successfully.");

        profileViewModel.setState(profileState);
        profileViewModel.firePropertyChanged();
    }

    /**
     * Copies the active plan information into the view state.
     *
     * @param profileState state used by the Profile View
     * @param activePlan user's active weight-goal plan
     */
    private void updatePlanState(
            ProfileState profileState,
            WeightGoalPlan activePlan) {

        if (activePlan == null) {
            profileState.setTargetWeightInKilograms(
                    0.0);

            profileState.setDurationDays(
                    0);

            profileState.setStartDate(
                    "");

            profileState.setEndDate(
                    "");

            profileState.setDailyCalorieGoal(
                    null);
        }
        else {
            profileState.setTargetWeightInKilograms(
                    activePlan
                            .getTargetWeightInKilograms());

            profileState.setDurationDays(
                    activePlan.getDurationDays());

            profileState.setStartDate(
                    activePlan
                            .getStartDate()
                            .toString());

            profileState.setEndDate(
                    activePlan
                            .getEndDate()
                            .toString());

            profileState.setDailyCalorieGoal(
                    activePlan
                            .getCurrentDailyCalorieGoal());
        }
    }

    @Override
    public void prepareFailView(
            String errorMessage) {

        final ProfileState profileState =
                profileViewModel.getState();

        profileState.setProfileError(
                errorMessage);

        profileState.setSuccessMessage(
                null);

        profileViewModel.setState(
                profileState);

        profileViewModel.firePropertyChanged();
    }

    @Override
    public void switchToHomeView() {
        viewManagerModel.setState(
                LoggedInViewModel.VIEW_NAME);

        viewManagerModel.firePropertyChanged();
    }
}