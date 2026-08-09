package enery.use_case.login;

import enery.entity.user.User;

/**
 * The Login Interactor.
 */
public class LoginInteractor implements LoginInputBoundary {

    private final LoginUserDataAccessInterface userDataAccessObject;
    private final LoginOutputBoundary loginPresenter;

    public LoginInteractor(
            LoginUserDataAccessInterface userDataAccessInterface,
            LoginOutputBoundary loginOutputBoundary) {

        this.userDataAccessObject = userDataAccessInterface;
        this.loginPresenter = loginOutputBoundary;
    }

    @Override
    public void execute(LoginInputData loginInputData) {

        final String username =
                loginInputData.getUsername();

        final String password =
                loginInputData.getPassword();

        if (!userDataAccessObject.existsByName(username)) {

            loginPresenter.prepareFailView(
                    username + ": Account does not exist."
            );

            return;
        }

        final User user =
                userDataAccessObject.get(username);

        if (!password.equals(user.getPassword())) {

            loginPresenter.prepareFailView(
                    "Incorrect password for \""
                            + username
                            + "\"."
            );

            return;
        }

        userDataAccessObject.setCurrentUsername(username);

        final boolean profileComplete =
                user.getUserProfile() != null;

        final LoginOutputData loginOutputData =
                new LoginOutputData(
                        user.getName(),
                        false,
                        profileComplete
                );

        loginPresenter.prepareSuccessView(
                loginOutputData
        );
    }

    @Override
    public void switchToSignupView() {
        loginPresenter.switchToSignupView();
    }
}
