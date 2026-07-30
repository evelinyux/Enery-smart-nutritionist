package enery.interface_adapter.log_exercise;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import enery.entity.exercise.ExerciseEntry;
import enery.use_case.log_exercise.LogExerciseOutputData;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LogExercisePresenterTest {

    @Test
    void preparesSuccessState() {
        final LogExerciseViewModel viewModel =
                new LogExerciseViewModel();

        final LogExercisePresenter presenter =
                new LogExercisePresenter(viewModel);

        final ExerciseEntry entry =
                new ExerciseEntry(
                        "Cycling",
                        45,
                        300.0,
                        LocalDateTime.now()
                );

        presenter.prepareSuccessView(
                new LogExerciseOutputData(
                        "Paul",
                        entry
                )
        );

        final LogExerciseState state =
                viewModel.getState();

        assertEquals(
                "",
                state.getErrorMessage()
        );
        assertEquals(
                "Cycling was logged successfully.",
                state.getSuccessMessage()
        );
    }

    @Test
    void preparesFailureState() {
        final LogExerciseViewModel viewModel =
                new LogExerciseViewModel();

        final LogExercisePresenter presenter =
                new LogExercisePresenter(viewModel);

        presenter.prepareFailView(
                "Duration must be greater than zero."
        );

        final LogExerciseState state =
                viewModel.getState();

        assertEquals(
                "Duration must be greater than zero.",
                state.getErrorMessage()
        );
        assertEquals(
                "",
                state.getSuccessMessage()
        );
    }
}
