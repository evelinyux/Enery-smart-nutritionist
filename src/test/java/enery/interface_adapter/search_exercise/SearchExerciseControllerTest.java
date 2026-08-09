package enery.interface_adapter.search_exercise;

import org.junit.jupiter.api.Test;

import enery.use_case.search_exercise.SearchExerciseInputBoundary;
import enery.use_case.search_exercise.SearchExerciseInputData;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchExerciseControllerTest {

    @Test
    void passesInputToInteractor() {
        final SearchExerciseInputData[] receivedInput =
                {null};

        final SearchExerciseInputBoundary interactor =
                inputData -> receivedInput[0] = inputData;

        final SearchExerciseController controller =
                new SearchExerciseController(interactor);

        controller.execute(
                "test_user",
                "cycling",
                30
        );

        assertEquals(
                "test_user",
                receivedInput[0].getUsername()
        );

        assertEquals(
                "cycling",
                receivedInput[0].getExerciseName()
        );

        assertEquals(
                30,
                receivedInput[0].getDurationInMinutes()
        );
    }
}
