package enery.use_case.forgot_password;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ForgotPasswordOutputDataTest {

    @Test
    void constructorAndGetterTest() {
        ForgotPasswordOutputData outputData =
                new ForgotPasswordOutputData(
                        "nicole");

        assertEquals(
                "nicole",
                outputData.getUsername());
    }
}