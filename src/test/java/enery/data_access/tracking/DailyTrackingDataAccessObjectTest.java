package enery.data_access.tracking;

import java.time.LocalDate;
import java.util.List;

import enery.data_access.in_memory.InMemoryExerciseDataAccessObject;
import enery.data_access.in_memory.InMemoryMealDataAccessObject;
import enery.data_access.in_memory.InMemoryUserDataAccessObject;
import enery.entity.exercise.ExerciseEntry;
import enery.entity.meal.FoodItem;
import enery.entity.meal.MealEntry;
import enery.entity.meal.MealType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DailyTrackingDataAccessObjectTest {

    private InMemoryUserDataAccessObject userDataAccessObject;
    private InMemoryMealDataAccessObject mealDataAccessObject;
    private InMemoryExerciseDataAccessObject exerciseDataAccessObject;
    private DailyTrackingDataAccessObject trackingDataAccessObject;

    @BeforeEach
    void setUp() {
        userDataAccessObject =
                new InMemoryUserDataAccessObject();

        mealDataAccessObject =
                new InMemoryMealDataAccessObject(
                        userDataAccessObject
                );

        exerciseDataAccessObject =
                new InMemoryExerciseDataAccessObject();

        trackingDataAccessObject =
                new DailyTrackingDataAccessObject(
                        mealDataAccessObject,
                        exerciseDataAccessObject
                );
    }

    @Test
    void getMealsReturnsMealsAcrossDateRangeTest() {
        final LocalDate firstDate =
                LocalDate.of(
                        2026,
                        8,
                        8
                );

        final LocalDate secondDate =
                LocalDate.of(
                        2026,
                        8,
                        9
                );

        final LocalDate thirdDate =
                LocalDate.of(
                        2026,
                        8,
                        10
                );

        mealDataAccessObject.saveMeal(
                "Ethan",
                createMeal(
                        "Apple",
                        MealType.BREAKFAST,
                        firstDate
                )
        );

        mealDataAccessObject.saveMeal(
                "Ethan",
                createMeal(
                        "Chicken",
                        MealType.LUNCH,
                        secondDate
                )
        );

        mealDataAccessObject.saveMeal(
                "Ethan",
                createMeal(
                        "Rice",
                        MealType.DINNER,
                        thirdDate
                )
        );

        final List<MealEntry> result =
                trackingDataAccessObject.getMeals(
                        "Ethan",
                        firstDate,
                        thirdDate
                );

        assertEquals(
                3,
                result.size()
        );

        assertEquals(
                "Apple",
                result.get(0)
                        .getFoodItem()
                        .getName()
        );

        assertEquals(
                "Chicken",
                result.get(1)
                        .getFoodItem()
                        .getName()
        );

        assertEquals(
                "Rice",
                result.get(2)
                        .getFoodItem()
                        .getName()
        );
    }

    @Test
    void getMealsIncludesStartAndEndDatesTest() {
        final LocalDate startDate =
                LocalDate.of(
                        2026,
                        8,
                        8
                );

        final LocalDate endDate =
                LocalDate.of(
                        2026,
                        8,
                        10
                );

        mealDataAccessObject.saveMeal(
                "Ethan",
                createMeal(
                        "Start Meal",
                        MealType.BREAKFAST,
                        startDate
                )
        );

        mealDataAccessObject.saveMeal(
                "Ethan",
                createMeal(
                        "End Meal",
                        MealType.DINNER,
                        endDate
                )
        );

        final List<MealEntry> result =
                trackingDataAccessObject.getMeals(
                        "Ethan",
                        startDate,
                        endDate
                );

        assertEquals(
                2,
                result.size()
        );
    }

    @Test
    void getMealsExcludesMealsOutsideRangeTest() {
        final LocalDate startDate =
                LocalDate.of(
                        2026,
                        8,
                        8
                );

        final LocalDate endDate =
                LocalDate.of(
                        2026,
                        8,
                        10
                );

        mealDataAccessObject.saveMeal(
                "Ethan",
                createMeal(
                        "Before",
                        MealType.BREAKFAST,
                        startDate.minusDays(1)
                )
        );

        mealDataAccessObject.saveMeal(
                "Ethan",
                createMeal(
                        "Inside",
                        MealType.LUNCH,
                        startDate
                )
        );

        mealDataAccessObject.saveMeal(
                "Ethan",
                createMeal(
                        "After",
                        MealType.DINNER,
                        endDate.plusDays(1)
                )
        );

        final List<MealEntry> result =
                trackingDataAccessObject.getMeals(
                        "Ethan",
                        startDate,
                        endDate
                );

        assertEquals(
                1,
                result.size()
        );

        assertEquals(
                "Inside",
                result.get(0)
                        .getFoodItem()
                        .getName()
        );
    }

    @Test
    void getMealsReturnsEmptyListWhenNoMealsExistTest() {
        final LocalDate date =
                LocalDate.of(
                        2026,
                        8,
                        10
                );

        final List<MealEntry> result =
                trackingDataAccessObject.getMeals(
                        "Ethan",
                        date,
                        date.plusDays(6)
                );

        assertTrue(
                result.isEmpty()
        );
    }

    @Test
    void getMealsWorksForSingleDayTest() {
        final LocalDate date =
                LocalDate.of(
                        2026,
                        8,
                        10
                );

        mealDataAccessObject.saveMeal(
                "Ethan",
                createMeal(
                        "Single Day Meal",
                        MealType.BREAKFAST,
                        date
                )
        );

        final List<MealEntry> result =
                trackingDataAccessObject.getMeals(
                        "Ethan",
                        date,
                        date
                );

        assertEquals(
                1,
                result.size()
        );
    }

    @Test
    void getExercisesReturnsExercisesAcrossDateRangeTest() {
        final LocalDate firstDate =
                LocalDate.of(
                        2026,
                        8,
                        8
                );

        final LocalDate secondDate =
                LocalDate.of(
                        2026,
                        8,
                        9
                );

        final LocalDate thirdDate =
                LocalDate.of(
                        2026,
                        8,
                        10
                );

        exerciseDataAccessObject.saveExercise(
                "Ethan",
                createExercise(
                        "Running",
                        firstDate
                )
        );

        exerciseDataAccessObject.saveExercise(
                "Ethan",
                createExercise(
                        "Cycling",
                        secondDate
                )
        );

        exerciseDataAccessObject.saveExercise(
                "Ethan",
                createExercise(
                        "Swimming",
                        thirdDate
                )
        );

        final List<ExerciseEntry> result =
                trackingDataAccessObject.getExercises(
                        "Ethan",
                        firstDate,
                        thirdDate
                );

        assertEquals(
                3,
                result.size()
        );

        assertEquals(
                "Running",
                result.get(0).getExerciseName()
        );

        assertEquals(
                "Cycling",
                result.get(1).getExerciseName()
        );

        assertEquals(
                "Swimming",
                result.get(2).getExerciseName()
        );
    }

    @Test
    void getExercisesIncludesBoundaryDatesTest() {
        final LocalDate startDate =
                LocalDate.of(
                        2026,
                        8,
                        8
                );

        final LocalDate endDate =
                LocalDate.of(
                        2026,
                        8,
                        10
                );

        exerciseDataAccessObject.saveExercise(
                "Ethan",
                createExercise(
                        "Start Exercise",
                        startDate
                )
        );

        exerciseDataAccessObject.saveExercise(
                "Ethan",
                createExercise(
                        "End Exercise",
                        endDate
                )
        );

        final List<ExerciseEntry> result =
                trackingDataAccessObject.getExercises(
                        "Ethan",
                        startDate,
                        endDate
                );

        assertEquals(
                2,
                result.size()
        );
    }

    @Test
    void getExercisesExcludesExercisesOutsideRangeTest() {
        final LocalDate startDate =
                LocalDate.of(
                        2026,
                        8,
                        8
                );

        final LocalDate endDate =
                LocalDate.of(
                        2026,
                        8,
                        10
                );

        exerciseDataAccessObject.saveExercise(
                "Ethan",
                createExercise(
                        "Before",
                        startDate.minusDays(1)
                )
        );

        exerciseDataAccessObject.saveExercise(
                "Ethan",
                createExercise(
                        "Inside",
                        startDate
                )
        );

        exerciseDataAccessObject.saveExercise(
                "Ethan",
                createExercise(
                        "After",
                        endDate.plusDays(1)
                )
        );

        final List<ExerciseEntry> result =
                trackingDataAccessObject.getExercises(
                        "Ethan",
                        startDate,
                        endDate
                );

        assertEquals(
                1,
                result.size()
        );

        assertEquals(
                "Inside",
                result.get(0).getExerciseName()
        );
    }

    @Test
    void getExercisesReturnsEmptyListWhenNoneExistTest() {
        final LocalDate startDate =
                LocalDate.of(
                        2026,
                        8,
                        10
                );

        final List<ExerciseEntry> result =
                trackingDataAccessObject.getExercises(
                        "Ethan",
                        startDate,
                        startDate.plusDays(6)
                );

        assertTrue(
                result.isEmpty()
        );
    }

    @Test
    void mealResultListIsUnmodifiableTest() {
        final LocalDate date =
                LocalDate.of(
                        2026,
                        8,
                        10
                );

        mealDataAccessObject.saveMeal(
                "Ethan",
                createMeal(
                        "Apple",
                        MealType.BREAKFAST,
                        date
                )
        );

        final List<MealEntry> result =
                trackingDataAccessObject.getMeals(
                        "Ethan",
                        date,
                        date
                );

        assertThrows(
                UnsupportedOperationException.class,
                () -> result.add(
                        createMeal(
                                "Chicken",
                                MealType.LUNCH,
                                date
                        )
                )
        );
    }

    @Test
    void exerciseResultListIsUnmodifiableTest() {
        final LocalDate date =
                LocalDate.of(
                        2026,
                        8,
                        10
                );

        exerciseDataAccessObject.saveExercise(
                "Ethan",
                createExercise(
                        "Running",
                        date
                )
        );

        final List<ExerciseEntry> result =
                trackingDataAccessObject.getExercises(
                        "Ethan",
                        date,
                        date
                );

        assertThrows(
                UnsupportedOperationException.class,
                () -> result.add(
                        createExercise(
                                "Cycling",
                                date
                        )
                )
        );
    }

    private static MealEntry createMeal(
            String name,
            MealType mealType,
            LocalDate date) {

        final FoodItem foodItem =
                new FoodItem(
                        name,
                        100.0,
                        100.0,
                        10.0,
                        20.0,
                        5.0,
                        2.0,
                        5.0,
                        50.0
                );

        return new MealEntry(
                foodItem,
                mealType,
                1.0,
                date
        );
    }

    private static ExerciseEntry createExercise(
            String name,
            LocalDate date) {

        return new ExerciseEntry(
                name,
                30,
                250.0,
                date
        );
    }
}
