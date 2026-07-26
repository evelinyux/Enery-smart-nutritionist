package enery.entity;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExerciseEntryTest {

    @Test
    void storesExerciseValues() {
        final LocalDateTime time =
                LocalDateTime.of(2026, 7, 26, 18, 0);

        final ExerciseEntry entry = new ExerciseEntry(
                "Running",
                30,
                320.0,
                time
        );

        assertEquals("Running", entry.getExerciseName());
        assertEquals(30, entry.getDurationInMinutes());
        assertEquals(320.0, entry.getCaloriesBurned());
        assertEquals(time, entry.getDateTime());
    }
}
