package enery.entity;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommonExerciseEntryFactoryTest {

    @Test
    void createsExerciseEntry() {
        final LocalDateTime time =
                LocalDateTime.of(2026, 7, 26, 18, 0);

        final ExerciseEntryFactory factory =
                new CommonExerciseEntryFactory();

        final ExerciseEntry entry = factory.create(
                "Cycling",
                45,
                300.0,
                time
        );

        assertEquals("Cycling", entry.getExerciseName());
        assertEquals(45, entry.getDurationInMinutes());
        assertEquals(300.0, entry.getCaloriesBurned());
        assertEquals(time, entry.getDateTime());
    }
}