package enery.interface_adapter.forgot_password;

import enery.interface_adapter.shared.ViewManagerModel;
import enery.use_case.forgot_password.ForgotPasswordOutputData;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ForgotPasswordPresenterTest {

    @Test
    void prepareSuccessViewTest() {
        ViewManagerModel viewManagerModel =
                new ViewManagerModel();

        ForgotPasswordViewModel viewModel =
                new ForgotPasswordViewModel();

        ForgotPasswordState state =
                viewModel.getState();

        state.setUsername("nicole");
        state.setNewPassword("password");
        state.setConfirmPassword("password");
        state.setError("old error");

        List<PropertyChangeEvent> events =
                new ArrayList<>();

        viewModel.addPropertyChangeListener(
                events::add);

        ForgotPasswordPresenter presenter =
                new ForgotPasswordPresenter(
                        viewManagerModel,
                        viewModel);

        presenter.prepareSuccessView(
                new ForgotPasswordOutputData(
                        "nicole"));

        assertNull(
                state.getError());

        assertEquals(
                "Password reset successfully.",
                state.getSuccessMessage());

        assertEquals(
                "",
                state.getNewPassword());

        assertEquals(
                "",
                state.getConfirmPassword());

        assertEquals(
                "nicole",
                state.getUsername());

        assertEquals(
                1,
                events.size());

        assertEquals(
                "state",
                events.get(0).getPropertyName());
    }

    @Test
    void prepareFailViewTest() {
        ViewManagerModel viewManagerModel =
                new ViewManagerModel();

        ForgotPasswordViewModel viewModel =
                new ForgotPasswordViewModel();

        viewModel.getState().setSuccessMessage(
                "old success");

        List<PropertyChangeEvent> events =
                new ArrayList<>();

        viewModel.addPropertyChangeListener(
                events::add);

        ForgotPasswordPresenter presenter =
                new ForgotPasswordPresenter(
                        viewManagerModel,
                        viewModel);

        presenter.prepareFailView(
                "Passwords do not match.");

        ForgotPasswordState state =
                viewModel.getState();

        assertEquals(
                "Passwords do not match.",
                state.getError());

        assertNull(
                state.getSuccessMessage());

        assertEquals(
                1,
                events.size());

        assertEquals(
                "state",
                events.get(0).getPropertyName());
    }

    @Test
    void switchToLoginViewTest() {
        ViewManagerModel viewManagerModel =
                new ViewManagerModel();

        ForgotPasswordViewModel viewModel =
                new ForgotPasswordViewModel();

        List<PropertyChangeEvent> events =
                new ArrayList<>();

        viewManagerModel.addPropertyChangeListener(
                events::add);

        ForgotPasswordPresenter presenter =
                new ForgotPasswordPresenter(
                        viewManagerModel,
                        viewModel);

        presenter.switchToLoginView();

        assertEquals(
                "log in",
                viewManagerModel.getState());

        assertEquals(
                1,
                events.size());

        assertEquals(
                "state",
                events.get(0).getPropertyName());
    }
}