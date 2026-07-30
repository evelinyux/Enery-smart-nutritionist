package enery.entity;

import java.time.LocalDate;

/**
 * Represents one exercise activity recorded by a user.
 */
public final class ExerciseEntry {

    private final String exerciseName;
    private final int durationInMinutes;
    private final LocalDate todaysDate;

    public ExerciseEntry(
            String exerciseName,
            int durationInMinutes) {
//TODO: Figure out if we need to .lower(0 the exercise name
        this.exerciseName = exerciseName;
        this.durationInMinutes = durationInMinutes;
        this.todaysDate = LocalDate.now();
    }

        public String getExerciseName () {
            return exerciseName;
        }

        public int getDurationInMinutes () {
            return durationInMinutes;
        }

        public LocalDate getTodaysDate () {
            return todaysDate;
        }
    }

