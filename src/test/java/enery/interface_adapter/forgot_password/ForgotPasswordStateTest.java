package enery.interface_adapter.forgot_password;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ForgotPasswordStateTest {

    @Test
    void settersAndGettersTest() {
        ForgotPasswordState state =
                new ForgotPasswordState();

        state.setUsername("nicole");
        state.setNewPassword("newPassword");
        state.setConfirmPassword("newPassword");
        state.setError("error");
        state.setSuccessMessage("success");

        assertEquals(
                "nicole",
                state.getUsername());

        assertEquals(
                "newPassword",
                state.getNewPassword());

        assertEquals(
                "newPassword",
                state.getConfirmPassword());

        assertEquals(
                "error",
                state.getError());

        assertEquals(
                "success",
                state.getSuccessMessage());
    }
}