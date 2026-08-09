package enery.use_case.change_password;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChangePasswordInputDataTest {

    @Test
    void constructorAndGettersTest() {
        ChangePasswordInputData inputData =
                new ChangePasswordInputData(
                        "nicole",
                        "newPassword123");

        assertEquals(
                "nicole",
                inputData.getUsername());

        assertEquals(
                "newPassword123",
                inputData.getPassword());
    }
}