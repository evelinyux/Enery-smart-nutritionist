package enery.interface_adapter.login;

import enery.interface_adapter.change_password.LoggedInState;
import enery.interface_adapter.change_password.LoggedInViewModel;
import enery.interface_adapter.shared.ViewManagerModel;
import enery.interface_adapter.signup.SignupViewModel;
import enery.use_case.login.LoginInputBoundary;
import enery.use_case.login.LoginInputData;
import enery.use_case.login.LoginOutputData;
import enery.view.home.HomeView;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginInterfaceAdapterTest {

    @Test
    void controllerExecutePassesInputDataToInteractor() {
        final LoginInputBoundary interactor =
                mock(LoginInputBoundary.class);

        final LoginController controller =
                new LoginController(interactor);

        controller.execute(
                "ethan",
                "password123"
        );

        verify(interactor)
                .execute(
                        any(LoginInputData.class)
                );
    }

    @Test
    void controllerSwitchToSignupDelegatesToInteractor() {
        final LoginInputBoundary interactor =
                mock(LoginInputBoundary.class);

        final LoginController controller =
                new LoginController(interactor);

        controller.switchToSignupView();

        verify(interactor).switchToSignupView();
    }

    @Test
    void loginStateStartsWithExpectedDefaults() {
        final LoginState state = new LoginState();

        assertEquals("", state.getUsername());
        assertEquals("", state.getPassword());
        assertNull(state.getLoginError());
    }

    @Test
    void loginStateStoresValues() {
        final LoginState state = new LoginState();

        state.setUsername("ethan");
        state.setPassword("secret");
        state.setLoginError("Invalid login");

        assertEquals("ethan", state.getUsername());
        assertEquals("secret", state.getPassword());
        assertEquals("Invalid login", state.getLoginError());
    }

    @Test
    void loginViewModelStartsWithLoginState() {
        final LoginViewModel viewModel =
                new LoginViewModel();

        assertNotNull(viewModel.getState());
        assertInstanceOf(
                LoginState.class,
                viewModel.getState()
        );

        assertEquals("log in", viewModel.getViewName());
    }

    @Test
    void presenterSuccessUpdatesLoggedInAndLoginStates() {
        final ViewManagerModel viewManagerModel =
                mock(ViewManagerModel.class);

        final LoggedInViewModel loggedInViewModel =
                mock(LoggedInViewModel.class);

        final LoginViewModel loginViewModel =
                mock(LoginViewModel.class);

        final SignupViewModel signupViewModel =
                mock(SignupViewModel.class);

        final LoggedInState loggedInState =
                new LoggedInState();

        loggedInState.setPassword("old-password");
        loggedInState.setPasswordError("old-error");

        final LoginState loginState =
                new LoginState();

        loginState.setPassword("entered-password");
        loginState.setLoginError("old-login-error");

        when(loggedInViewModel.getState())
                .thenReturn(loggedInState);

        when(loginViewModel.getState())
                .thenReturn(loginState);

        final LoginPresenter presenter =
                new LoginPresenter(
                        viewManagerModel,
                        loggedInViewModel,
                        loginViewModel,
                        signupViewModel
                );

        final LoginOutputData outputData =
                mock(LoginOutputData.class);

        when(outputData.getUsername())
                .thenReturn("ethan");

        when(outputData.isProfileComplete())
                .thenReturn(true);

        presenter.prepareSuccessView(outputData);

        assertEquals(
                "ethan",
                loggedInState.getUsername()
        );

        assertTrue(
                loggedInState.isProfileComplete()
        );

        assertEquals(
                "",
                loggedInState.getPassword()
        );

        assertNull(
                loggedInState.getPasswordError()
        );

        assertEquals(
                "",
                loginState.getPassword()
        );

        assertNull(
                loginState.getLoginError()
        );

        verify(loggedInViewModel)
                .setState(loggedInState);

        verify(loggedInViewModel)
                .firePropertyChanged();

        verify(loginViewModel)
                .setState(loginState);

        verify(loginViewModel)
                .firePropertyChanged();

        verify(viewManagerModel)
                .setState(HomeView.VIEW_NAME);

        verify(viewManagerModel)
                .firePropertyChanged();
    }

    @Test
    void presenterFailureSetsLoginError() {
        final ViewManagerModel viewManagerModel =
                mock(ViewManagerModel.class);

        final LoggedInViewModel loggedInViewModel =
                mock(LoggedInViewModel.class);

        final LoginViewModel loginViewModel =
                mock(LoginViewModel.class);

        final SignupViewModel signupViewModel =
                mock(SignupViewModel.class);

        final LoginState loginState =
                new LoginState();

        when(loginViewModel.getState())
                .thenReturn(loginState);

        final LoginPresenter presenter =
                new LoginPresenter(
                        viewManagerModel,
                        loggedInViewModel,
                        loginViewModel,
                        signupViewModel
                );

        presenter.prepareFailView(
                "Incorrect username or password"
        );

        assertEquals(
                "Incorrect username or password",
                loginState.getLoginError()
        );

        verify(loginViewModel)
                .setState(loginState);

        verify(loginViewModel)
                .firePropertyChanged();
    }

    @Test
    void presenterSwitchesToSignupView() {
        final ViewManagerModel viewManagerModel =
                mock(ViewManagerModel.class);

        final LoggedInViewModel loggedInViewModel =
                mock(LoggedInViewModel.class);

        final LoginViewModel loginViewModel =
                mock(LoginViewModel.class);

        final SignupViewModel signupViewModel =
                mock(SignupViewModel.class);

        when(signupViewModel.getViewName())
                .thenReturn("sign up");

        final LoginPresenter presenter =
                new LoginPresenter(
                        viewManagerModel,
                        loggedInViewModel,
                        loginViewModel,
                        signupViewModel
                );

        presenter.switchToSignupView();

        verify(viewManagerModel)
                .setState("sign up");

        verify(viewManagerModel)
                .firePropertyChanged();
    }
}
