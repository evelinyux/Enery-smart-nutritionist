package enery.interface_adapter.login;

import enery.interface_adapter.change_password.LoggedInState;
import enery.interface_adapter.change_password.LoggedInViewModel;
import enery.interface_adapter.shared.ViewManagerModel;
import enery.interface_adapter.signup.SignupViewModel;
import enery.use_case.login.LoginOutputData;
import enery.view.home.HomeView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class LoginPresenterTest {

    private ViewManagerModel viewManagerModel;
    private LoggedInViewModel loggedInViewModel;
    private LoginViewModel loginViewModel;
    private SignupViewModel signupViewModel;

    private LoggedInState loggedInState;
    private LoginState loginState;

    private LoginPresenter presenter;

    @BeforeEach
    void setUp() {
        viewManagerModel = mock(ViewManagerModel.class);
        loggedInViewModel = mock(LoggedInViewModel.class);
        loginViewModel = mock(LoginViewModel.class);
        signupViewModel = mock(SignupViewModel.class);

        loggedInState = mock(LoggedInState.class);
        loginState = mock(LoginState.class);

        when(loggedInViewModel.getState()).thenReturn(loggedInState);
        when(loginViewModel.getState()).thenReturn(loginState);

        presenter = new LoginPresenter(
                viewManagerModel,
                loggedInViewModel,
                loginViewModel,
                signupViewModel
        );
    }

    @Test
    void prepareSuccessViewTest() {
        final LoginOutputData outputData = mock(LoginOutputData.class);

        when(outputData.getUsername()).thenReturn("Nicole");
        when(outputData.isProfileComplete()).thenReturn(true);

        presenter.prepareSuccessView(outputData);

        // Logged-in state should be updated.
        verify(loggedInState).setUsername("Nicole");
        verify(loggedInState).setProfileComplete(true);
        verify(loggedInState).setPassword("");
        verify(loggedInState).setPasswordError(null);

        verify(loggedInViewModel).setState(loggedInState);
        verify(loggedInViewModel).firePropertyChanged();

        // Login state should be cleaned.
        verify(loginState).setLoginError(null);
        verify(loginState).setPassword("");

        verify(loginViewModel).setState(loginState);
        verify(loginViewModel).firePropertyChanged();

        // The application should switch to the home view.
        verify(viewManagerModel).setState(HomeView.VIEW_NAME);
        verify(viewManagerModel).firePropertyChanged();
    }

    @Test
    void prepareFailViewTest() {
        presenter.prepareFailView("Incorrect username or password");

        verify(loginState)
                .setLoginError("Incorrect username or password");

        verify(loginViewModel).setState(loginState);
        verify(loginViewModel).firePropertyChanged();
    }

    @Test
    void switchToSignupViewTest() {
        when(signupViewModel.getViewName()).thenReturn("sign up");

        presenter.switchToSignupView();

        verify(viewManagerModel).setState("sign up");
        verify(viewManagerModel).firePropertyChanged();
    }
}