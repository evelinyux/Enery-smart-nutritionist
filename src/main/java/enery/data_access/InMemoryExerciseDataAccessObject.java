package enery.data_access;

import enery.entity.exercise.ExerciseSearchResult;
import java.util.List;

public class InMemoryExerciseDataAccessObject {
    public List<ExerciseSearchResult> fetchCandidateExercises(int targetCalories) {
        // Return a static/hardcoded list of local exercise entities
        return List.of(
                new ExerciseSearchResult("Running", 300, 60, 300),
                new ExerciseSearchResult("Cycling", 200, 60, 200),
                new ExerciseSearchResult("Jumping Jacks", 150, 60, 150)
        );
    }
}