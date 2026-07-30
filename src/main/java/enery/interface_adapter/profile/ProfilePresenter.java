package enery.interface_adapter.profile;

import enery.entity.UserProfile;
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

        profileState.setProfileError(null);
        profileState.setSuccessMessage(
                "Profile saved successfully.");

        profileViewModel.setState(profileState);
        profileViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final ProfileState profileState =
                profileViewModel.getState();

        profileState.setProfileError(errorMessage);
        profileState.setSuccessMessage(null);

        profileViewModel.setState(profileState);
        profileViewModel.firePropertyChanged();
    }

    @Override
    public void switchToHomeView() {
        viewManagerModel.setState(
                LoggedInViewModel.VIEW_NAME);
        viewManagerModel.firePropertyChanged();
    }
}