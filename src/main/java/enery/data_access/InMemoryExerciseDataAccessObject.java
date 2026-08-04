package enery.data_access;

import enery.entity.exercise.ExerciseSearchResult;
import enery.use_case.recommendation.RecommendationExerciseDataAccessInterface;
import java.util.List;

public class InMemoryExerciseDataAccessObject implements RecommendationExerciseDataAccessInterface {
    @Override
    public List<ExerciseSearchResult> fetchCandidateExercises(int targetCaloriesToBurn) {
        return List.of(
                new ExerciseSearchResult("Running", 300, 60, 300),
                new ExerciseSearchResult("Cycling", 200, 60, 200),
                new ExerciseSearchResult("Jumping Jacks", 150, 60, 150)
        );
    }
}