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
                "cycling",
                160.0,
                30
        );

        assertEquals(
                "cycling",
                receivedInput[0].getActivityQuery()
        );
        assertEquals(
                160.0,
                receivedInput[0].getWeightInPounds()
        );
        assertEquals(
                30,
                receivedInput[0].getDurationInMinutes()
        );
    }
}
