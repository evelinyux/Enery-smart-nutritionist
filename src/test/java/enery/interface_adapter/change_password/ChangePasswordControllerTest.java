package enery.interface_adapter.change_password;

import enery.use_case.change_password.ChangePasswordInputBoundary;
import enery.use_case.change_password.ChangePasswordInputData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChangePasswordControllerTest {

    @Test
    void executeCallsInteractorTest() {
        // Arrange
        FakeInteractor fakeInteractor =
                new FakeInteractor();

        ChangePasswordController controller =
                new ChangePasswordController(
                        fakeInteractor);

        // Act
        controller.execute(
                "newPassword123",
                "nicole");

        // Assert
        assertTrue(fakeInteractor.executeCalled);
        assertNotNull(fakeInteractor.receivedInputData);
    }

    private static class FakeInteractor
            implements ChangePasswordInputBoundary {

        private boolean executeCalled;
        private ChangePasswordInputData receivedInputData;

        @Override
        public void execute(
                ChangePasswordInputData inputData) {

            executeCalled = true;
            receivedInputData = inputData;
        }
    }
}