package enery.use_case.change_password;

import enery.entity.User;

/**
 * The Change Password Interactor.
 */
public class ChangePasswordInteractor implements ChangePasswordInputBoundary {
    private final ChangePasswordUserDataAccessInterface userDataAccessObject;
    private final ChangePasswordOutputBoundary userPresenter;

    public ChangePasswordInteractor(ChangePasswordUserDataAccessInterface changePasswordDataAccessInterface,
                                    ChangePasswordOutputBoundary changePasswordOutputBoundary) {
        this.userDataAccessObject = changePasswordDataAccessInterface;
        this.userPresenter = changePasswordOutputBoundary;
    }

    @Override
    public void execute(ChangePasswordInputData changePasswordInputData) {
        final User user =
                userDataAccessObject.get(
                        changePasswordInputData.getUsername());

        if (user == null) {
            userPresenter.prepareFailView(
                    "User does not exist.");
            return;
        }

        user.setPassword(
                changePasswordInputData.getPassword());

        userDataAccessObject.changePassword(user);

        final ChangePasswordOutputData outputData =
                new ChangePasswordOutputData(
                        user.getName(),
                        false);

        userPresenter.prepareSuccessView(outputData);
    }
}

