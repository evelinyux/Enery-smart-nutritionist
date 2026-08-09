package enery.interface_adapter.login;

import enery.interface_adapter.shared.ViewManagerModel;
import enery.interface_adapter.change_password.LoggedInState;
import enery.interface_adapter.change_password.LoggedInViewModel;
import enery.interface_adapter.signup.SignupViewModel;
import enery.use_case.login.LoginOutputBoundary;
import enery.use_case.login.LoginOutputData;
import enery.view.home.HomeView;

/**
 * Presenter for the Login Use Case.
 */
public class LoginPresenter
        implements LoginOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final LoggedInViewModel loggedInViewModel;
    private final LoginViewModel loginViewModel;
    private final SignupViewModel signupViewModel;

    public LoginPresenter(
            ViewManagerModel viewManagerModel,
            LoggedInViewModel loggedInViewModel,
            LoginViewModel loginViewModel,
            SignupViewModel signupViewModel) {

        this.viewManagerModel =
                viewManagerModel;

        this.loggedInViewModel =
                loggedInViewModel;

        this.loginViewModel =
                loginViewModel;

        this.signupViewModel =
                signupViewModel;
    }

    @Override
    public void prepareSuccessView(
            LoginOutputData outputData) {

        final LoggedInState loggedInState =
                loggedInViewModel.getState();

        loggedInState.setUsername(
                outputData.getUsername()
        );

        loggedInState.setProfileComplete(
                outputData.isProfileComplete()
        );

        loggedInState.setPassword("");
        loggedInState.setPasswordError(null);

        loggedInViewModel.setState(
                loggedInState
        );

        loggedInViewModel.firePropertyChanged();

        final LoginState loginState =
                loginViewModel.getState();

        loginState.setLoginError(null);
        loginState.setPassword("");

        loginViewModel.setState(loginState);
        loginViewModel.firePropertyChanged();

        viewManagerModel.setState(
                HomeView.VIEW_NAME
        );

        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {

        final LoginState loginState =
                loginViewModel.getState();

        loginState.setLoginError(error);

        loginViewModel.setState(loginState);
        loginViewModel.firePropertyChanged();
    }

    @Override
    public void switchToSignupView() {

        viewManagerModel.setState(
                signupViewModel.getViewName()
        );

        viewManagerModel.firePropertyChanged();
    }
}
