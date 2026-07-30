package enery.use_case.profile;

import enery.entity.User;
import enery.entity.UserProfile;

/**
 * The Profile Interactor.
 */
public class ProfileInteractor implements ProfileInputBoundary {

    private final ProfileUserDataAccessInterface userDataAccessObject;
    private final ProfileOutputBoundary profilePresenter;

    public ProfileInteractor(
            ProfileUserDataAccessInterface profileDataAccessInterface,
            ProfileOutputBoundary profileOutputBoundary) {

        this.userDataAccessObject =
                profileDataAccessInterface;
        this.profilePresenter =
                profileOutputBoundary;
    }

    @Override
    public void execute(ProfileInputData profileInputData) {
        if (profileInputData.getAge() <= 0) {
            profilePresenter.prepareFailView(
                    "Age must be greater than zero.");
        }
        else if (profileInputData.getHeightInCentimetres() <= 0) {
            profilePresenter.prepareFailView(
                    "Height must be greater than zero.");
        }
        else if (profileInputData.getWeightInKilograms() <= 0) {
            profilePresenter.prepareFailView(
                    "Weight must be greater than zero.");
        }
        else if (profileInputData.getSex() == null) {
            profilePresenter.prepareFailView(
                    "Please select a sex.");
        }
        else if (profileInputData.getActivityLevel() == null) {
            profilePresenter.prepareFailView(
                    "Please select an activity level.");
        }
        else if (profileInputData.getHealthGoal() == null) {
            profilePresenter.prepareFailView(
                    "Please select a health goal.");
        }
        else {
            saveProfile(profileInputData);
        }
    }

    private void saveProfile(
            ProfileInputData profileInputData) {

        final String username =
                userDataAccessObject.getCurrentUsername();

        if (username == null) {
            profilePresenter.prepareFailView(
                    "No user is currently logged in.");
        }
        else {
            final User user =
                    userDataAccessObject.get(username);

            if (user == null) {
                profilePresenter.prepareFailView(
                        "The current user could not be found.");
            }
            else {
                final UserProfile userProfile =
                        new UserProfile(
                                profileInputData.getAge(),
                                profileInputData
                                        .getHeightInCentimetres(),
                                profileInputData
                                        .getWeightInKilograms(),
                                profileInputData.getSex(),
                                profileInputData
                                        .getActivityLevel(),
                                profileInputData
                                        .getHealthGoal(),
                                profileInputData
                                        .getDietaryRestrictions()
                        );

                user.setUserProfile(userProfile);
                userDataAccessObject.save(user);

                final ProfileOutputData outputData =
                        new ProfileOutputData(
                                username,
                                userProfile,
                                false
                        );

                profilePresenter.prepareSuccessView(
                        outputData);
            }
        }
    }

    @Override
    public void switchToHomeView() {
        profilePresenter.switchToHomeView();
    }
}