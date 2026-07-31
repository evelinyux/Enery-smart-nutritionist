package enery.interface_adapter.search_exercise;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import enery.entity.exercise.ExerciseSearchResult;

/**
 * Stores the current state of the exercise-search screen.
 */
public final class SearchExerciseState {

    private String activityQuery = "";
    private double weightInPounds;
    private int durationInMinutes = 30;
    private List<ExerciseSearchResult> results = new ArrayList<>();
    private String errorMessage = "";

    public String getActivityQuery() {
        return activityQuery;
    }

    public void setActivityQuery(String activityQuery) {
        this.activityQuery = activityQuery;
    }

    public double getWeightInPounds() {
        return weightInPounds;
    }

    public void setWeightInPounds(double weightInPounds) {
        this.weightInPounds = weightInPounds;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public List<ExerciseSearchResult> getResults() {
        return Collections.unmodifiableList(results);
    }

    public void setResults(List<ExerciseSearchResult> results) {
        this.results = new ArrayList<>(results);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
