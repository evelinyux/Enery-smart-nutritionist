package enery.use_case.search_exercise;

/**
 * Input data for searching exercise calorie information.
 */
public final class SearchExerciseInputData {

    private final String username;
    private final String exerciseName;
    private final int durationInMinutes;

    /**
     * Creates input data for an exercise search.
     *
     * @param username logged-in username
     * @param exerciseName exercise search query
     * @param durationInMinutes exercise duration
     */
    public SearchExerciseInputData(
            String username,
            String exerciseName,
            int durationInMinutes) {

        this.username = username;
        this.exerciseName = exerciseName;
        this.durationInMinutes = durationInMinutes;
    }

    public String getUsername() {
        return username;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }
}
