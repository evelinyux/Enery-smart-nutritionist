package enery.interface_adapter.forgot_password;

import enery.use_case.forgot_password.ForgotPasswordInputBoundary;
import enery.use_case.forgot_password.ForgotPasswordInputData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ForgotPasswordControllerTest {

    @Test
    void executeTest() {
        FakeInteractor interactor =
                new FakeInteractor();

        ForgotPasswordController controller =
                new ForgotPasswordController(
                        interactor);

        controller.execute(
                "nicole",
                "newPassword",
                "newPassword");

        assertTrue(
                interactor.executeCalled);

        assertNotNull(
                interactor.inputData);

        assertEquals(
                "nicole",
                interactor.inputData.getUsername());

        assertEquals(
                "newPassword",
                interactor.inputData.getNewPassword());

        assertEquals(
                "newPassword",
                interactor.inputData.getConfirmPassword());
    }

    @Test
    void switchToLoginViewTest() {
        FakeInteractor interactor =
                new FakeInteractor();

        ForgotPasswordController controller =
                new ForgotPasswordController(
                        interactor);

        controller.switchToLoginView();

        assertTrue(
                interactor.switchCalled);
    }

    private static class FakeInteractor
            implements ForgotPasswordInputBoundary {

        private boolean executeCalled;
        private boolean switchCalled;

        private ForgotPasswordInputData inputData;

        @Override
        public void execute(
                ForgotPasswordInputData inputData) {

            executeCalled = true;
            this.inputData = inputData;
        }

        @Override
        public void switchToLoginView() {
            switchCalled = true;
        }
    }
}