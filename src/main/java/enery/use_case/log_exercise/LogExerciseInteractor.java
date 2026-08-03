package enery.use_case.log_exercise;

import enery.entity.exercise.ExerciseEntry;
import enery.entity.exercise.ExerciseEntryFactory;

/**
 * Interactor for logging an exercise activity.
 */
public final class LogExerciseInteractor
        implements LogExerciseInputBoundary {

    private final LogExerciseUserDataAccessInterface dataAccessObject;
    private final LogExerciseOutputBoundary presenter;
    private final ExerciseEntryFactory exerciseEntryFactory;

    public LogExerciseInteractor(
            LogExerciseUserDataAccessInterface dataAccessObject,
            LogExerciseOutputBoundary presenter,
            ExerciseEntryFactory exerciseEntryFactory) {

        this.dataAccessObject = dataAccessObject;
        this.presenter = presenter;
        this.exerciseEntryFactory = exerciseEntryFactory;
    }

    @Override
    public void execute(LogExerciseInputData inputData) {
        if (inputData.getUsername() == null
                || inputData.getUsername().isBlank()) {

            presenter.prepareFailView(
                    "A logged-in user is required.");
            return;
        }

        if (inputData.getExerciseName() == null
                || inputData.getExerciseName().isBlank()) {

            presenter.prepareFailView(
                    "An exercise name is required.");
            return;
        }

        if (inputData.getDurationInMinutes() <= 0) {
            presenter.prepareFailView(
                    "Duration must be greater than zero.");
            return;
        }

        if (inputData.getCaloriesBurned() < 0.0) {
            presenter.prepareFailView(
                    "Calories burned cannot be negative.");
            return;
        }

        if (inputData.getDate() == null) {
            presenter.prepareFailView(
                    "A date and time are required.");
            return;
        }

        final ExerciseEntry exerciseEntry =
                exerciseEntryFactory.create(
                        inputData.getExerciseName(),
                        inputData.getDurationInMinutes(),
                        inputData.getCaloriesBurned(),
                        inputData.getDate()
                );

        dataAccessObject.saveExercise(
                inputData.getUsername(),
                exerciseEntry
        );

        presenter.prepareSuccessView(
                new LogExerciseOutputData(
                        inputData.getUsername(),
                        exerciseEntry
                )
        );
    }
}
