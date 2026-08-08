package enery.interface_adapter.forgot_password;

import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ForgotPasswordViewModelTest {

    @Test
    void initialStateTest() {
        ForgotPasswordViewModel viewModel =
                new ForgotPasswordViewModel();

        assertNotNull(
                viewModel.getState());

        assertEquals(
                "",
                viewModel.getState().getUsername());

        assertEquals(
                "",
                viewModel.getState().getNewPassword());

        assertEquals(
                "",
                viewModel.getState().getConfirmPassword());
    }

    @Test
    void setStateTest() {
        ForgotPasswordViewModel viewModel =
                new ForgotPasswordViewModel();

        ForgotPasswordState state =
                new ForgotPasswordState();

        state.setUsername("nicole");

        viewModel.setState(state);

        assertSame(
                state,
                viewModel.getState());

        assertEquals(
                "nicole",
                viewModel.getState().getUsername());
    }

    @Test
    void firePropertyChangedTest() {
        ForgotPasswordViewModel viewModel =
                new ForgotPasswordViewModel();

        List<PropertyChangeEvent> events =
                new ArrayList<>();

        viewModel.addPropertyChangeListener(
                events::add);

        viewModel.firePropertyChanged();

        assertEquals(
                1,
                events.size());

        assertEquals(
                "state",
                events.get(0).getPropertyName());

        assertSame(
                viewModel.getState(),
                events.get(0).getNewValue());
    }
}