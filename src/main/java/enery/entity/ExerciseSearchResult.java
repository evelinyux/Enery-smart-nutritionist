package enery.entity;

/**
 * Represents one exercise result returned by a calories-burned search.
 */
public final class ExerciseSearchResult {

    private final String exerciseName;
    private final double caloriesPerHour;
    private final int durationInMinutes;
    private final double totalCalories;

    public ExerciseSearchResult(
            String exerciseName,
            double caloriesPerHour,
            int durationInMinutes,
            double totalCalories) {

        this.exerciseName = exerciseName;
        this.caloriesPerHour = caloriesPerHour;
        this.durationInMinutes = durationInMinutes;
        this.totalCalories = totalCalories;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public double getCaloriesPerHour() {
        return caloriesPerHour;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public double getTotalCalories() {
        return totalCalories;
    }

    @Override
    public String toString() {
        return exerciseName
                + " — "
                + totalCalories
                + " calories";
    }
}