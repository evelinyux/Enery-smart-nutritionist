package enery.interface_adapter.change_password;

import enery.use_case.change_password.ChangePasswordOutputData;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChangePasswordPresenterTest {

    @Test
    void prepareSuccessViewTest() {
        // Arrange
        LoggedInViewModel viewModel = new LoggedInViewModel();

        List<PropertyChangeEvent> events =
                new ArrayList<>();

        viewModel.addPropertyChangeListener(events::add);

        ChangePasswordPresenter presenter =
                new ChangePasswordPresenter(viewModel);

        ChangePasswordOutputData outputData =
                new ChangePasswordOutputData(
                        "nicole",
                        false);

        // Act
        presenter.prepareSuccessView(outputData);

        // Assert
        assertEquals(1, events.size());
        assertEquals(
                "password",
                events.get(0).getPropertyName());

        assertEquals(
                viewModel.getState(),
                events.get(0).getNewValue());
    }

    @Test
    void prepareFailViewDoesNothingTest() {
        // Arrange
        LoggedInViewModel viewModel =
                new LoggedInViewModel();

        List<PropertyChangeEvent> events =
                new ArrayList<>();

        viewModel.addPropertyChangeListener(events::add);

        ChangePasswordPresenter presenter =
                new ChangePasswordPresenter(viewModel);

        // Act
        presenter.prepareFailView("error");

        // Assert
        assertTrue(events.isEmpty());
    }
}