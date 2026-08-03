package enery.use_case.forgot_password;

import enery.entity.user.User;

/**
 * The Forgot Password Interactor.
 */
public class ForgotPasswordInteractor
        implements ForgotPasswordInputBoundary {

    private final ForgotPasswordUserDataAccessInterface
            userDataAccessObject;

    private final ForgotPasswordOutputBoundary
            forgotPasswordPresenter;

    public ForgotPasswordInteractor(
            ForgotPasswordUserDataAccessInterface
                    userDataAccessObject,
            ForgotPasswordOutputBoundary
                    forgotPasswordPresenter) {

        this.userDataAccessObject =
                userDataAccessObject;

        this.forgotPasswordPresenter =
                forgotPasswordPresenter;
    }

    @Override
    public void execute(
            ForgotPasswordInputData inputData) {

        final String username =
                inputData.getUsername();

        final String newPassword =
                inputData.getNewPassword();

        final String confirmPassword =
                inputData.getConfirmPassword();

        if (username == null
                || username.isBlank()) {

            forgotPasswordPresenter.prepareFailView(
                    "Username cannot be empty.");
            return;
        }

        if (!userDataAccessObject.existsByName(username)) {
            forgotPasswordPresenter.prepareFailView(
                    "User does not exist.");
            return;
        }

        if (newPassword == null
                || newPassword.isBlank()) {

            forgotPasswordPresenter.prepareFailView(
                    "New password cannot be empty.");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            forgotPasswordPresenter.prepareFailView(
                    "Passwords do not match.");
            return;
        }

        final User user =
                userDataAccessObject.get(username);

        user.setPassword(newPassword);

        userDataAccessObject.changePassword(user);

        final ForgotPasswordOutputData outputData =
                new ForgotPasswordOutputData(
                        username);

        forgotPasswordPresenter.prepareSuccessView(
                outputData);
    }

    @Override
    public void switchToLoginView() {
        forgotPasswordPresenter.switchToLoginView();
    }
}