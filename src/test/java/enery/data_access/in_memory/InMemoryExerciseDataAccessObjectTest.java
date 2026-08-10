package enery.data_access.in_memory;

import java.time.LocalDate;
import java.util.List;

import enery.entity.exercise.ExerciseEntry;
import enery.entity.exercise.ExerciseSearchResult;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryExerciseDataAccessObjectTest {

    private InMemoryExerciseDataAccessObject dataAccessObject;

    @BeforeEach
    void setUp() {
        dataAccessObject =
                new InMemoryExerciseDataAccessObject();
    }

    @Test
    void saveAndGetExerciseTest() {
        final LocalDate date =
                LocalDate.now();

        final ExerciseEntry entry =
                new ExerciseEntry(
                        "Running",
                        30,
                        250.0,
                        date
                );

        dataAccessObject.saveExercise(
                "Ethan",
                entry
        );

        final List<ExerciseEntry> exercises =
                dataAccessObject.getExercises(
                        "Ethan",
                        date
                );

        assertEquals(
                1,
                exercises.size()
        );

        assertEquals(
                "Running",
                exercises
                        .get(0)
                        .getExerciseName()
        );
    }

    @Test
    void filtersExerciseByDateTest() {
        final LocalDate firstDate =
                LocalDate.of(
                        2026,
                        8,
                        9
                );

        final LocalDate secondDate =
                LocalDate.of(
                        2026,
                        8,
                        10
                );

        dataAccessObject.saveExercise(
                "Ethan",
                new ExerciseEntry(
                        "Running",
                        30,
                        250.0,
                        firstDate
                )
        );

        dataAccessObject.saveExercise(
                "Ethan",
                new ExerciseEntry(
                        "Cycling",
                        45,
                        300.0,
                        secondDate
                )
        );

        final List<ExerciseEntry> exercises =
                dataAccessObject.getExercises(
                        "Ethan",
                        secondDate
                );

        assertEquals(
                1,
                exercises.size()
        );

        assertEquals(
                "Cycling",
                exercises
                        .get(0)
                        .getExerciseName()
        );
    }

    @Test
    void differentUsersAreSeparatedTest() {
        final LocalDate date =
                LocalDate.now();

        dataAccessObject.saveExercise(
                "Ethan",
                new ExerciseEntry(
                        "Running",
                        30,
                        250.0,
                        date
                )
        );

        dataAccessObject.saveExercise(
                "Other",
                new ExerciseEntry(
                        "Cycling",
                        45,
                        300.0,
                        date
                )
        );

        assertEquals(
                1,
                dataAccessObject
                        .getExercises(
                                "Ethan",
                                date
                        )
                        .size()
        );

        assertEquals(
                "Running",
                dataAccessObject
                        .getExercises(
                                "Ethan",
                                date
                        )
                        .get(0)
                        .getExerciseName()
        );
    }

    @Test
    void unknownUserReturnsEmptyListTest() {
        assertTrue(
                dataAccessObject
                        .getExercises(
                                "unknown",
                                LocalDate.now()
                        )
                        .isEmpty()
        );
    }

    @Test
    void hasExerciseReturnsTrueTest() {
        final LocalDate date =
                LocalDate.now();

        dataAccessObject.saveExercise(
                "Ethan",
                new ExerciseEntry(
                        "Running",
                        30,
                        250.0,
                        date
                )
        );

        assertTrue(
                dataAccessObject.hasExercise(
                        "Ethan",
                        date
                )
        );
    }

    @Test
    void hasExerciseReturnsFalseTest() {
        assertFalse(
                dataAccessObject.hasExercise(
                        "Ethan",
                        LocalDate.now()
                )
        );
    }

    @Test
    void candidateExercisesTest() {
        final List<ExerciseSearchResult> results =
                dataAccessObject
                        .fetchCandidateExercises(
                                300
                        );

        assertEquals(
                3,
                results.size()
        );

        assertEquals(
                "Running",
                results
                        .get(0)
                        .getExerciseName()
        );

        assertEquals(
                "Cycling",
                results
                        .get(1)
                        .getExerciseName()
        );

        assertEquals(
                "Jumping Jacks",
                results
                        .get(2)
                        .getExerciseName()
        );
    }
}
