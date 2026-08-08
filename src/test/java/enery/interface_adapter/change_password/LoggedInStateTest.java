package enery.interface_adapter.change_password;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoggedInStateTest {

    @Test
    void usernameTest() {
        LoggedInState state =
                new LoggedInState();

        state.setUsername("nicole");

        assertEquals(
                "nicole",
                state.getUsername());
    }

    @Test
    void passwordTest() {
        LoggedInState state =
                new LoggedInState();

        state.setPassword("password123");

        assertEquals(
                "password123",
                state.getPassword());
    }

    @Test
    void copyConstructorTest() {
        LoggedInState original =
                new LoggedInState();

        original.setUsername("nicole");
        original.setPassword("password123");
        original.setPasswordError("error");

        LoggedInState copy =
                new LoggedInState(original);

        assertEquals(
                "nicole",
                copy.getUsername());

        assertEquals(
                "password123",
                copy.getPassword());
    }
}