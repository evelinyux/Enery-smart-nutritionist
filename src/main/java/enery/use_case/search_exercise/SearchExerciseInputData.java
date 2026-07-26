package enery.use_case.search_exercise;

/**
 * Input data for searching exercise calorie information.
 */
public final class SearchExerciseInputData {

    private final String activityQuery;
    private final double weightInPounds;
    private final int durationInMinutes;

    public SearchExerciseInputData(
            String activityQuery,
            double weightInPounds,
            int durationInMinutes) {

        this.activityQuery = activityQuery;
        this.weightInPounds = weightInPounds;
        this.durationInMinutes = durationInMinutes;
    }

    public String getActivityQuery() {
        return activityQuery;
    }

    public double getWeightInPounds() {
        return weightInPounds;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }
}
