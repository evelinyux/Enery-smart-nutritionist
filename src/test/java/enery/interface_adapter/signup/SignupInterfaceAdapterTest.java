package enery.interface_adapter.signup;

import enery.interface_adapter.login.LoginState;
import enery.interface_adapter.login.LoginViewModel;
import enery.interface_adapter.shared.ViewManagerModel;
import enery.use_case.signup.SignupInputBoundary;
import enery.use_case.signup.SignupInputData;
import enery.use_case.signup.SignupOutputData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SignupInterfaceAdapterTest {

    @Test
    void controllerExecutePassesInputDataToInteractor() {
        final SignupInputBoundary interactor =
                mock(SignupInputBoundary.class);

        final SignupController controller =
                new SignupController(interactor);

        controller.execute(
                "ethan",
                "password123",
                "password123"
        );

        verify(interactor)
                .execute(
                        any(SignupInputData.class)
                );
    }

    @Test
    void controllerSwitchToLoginDelegatesToInteractor() {
        final SignupInputBoundary interactor =
                mock(SignupInputBoundary.class);

        final SignupController controller =
                new SignupController(interactor);

        controller.switchToLoginView();

        verify(interactor).switchToLoginView();
    }

    @Test
    void signupStateStartsWithExpectedDefaults() {
        final SignupState state =
                new SignupState();

        assertEquals("", state.getUsername());
        assertEquals("", state.getPassword());
        assertEquals("", state.getRepeatPassword());

        assertNull(state.getUsernameError());
        assertNull(state.getPasswordError());
        assertNull(state.getRepeatPasswordError());
    }

    @Test
    void signupStateStoresAllValues() {
        final SignupState state =
                new SignupState();

        state.setUsername("ethan");
        state.setUsernameError("username error");

        state.setPassword("password");
        state.setPasswordError("password error");

        state.setRepeatPassword("repeat");
        state.setRepeatPasswordError(
                "repeat error"
        );

        assertEquals(
                "ethan",
                state.getUsername()
        );

        assertEquals(
                "username error",
                state.getUsernameError()
        );

        assertEquals(
                "password",
                state.getPassword()
        );

        assertEquals(
                "password error",
                state.getPasswordError()
        );

        assertEquals(
                "repeat",
                state.getRepeatPassword()
        );

        assertEquals(
                "repeat error",
                state.getRepeatPasswordError()
        );
    }

    @Test
    void signupStateToStringContainsMainValues() {
        final SignupState state =
                new SignupState();

        state.setUsername("ethan");
        state.setPassword("abc");
        state.setRepeatPassword("abc");

        final String result =
                state.toString();

        assertTrue(result.contains("ethan"));
        assertTrue(result.contains("abc"));
    }

    @Test
    void signupViewModelStartsWithState() {
        final SignupViewModel viewModel =
                new SignupViewModel();

        assertNotNull(viewModel.getState());

        assertInstanceOf(
                SignupState.class,
                viewModel.getState()
        );

        assertEquals(
                "sign up",
                viewModel.getViewName()
        );
    }

    @Test
    void presenterSuccessCopiesUsernameAndNavigates() {
        final ViewManagerModel viewManagerModel =
                mock(ViewManagerModel.class);

        final SignupViewModel signupViewModel =
                mock(SignupViewModel.class);

        final LoginViewModel loginViewModel =
                mock(LoginViewModel.class);

        final LoginState loginState =
                new LoginState();

        when(loginViewModel.getState())
                .thenReturn(loginState);

        when(loginViewModel.getViewName())
                .thenReturn("log in");

        final SignupOutputData response =
                mock(SignupOutputData.class);

        when(response.getUsername())
                .thenReturn("new-user");

        final SignupPresenter presenter =
                new SignupPresenter(
                        viewManagerModel,
                        signupViewModel,
                        loginViewModel
                );

        presenter.prepareSuccessView(response);

        assertEquals(
                "new-user",
                loginState.getUsername()
        );

        verify(loginViewModel)
                .setState(loginState);

        verify(loginViewModel)
                .firePropertyChanged();

        verify(viewManagerModel)
                .setState("log in");

        verify(viewManagerModel)
                .firePropertyChanged();
    }

    @Test
    void presenterFailureSetsUsernameError() {
        final ViewManagerModel viewManagerModel =
                mock(ViewManagerModel.class);

        final SignupViewModel signupViewModel =
                mock(SignupViewModel.class);

        final LoginViewModel loginViewModel =
                mock(LoginViewModel.class);

        final SignupState signupState =
                new SignupState();

        when(signupViewModel.getState())
                .thenReturn(signupState);

        final SignupPresenter presenter =
                new SignupPresenter(
                        viewManagerModel,
                        signupViewModel,
                        loginViewModel
                );

        presenter.prepareFailView(
                "Username already exists"
        );

        assertEquals(
                "Username already exists",
                signupState.getUsernameError()
        );

        verify(signupViewModel)
                .firePropertyChanged();
    }

    @Test
    void presenterSwitchToLoginNavigates() {
        final ViewManagerModel viewManagerModel =
                mock(ViewManagerModel.class);

        final SignupViewModel signupViewModel =
                mock(SignupViewModel.class);

        final LoginViewModel loginViewModel =
                mock(LoginViewModel.class);

        when(loginViewModel.getViewName())
                .thenReturn("log in");

        final SignupPresenter presenter =
                new SignupPresenter(
                        viewManagerModel,
                        signupViewModel,
                        loginViewModel
                );

        presenter.switchToLoginView();

        verify(viewManagerModel)
                .setState("log in");

        verify(viewManagerModel)
                .firePropertyChanged();
    }
}
