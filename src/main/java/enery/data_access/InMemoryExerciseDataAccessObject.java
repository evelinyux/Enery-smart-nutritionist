package enery.data_access;

import enery.entity.exercise.ExerciseEntry;
import enery.entity.exercise.ExerciseSearchResult;
import enery.use_case.log_exercise.LogExerciseUserDataAccessInterface;
import enery.use_case.recommendation.RecommendationExerciseDataAccessInterface;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryExerciseDataAccessObject
        implements LogExerciseUserDataAccessInterface,
        RecommendationExerciseDataAccessInterface {
    private final Map<String, List<ExerciseEntry>> exerciseEntries =
            new HashMap<>();

    public List<ExerciseEntry> getExercises(
            String username,
            LocalDate date) {

        return exerciseEntries
                .getOrDefault(username, List.of())
                .stream()
                .filter(entry -> entry.getDate().equals(date))
                .toList();
    }

    @Override
    public List<ExerciseSearchResult> fetchCandidateExercises(int targetCaloriesToBurn) {
        return List.of(
                new ExerciseSearchResult("Running", 300, 60, 300),
                new ExerciseSearchResult("Cycling", 200, 60, 200),
                new ExerciseSearchResult("Jumping Jacks", 150, 60, 150)
        );
    }

    @Override
    public void saveExercise(
            String username,
            ExerciseEntry exerciseEntry) {

        exerciseEntries
                .computeIfAbsent(username, key -> new ArrayList<>())
                .add(exerciseEntry);
    }
}