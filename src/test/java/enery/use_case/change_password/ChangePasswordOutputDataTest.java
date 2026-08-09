package enery.use_case.change_password;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChangePasswordOutputDataTest {

    @Test
    void successOutputDataTest() {
        ChangePasswordOutputData outputData =
                new ChangePasswordOutputData(
                        "nicole",
                        false);

        assertEquals(
                "nicole",
                outputData.getUsername());

        assertFalse(
                outputData.isUseCaseFailed());
    }

    @Test
    void failureOutputDataTest() {
        ChangePasswordOutputData outputData =
                new ChangePasswordOutputData(
                        "nicole",
                        true);

        assertEquals(
                "nicole",
                outputData.getUsername());

        assertTrue(
                outputData.isUseCaseFailed());
    }
}