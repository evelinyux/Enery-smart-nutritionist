package enery.interface_adapter.profile;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProfileViewModelTest {

    @Test
    void constructorTest() {
        ProfileViewModel viewModel =
                new ProfileViewModel();

        assertEquals(
                "profile",
                viewModel.getViewName());

        assertNotNull(
                viewModel.getState());

        assertInstanceOf(
                ProfileState.class,
                viewModel.getState());
    }
}