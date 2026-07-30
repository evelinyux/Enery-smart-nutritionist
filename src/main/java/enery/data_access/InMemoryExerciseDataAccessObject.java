package enery.data_access;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import enery.entity.exercise.ExerciseEntry;
import enery.use_case.log_exercise.LogExerciseUserDataAccessInterface;

/**
 * Stores exercise entries in memory.
 */
public final class InMemoryExerciseDataAccessObject
        implements LogExerciseUserDataAccessInterface {

    private final Map<String, List<ExerciseEntry>>
            exercisesByUsername = new HashMap<>();

    @Override
    public void saveExercise(
            String username,
            ExerciseEntry exerciseEntry) {

        exercisesByUsername
                .computeIfAbsent(
                        username,
                        key -> new ArrayList<>()
                )
                .add(exerciseEntry);
    }

    @Override
    public List<ExerciseEntry> getExercises(
            String username,
            LocalDate date) {

        final List<ExerciseEntry> exercises =
                exercisesByUsername.getOrDefault(
                        username,
                        Collections.emptyList()
                );

        final List<ExerciseEntry> matchingExercises =
                new ArrayList<>();

        for (ExerciseEntry exercise : exercises) {
            if (exercise.getDateTime()
                    .toLocalDate()
                    .equals(date)) {

                matchingExercises.add(exercise);
            }
        }

        return Collections.unmodifiableList(
                matchingExercises);
    }
}