package enery.interface_adapter.search_exercise;

import java.util.List;

import org.junit.jupiter.api.Test;

import enery.entity.ExerciseSearchResult;
import enery.use_case.search_exercise.SearchExerciseOutputData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SearchExercisePresenterTest {

    @Test
    void preparesSuccessState() {
        final SearchExerciseViewModel viewModel =
                new SearchExerciseViewModel();

        final SearchExercisePresenter presenter =
                new SearchExercisePresenter(viewModel);

        final ExerciseSearchResult result =
                new ExerciseSearchResult(
                        "Cycling",
                        600.0,
                        30,
                        300.0
                );

        presenter.prepareSuccessView(
                new SearchExerciseOutputData(
                        List.of(result)
                )
        );

        final SearchExerciseState state =
                viewModel.getState();

        assertEquals(1, state.getResults().size());
        assertEquals(
                "Cycling",
                state.getResults()
                        .get(0)
                        .getExerciseName()
        );
        assertEquals("", state.getErrorMessage());
    }

    @Test
    void preparesFailureState() {
        final SearchExerciseViewModel viewModel =
                new SearchExerciseViewModel();

        final SearchExercisePresenter presenter =
                new SearchExercisePresenter(viewModel);

        presenter.prepareFailView(
                "No matching exercises were found."
        );

        final SearchExerciseState state =
                viewModel.getState();

        assertTrue(state.getResults().isEmpty());
        assertEquals(
                "No matching exercises were found.",
                state.getErrorMessage()
        );
    }
}
