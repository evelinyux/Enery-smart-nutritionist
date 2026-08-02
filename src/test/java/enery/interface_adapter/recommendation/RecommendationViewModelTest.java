package enery.interface_adapter.recommendation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RecommendationViewModelTest {

    @Test
    @DisplayName("firePropertyChanged - Triggers registered PropertyChangeListener")
    void firePropertyChanged_NotifiesRegisteredListener() {
        // Arrange
        final RecommendationViewModel viewModel = new RecommendationViewModel();
        final AtomicBoolean listenerFired = new AtomicBoolean(false);

        final PropertyChangeListener listener = (PropertyChangeEvent evt) -> {
            if ("state".equals(evt.getPropertyName())) {
                listenerFired.set(true);
            }
        };

        viewModel.addPropertyChangeListener(listener);

        // Act
        viewModel.firePropertyChanged();

        // Assert
        assertTrue(listenerFired.get(), "Listener should be called when firePropertyChanged is triggered");
    }

    @Test
    @DisplayName("setState / getState - Correctly gets and sets recommendation state")
    void stateGetterAndSetter_WorkAsExpected() {
        // Arrange
        final RecommendationViewModel viewModel = new RecommendationViewModel();
        final RecommendationState newState = new RecommendationState();
        newState.setRecommendationError("Test Error");

        // Act
        viewModel.setState(newState);

        // Assert
        assertNotNull(viewModel.getState());
        assertEquals("Test Error", viewModel.getState().getRecommendationError());
    }
}