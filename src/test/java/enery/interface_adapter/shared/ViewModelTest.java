package enery.interface_adapter.shared;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class ViewModelTest {

    @Test
    void storesViewNameTest() {
        final ViewModel<String> viewModel =
                new ViewModel<>(
                        "test view"
                );

        assertEquals(
                "test view",
                viewModel.getViewName()
        );
    }

    @Test
    void storesAndReturnsStateTest() {
        final ViewModel<String> viewModel =
                new ViewModel<>(
                        "test"
                );

        final String state =
                "hello";

        viewModel.setState(
                state
        );

        assertSame(
                state,
                viewModel.getState()
        );
    }

    @Test
    void firePropertyChangedUsesStatePropertyTest() {
        final ViewModel<String> viewModel =
                new ViewModel<>(
                        "test"
                );

        final List<PropertyChangeEvent> events =
                new ArrayList<>();

        viewModel.addPropertyChangeListener(
                events::add
        );

        viewModel.setState(
                "new state"
        );

        viewModel.firePropertyChanged();

        assertEquals(
                1,
                events.size()
        );

        assertEquals(
                "state",
                events.get(0)
                        .getPropertyName()
        );

        assertEquals(
                "new state",
                events.get(0)
                        .getNewValue()
        );
    }

    @Test
    void customPropertyNameIsUsedTest() {
        final ViewModel<String> viewModel =
                new ViewModel<>(
                        "test"
                );

        final List<PropertyChangeEvent> events =
                new ArrayList<>();

        viewModel.addPropertyChangeListener(
                events::add
        );

        viewModel.setState(
                "abc"
        );

        viewModel.firePropertyChanged(
                "username"
        );

        assertEquals(
                1,
                events.size()
        );

        assertEquals(
                "username",
                events.get(0)
                        .getPropertyName()
        );

        assertEquals(
                "abc",
                events.get(0)
                        .getNewValue()
        );
    }

    @Test
    void multipleListenersAreNotifiedTest() {
        final ViewModel<Integer> viewModel =
                new ViewModel<>(
                        "numbers"
                );

        final int[] firstCount = {0};
        final int[] secondCount = {0};

        viewModel.addPropertyChangeListener(
                event -> firstCount[0]++
        );

        viewModel.addPropertyChangeListener(
                event -> secondCount[0]++
        );

        viewModel.setState(
                10
        );

        viewModel.firePropertyChanged();

        assertEquals(
                1,
                firstCount[0]
        );

        assertEquals(
                1,
                secondCount[0]
        );
    }
}
