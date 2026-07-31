package enery.interface_adapter.log_exercise;

import java.time.LocalDate;

import enery.use_case.log_exercise.LogExerciseInputBoundary;
import enery.use_case.log_exercise.LogExerciseInputData;

/**
 * Controller for the log-exercise use case.
 */
public final class LogExerciseController {

    private final LogExerciseInputBoundary interactor;

    public LogExerciseController(
            LogExerciseInputBoundary interactor) {

        this.interactor = interactor;
    }

    /**
     * Sends exercise information to the log-exercise use case.
     *
     * @param username logged-in username
     * @param exerciseName selected exercise name
     * @param durationInMinutes exercise duration
     * @param caloriesBurned calculated calories burned
     * @param date time of the exercise entry
     */
    public void execute(
            String username,
            String exerciseName,
            int durationInMinutes,
            double caloriesBurned,
            LocalDate date) {

        final LogExerciseInputData inputData =
                new LogExerciseInputData(
                        username,
                        exerciseName,
                        durationInMinutes,
                        caloriesBurned,
                        date
                );

        interactor.execute(inputData);
    }
}
