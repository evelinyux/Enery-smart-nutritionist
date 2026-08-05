package enery.use_case.recommendation;

import enery.entity.exercise.ExerciseSearchResult;
import java.util.List;

public interface RecommendationExerciseDataAccessInterface {
    List<ExerciseSearchResult> fetchCandidateExercises(int targetCaloriesToBurn);
}