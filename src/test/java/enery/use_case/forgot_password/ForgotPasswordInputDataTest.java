package enery.use_case.forgot_password;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ForgotPasswordInputDataTest {

    @Test
    void constructorAndGettersTest() {
        ForgotPasswordInputData inputData =
                new ForgotPasswordInputData(
                        "nicole",
                        "newPassword",
                        "newPassword");

        assertEquals(
                "nicole",
                inputData.getUsername());

        assertEquals(
                "newPassword",
                inputData.getNewPassword());

        assertEquals(
                "newPassword",
                inputData.getConfirmPassword());
    }
}