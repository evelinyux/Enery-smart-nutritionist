package enery.interface_adapter.progress;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProgressViewModelTest {

    @Test
    void constructorInitializesStateTest() {
        final ProgressViewModel viewModel =
                new ProgressViewModel();

        assertNotNull(
                viewModel.getState()
        );
    }

    @Test
    void viewNameIsProgressTest() {
        final ProgressViewModel viewModel =
                new ProgressViewModel();

        assertEquals(
                "progress",
                viewModel.getViewName()
        );
    }
}
