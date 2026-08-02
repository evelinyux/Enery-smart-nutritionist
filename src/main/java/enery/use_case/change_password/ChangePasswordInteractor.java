package enery.use_case.change_password;

import enery.entity.User;

/**
 * The Change Password Interactor.
 */
public class ChangePasswordInteractor
        implements ChangePasswordInputBoundary {

    private final ChangePasswordUserDataAccessInterface
            userDataAccessObject;

    private final ChangePasswordOutputBoundary
            userPresenter;

    /**
     * Creates the Change Password Interactor.
     *
     * @param userDataAccessObject user data access object
     * @param userPresenter presenter
     */
    public ChangePasswordInteractor(
            ChangePasswordUserDataAccessInterface
                    userDataAccessObject,
            ChangePasswordOutputBoundary
                    userPresenter) {

        this.userDataAccessObject =
                userDataAccessObject;

        this.userPresenter =
                userPresenter;
    }

    @Override
    public void execute(
            ChangePasswordInputData inputData) {

        final User user =
                userDataAccessObject.get(
                        inputData.getUsername());

        if (user == null) {
            userPresenter.prepareFailView(
                    "User does not exist.");
            return;
        }

        /*
         * Update the existing User instead of creating
         * a completely new User.
         */
        user.setPassword(
                inputData.getPassword());

        userDataAccessObject.changePassword(
                user);

        final ChangePasswordOutputData outputData =
                new ChangePasswordOutputData(
                        user.getName(),
                        false
                );

        userPresenter.prepareSuccessView(
                outputData);
    }
}