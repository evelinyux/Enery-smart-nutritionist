package enery.use_case.log_exercise;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import enery.data_access.InMemoryExerciseDataAccessObject;
import enery.entity.CommonExerciseEntryFactory;
import enery.entity.ExerciseEntry;
import enery.entity.ExerciseEntryFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class LogExerciseInteractorTest {

    @Test
    void successTest() {
        final InMemoryExerciseDataAccessObject repository =
                new InMemoryExerciseDataAccessObject();

        final LocalDateTime time =
                LocalDateTime.of(2026, 7, 26, 18, 0);

        final LogExerciseInputData inputData =
                new LogExerciseInputData(
                        "Paul",
                        "Cycling",
                        45,
                        300.0,
                        time
                );

        final boolean[] successCalled = {false};

        final LogExerciseOutputBoundary presenter =
                new LogExerciseOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            LogExerciseOutputData outputData) {

                        successCalled[0] = true;

                        assertEquals(
                                "Paul",
                                outputData.getUsername()
                        );

                        assertEquals(
                                "Cycling",
                                outputData
                                        .getExerciseEntry()
                                        .getExerciseName()
                        );
                    }

                    @Override
                    public void prepareFailView(
                            String errorMessage) {

                        fail(
                                "Use case failure is unexpected.");
                    }
                };

        final ExerciseEntryFactory factory =
                new CommonExerciseEntryFactory();

        final LogExerciseInputBoundary interactor =
                new LogExerciseInteractor(
                        repository,
                        presenter,
                        factory
                );

        interactor.execute(inputData);

        assertTrue(successCalled[0]);

        final List<ExerciseEntry> exercises =
                repository.getExercises(
                        "Paul",
                        LocalDate.of(2026, 7, 26)
                );

        assertEquals(1, exercises.size());
        assertEquals(
                300.0,
                exercises.get(0).getCaloriesBurned()
        );
    }

    @Test
    void rejectsNonPositiveDuration() {
        final InMemoryExerciseDataAccessObject repository =
                new InMemoryExerciseDataAccessObject();

        final LogExerciseInputData inputData =
                new LogExerciseInputData(
                        "Paul",
                        "Cycling",
                        0,
                        300.0,
                        LocalDateTime.now()
                );

        final LogExerciseOutputBoundary presenter =
                new LogExerciseOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            LogExerciseOutputData outputData) {

                        fail(
                                "Use case success is unexpected.");
                    }

                    @Override
                    public void prepareFailView(
                            String errorMessage) {

                        assertEquals(
                                "Duration must be greater than zero.",
                                errorMessage
                        );
                    }
                };

        final LogExerciseInputBoundary interactor =
                new LogExerciseInteractor(
                        repository,
                        presenter,
                        new CommonExerciseEntryFactory()
                );

        interactor.execute(inputData);

        assertTrue(
                repository
                        .getExercises(
                                "Paul",
                                LocalDate.now()
                        )
                        .isEmpty()
        );
    }
}
