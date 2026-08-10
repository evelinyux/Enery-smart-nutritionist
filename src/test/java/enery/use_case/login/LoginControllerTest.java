package enery.use_case.login;

import enery.interface_adapter.login.LoginController;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class LoginControllerTest {

    @Test
    void executeTest() {
        final LoginInputBoundary inputBoundary =
                mock(LoginInputBoundary.class);

        final LoginController controller =
                new LoginController(inputBoundary);

        controller.execute("Nicole", "password123");

        final ArgumentCaptor<LoginInputData> captor =
                ArgumentCaptor.forClass(LoginInputData.class);

        verify(inputBoundary).execute(captor.capture());

        final LoginInputData inputData = captor.getValue();

        assertEquals("Nicole", inputData.getUsername());
        assertEquals("password123", inputData.getPassword());
    }

    @Test
    void switchToSignupViewTest() {
        final LoginInputBoundary inputBoundary =
                mock(LoginInputBoundary.class);

        final LoginController controller =
                new LoginController(inputBoundary);

        controller.switchToSignupView();

        verify(inputBoundary).switchToSignupView();
    }
}