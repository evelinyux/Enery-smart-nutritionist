package enery.use_case.search_exercise;

import java.util.List;

import enery.entity.ExerciseSearchResult;

/**
 * Output data containing matching exercise results.
 */
public final class SearchExerciseOutputData {

    private final List<ExerciseSearchResult> results;

    public SearchExerciseOutputData(
            List<ExerciseSearchResult> results) {

        this.results = List.copyOf(results);
    }

    public List<ExerciseSearchResult> getResults() {
        return results;
    }
}
