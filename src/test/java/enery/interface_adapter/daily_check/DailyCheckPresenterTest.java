package enery.interface_adapter.daily_check;

import enery.interface_adapter.shared.ViewManagerModel;
import enery.use_case.daily_check.DailyCheckOutputData;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DailyCheckPresenterTest {

    private DailyCheckViewModel viewModel;
    private DailyCheckPresenter presenter;

    @BeforeEach
    void setUp() {
        final ViewManagerModel viewManagerModel =
                new ViewManagerModel();

        viewModel =
                new DailyCheckViewModel();

        presenter =
                new DailyCheckPresenter(
                        viewManagerModel,
                        viewModel
                );
    }

    @Test
    void successUpdatesAllLoggedStatesTest() {
        final DailyCheckOutputData outputData =
                new DailyCheckOutputData(
                        true,
                        true,
                        false,
                        true
                );

        presenter.prepareSuccessView(
                outputData
        );

        final DailyCheckState state =
                viewModel.getState();

        assertTrue(
                state.isBreakFastLogged()
        );

        assertTrue(
                state.isLunchLogged()
        );

        assertFalse(
                state.isDinnerLogged()
        );

        assertTrue(
                state.isExerciseLogged()
        );
    }

    @Test
    void successClearsPreviousErrorTest() {
        final DailyCheckState initialState =
                viewModel.getState();

        initialState.setDailyCheckError(
                "Old error"
        );

        initialState.setSuccessMessage(
                "Old success"
        );

        viewModel.setState(
                initialState
        );

        final DailyCheckOutputData outputData =
                new DailyCheckOutputData(
                        true,
                        false,
                        false,
                        false
                );

        presenter.prepareSuccessView(
                outputData
        );

        final DailyCheckState state =
                viewModel.getState();

        assertNull(
                state.getDailyCheckError()
        );

        assertEquals(
                "",
                state.getSuccessMessage()
        );
    }

    @Test
    void successCanSetAllValuesFalseTest() {
        final DailyCheckOutputData outputData =
                new DailyCheckOutputData(
                        false,
                        false,
                        false,
                        false
                );

        presenter.prepareSuccessView(
                outputData
        );

        final DailyCheckState state =
                viewModel.getState();

        assertFalse(
                state.isBreakFastLogged()
        );

        assertFalse(
                state.isLunchLogged()
        );

        assertFalse(
                state.isDinnerLogged()
        );

        assertFalse(
                state.isExerciseLogged()
        );
    }

    @Test
    void failureSetsErrorMessageTest() {
        presenter.prepareFailureView(
                "Unable to load daily check."
        );

        final DailyCheckState state =
                viewModel.getState();

        assertEquals(
                "Unable to load daily check.",
                state.getDailyCheckError()
        );

        assertNull(
                state.getSuccessMessage()
        );
    }

    @Test
    void failureReplacesPreviousSuccessMessageTest() {
        final DailyCheckState initialState =
                viewModel.getState();

        initialState.setSuccessMessage(
                "Everything is logged."
        );

        initialState.setDailyCheckError(
                null
        );

        viewModel.setState(
                initialState
        );

        presenter.prepareFailureView(
                "Something went wrong."
        );

        final DailyCheckState state =
                viewModel.getState();

        assertEquals(
                "Something went wrong.",
                state.getDailyCheckError()
        );

        assertNull(
                state.getSuccessMessage()
        );
    }

    @Test
    void successFiresPropertyChangeTest() {
        final int[] eventCount = {0};

        viewModel.addPropertyChangeListener(
                event -> eventCount[0]++
        );

        final DailyCheckOutputData outputData =
                new DailyCheckOutputData(
                        true,
                        true,
                        true,
                        true
                );

        presenter.prepareSuccessView(
                outputData
        );

        assertEquals(
                1,
                eventCount[0]
        );
    }

    @Test
    void failureFiresPropertyChangeTest() {
        final int[] eventCount = {0};

        viewModel.addPropertyChangeListener(
                event -> eventCount[0]++
        );

        presenter.prepareFailureView(
                "Failure"
        );

        assertEquals(
                1,
                eventCount[0]
        );
    }
}
