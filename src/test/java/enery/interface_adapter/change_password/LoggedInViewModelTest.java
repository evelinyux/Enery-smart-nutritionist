package enery.interface_adapter.change_password;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LoggedInViewModelTest {

    @Test
    void constructorTest() {
        LoggedInViewModel viewModel =
                new LoggedInViewModel();

        assertEquals(
                "home",
                viewModel.getViewName());

        assertNotNull(
                viewModel.getState());

        assertEquals(
                "",
                viewModel.getState().getUsername());

        assertEquals(
                "",
                viewModel.getState().getPassword());
    }
}