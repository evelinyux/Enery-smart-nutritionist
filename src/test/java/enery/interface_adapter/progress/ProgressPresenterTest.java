package enery.interface_adapter.progress;

import enery.interface_adapter.shared.ViewManagerModel;
import enery.use_case.progress.ProgressOutputData;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ProgressPresenterTest {

    private ProgressViewModel viewModel;
    private ProgressPresenter presenter;

    @BeforeEach
    void setUp() {
        final ViewManagerModel viewManagerModel =
                new ViewManagerModel();

        viewModel =
                new ProgressViewModel();

        presenter =
                new ProgressPresenter(
                        viewManagerModel,
                        viewModel
                );
    }

    @Test
    void prepareSuccessViewUpdatesProgressStateTest() {
        final ProgressOutputData outputData =
                createOutputData(
                        1500.0,
                        2000.0,
                        0.75
                );

        presenter.prepareSuccessView(
                outputData
        );

        final ProgressState state =
                viewModel.getState();

        assertEquals(
                1500.0,
                state.getTodaysCalories(),
                0.0001
        );

        assertEquals(
                2000.0,
                state.getDailyCalorieGoal(),
                0.0001
        );

        assertEquals(
                0.75,
                state.getDailyProgressPercent(),
                0.0001
        );
    }

    @Test
    void prepareSuccessViewClearsOldErrorTest() {
        final ProgressState state =
                viewModel.getState();

        state.setProgressError(
                "Old error"
        );

        state.setSuccessMessage(
                "Old message"
        );

        viewModel.setState(
                state
        );

        presenter.prepareSuccessView(
                createOutputData(
                        1000.0,
                        2000.0,
                        0.5
                )
        );

        assertNull(
                viewModel.getState()
                        .getProgressError()
        );

        assertEquals(
                "",
                viewModel.getState()
                        .getSuccessMessage()
        );
    }

    @Test
    void prepareFailViewSetsErrorTest() {
        presenter.prepareFailView(
                "Unable to load progress."
        );

        final ProgressState state =
                viewModel.getState();

        assertEquals(
                "Unable to load progress.",
                state.getProgressError()
        );

        assertNull(
                state.getSuccessMessage()
        );
    }

    @Test
    void prepareSuccessViewFiresPropertyChangeTest() {
        final int[] eventCount = {0};

        viewModel.addPropertyChangeListener(
                event -> eventCount[0]++
        );

        presenter.prepareSuccessView(
                createOutputData(
                        1200.0,
                        2400.0,
                        0.5
                )
        );

        assertEquals(
                1,
                eventCount[0]
        );
    }

    @Test
    void prepareFailViewFiresPropertyChangeTest() {
        final int[] eventCount = {0};

        viewModel.addPropertyChangeListener(
                event -> eventCount[0]++
        );

        presenter.prepareFailView(
                "Failure"
        );

        assertEquals(
                1,
                eventCount[0]
        );
    }

    private static ProgressOutputData createOutputData(
            double todaysCalories,
            double dailyCalorieGoal,
            double dailyProgressPercent) {

        return new ProgressOutputData(
                todaysCalories,
                dailyCalorieGoal,
                dailyProgressPercent,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                0
        );
    }
}
