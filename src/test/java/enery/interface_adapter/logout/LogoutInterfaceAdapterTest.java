package enery.interface_adapter.logout;

import enery.interface_adapter.change_password.LoggedInState;
import enery.interface_adapter.change_password.LoggedInViewModel;
import enery.interface_adapter.login.LoginState;
import enery.interface_adapter.login.LoginViewModel;
import enery.interface_adapter.shared.ViewManagerModel;
import enery.use_case.logout.LogoutInputBoundary;
import enery.use_case.logout.LogoutInputData;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LogoutInterfaceAdapterTest {

    @Test
    void controllerExecutePassesUsernameToInteractor() {
        final LogoutInputBoundary interactor =
                mock(LogoutInputBoundary.class);

        final LogoutController controller =
                new LogoutController(interactor);

        controller.execute("ethan");

        final ArgumentCaptor<LogoutInputData> captor =
                ArgumentCaptor.forClass(
                        LogoutInputData.class
                );

        verify(interactor).execute(captor.capture());

        assertEquals(
                "ethan",
                captor.getValue().getUsername()
        );
    }

    @Test
    void presenterSuccessClearsUserInformation() {
        final ViewManagerModel viewManagerModel =
                mock(ViewManagerModel.class);

        final LoggedInViewModel loggedInViewModel =
                mock(LoggedInViewModel.class);

        final LoginViewModel loginViewModel =
                mock(LoginViewModel.class);

        final LoggedInState loggedInState =
                new LoggedInState();

        loggedInState.setUsername("ethan");

        final LoginState loginState =
                new LoginState();

        loginState.setUsername("ethan");
        loginState.setPassword("secret");

        when(loggedInViewModel.getState())
                .thenReturn(loggedInState);

        when(loginViewModel.getState())
                .thenReturn(loginState);

        when(loginViewModel.getViewName())
                .thenReturn("log in");

        final LogoutPresenter presenter =
                new LogoutPresenter(
                        viewManagerModel,
                        loggedInViewModel,
                        loginViewModel
                );

        presenter.prepareSuccessView(null);

        assertEquals(
                "",
                loggedInState.getUsername()
        );

        assertEquals(
                "",
                loginState.getUsername()
        );

        assertEquals(
                "",
                loginState.getPassword()
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
                .setState("log in");

        verify(viewManagerModel)
                .firePropertyChanged();
    }

    @Test
    void presenterFailViewDoesNothing() {
        final ViewManagerModel viewManagerModel =
                mock(ViewManagerModel.class);

        final LoggedInViewModel loggedInViewModel =
                mock(LoggedInViewModel.class);

        final LoginViewModel loginViewModel =
                mock(LoginViewModel.class);

        final LogoutPresenter presenter =
                new LogoutPresenter(
                        viewManagerModel,
                        loggedInViewModel,
                        loginViewModel
                );

        assertDoesNotThrow(
                () -> presenter.prepareFailView(
                        "failure"
                )
        );

        verifyNoInteractions(
                viewManagerModel,
                loggedInViewModel,
                loginViewModel
        );
    }
}
