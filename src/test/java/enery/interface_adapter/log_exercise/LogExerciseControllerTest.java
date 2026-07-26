package enery.interface_adapter.log_exercise;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import enery.use_case.log_exercise.LogExerciseInputBoundary;
import enery.use_case.log_exercise.LogExerciseInputData;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LogExerciseControllerTest {

    @Test
    void passesInputToInteractor() {
        final LogExerciseInputData[] receivedInput = {null};

        final LogExerciseInputBoundary interactor =
                inputData -> receivedInput[0] = inputData;

        final LogExerciseController controller =
                new LogExerciseController(interactor);

        final LocalDateTime time =
                LocalDateTime.of(2026, 7, 26, 18, 0);

        controller.execute(
                "Paul",
                "Cycling",
                45,
                300.0,
                time
        );

        assertEquals(
                "Paul",
                receivedInput[0].getUsername()
        );
        assertEquals(
                "Cycling",
                receivedInput[0].getExerciseName()
        );
        assertEquals(
                45,
                receivedInput[0].getDurationInMinutes()
        );
        assertEquals(
                300.0,
                receivedInput[0].getCaloriesBurned()
        );
        assertEquals(
                time,
                receivedInput[0].getDateTime()
        );
    }
}
