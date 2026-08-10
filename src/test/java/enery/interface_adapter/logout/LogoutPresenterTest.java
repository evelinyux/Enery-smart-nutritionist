package enery.interface_adapter.logout;

import enery.interface_adapter.change_password.LoggedInState;
import enery.interface_adapter.change_password.LoggedInViewModel;
import enery.interface_adapter.login.LoginState;
import enery.interface_adapter.login.LoginViewModel;
import enery.interface_adapter.shared.ViewManagerModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class LogoutPresenterTest {

    private ViewManagerModel viewManagerModel;
    private LoggedInViewModel loggedInViewModel;
    private LoginViewModel loginViewModel;

    private LoggedInState loggedInState;
    private LoginState loginState;

    private LogoutPresenter presenter;

    @BeforeEach
    void setUp() {
        viewManagerModel = mock(ViewManagerModel.class);
        loggedInViewModel = mock(LoggedInViewModel.class);
        loginViewModel = mock(LoginViewModel.class);

        loggedInState = mock(LoggedInState.class);
        loginState = mock(LoginState.class);

        when(loggedInViewModel.getState()).thenReturn(loggedInState);
        when(loginViewModel.getState()).thenReturn(loginState);
        when(loginViewModel.getViewName()).thenReturn("log in");

        presenter = new LogoutPresenter(
                viewManagerModel,
                loggedInViewModel,
                loginViewModel
        );
    }

    @Test
    void prepareSuccessViewTest() {
        // LogoutOutputData is not actually used by the presenter,
        // so null is sufficient here.
        presenter.prepareSuccessView(null);

        // Logged-in user should be cleared.
        verify(loggedInState).setUsername("");

        verify(loggedInViewModel).setState(loggedInState);
        verify(loggedInViewModel).firePropertyChanged();

        // Login form should be reset.
        verify(loginState).setUsername("");
        verify(loginState).setPassword("");

        verify(loginViewModel).setState(loginState);
        verify(loginViewModel).firePropertyChanged();

        // Application should return to log in view.
        verify(viewManagerModel).setState("log in");
        verify(viewManagerModel).firePropertyChanged();
    }

    @Test
    void prepareFailViewTest() {
        presenter.prepareFailView("Logout failed");

        // The current implementation intentionally does nothing on failure.
        verifyNoInteractions(
                viewManagerModel,
                loggedInViewModel,
                loginViewModel
        );
    }
}