package enery.data_access.in_memory;

import java.time.LocalDate;
import java.util.List;

import enery.entity.meal.FoodItem;
import enery.entity.meal.MealEntry;
import enery.entity.meal.MealType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryMealDataAccessObjectTest {

    private InMemoryUserDataAccessObject userDataAccessObject;
    private InMemoryMealDataAccessObject mealDataAccessObject;

    @BeforeEach
    void setUp() {
        userDataAccessObject =
                new InMemoryUserDataAccessObject();

        mealDataAccessObject =
                new InMemoryMealDataAccessObject(
                        userDataAccessObject
                );
    }

    @Test
    void saveAndGetMealTest() {
        final LocalDate date =
                LocalDate.of(
                        2026,
                        8,
                        10
                );

        final MealEntry entry =
                createMealEntry(
                        "Apple",
                        MealType.BREAKFAST,
                        date
                );

        mealDataAccessObject.saveMeal(
                "Ethan",
                entry
        );

        final List<MealEntry> meals =
                mealDataAccessObject.getMeals(
                        "Ethan",
                        date
                );

        assertEquals(
                1,
                meals.size()
        );

        assertEquals(
                "Apple",
                meals
                        .get(0)
                        .getFoodItem()
                        .getName()
        );
    }

    @Test
    void differentUsersAreSeparatedTest() {
        final LocalDate date =
                LocalDate.now();

        final MealEntry ethanMeal =
                createMealEntry(
                        "Apple",
                        MealType.BREAKFAST,
                        date
                );

        final MealEntry otherMeal =
                createMealEntry(
                        "Chicken",
                        MealType.LUNCH,
                        date
                );

        mealDataAccessObject.saveMeal(
                "Ethan",
                ethanMeal
        );

        mealDataAccessObject.saveMeal(
                "Other",
                otherMeal
        );

        assertEquals(
                1,
                mealDataAccessObject
                        .getMeals(
                                "Ethan",
                                date
                        )
                        .size()
        );

        assertEquals(
                "Apple",
                mealDataAccessObject
                        .getMeals(
                                "Ethan",
                                date
                        )
                        .get(0)
                        .getFoodItem()
                        .getName()
        );
    }

    @Test
    void filtersMealsByDateTest() {
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

        mealDataAccessObject.saveMeal(
                "Ethan",
                createMealEntry(
                        "Apple",
                        MealType.BREAKFAST,
                        firstDate
                )
        );

        mealDataAccessObject.saveMeal(
                "Ethan",
                createMealEntry(
                        "Chicken",
                        MealType.LUNCH,
                        secondDate
                )
        );

        final List<MealEntry> meals =
                mealDataAccessObject.getMeals(
                        "Ethan",
                        secondDate
                );

        assertEquals(
                1,
                meals.size()
        );

        assertEquals(
                "Chicken",
                meals
                        .get(0)
                        .getFoodItem()
                        .getName()
        );
    }

    @Test
    void unknownUserReturnsEmptyListTest() {
        final List<MealEntry> meals =
                mealDataAccessObject.getMeals(
                        "unknown",
                        LocalDate.now()
                );

        assertTrue(
                meals.isEmpty()
        );
    }

    @Test
    void hasMealReturnsTrueTest() {
        final LocalDate date =
                LocalDate.now();

        mealDataAccessObject.saveMeal(
                "Ethan",
                createMealEntry(
                        "Apple",
                        MealType.BREAKFAST,
                        date
                )
        );

        assertTrue(
                mealDataAccessObject.hasMeal(
                        "Ethan",
                        date,
                        MealType.BREAKFAST
                )
        );
    }

    @Test
    void hasMealReturnsFalseForWrongMealTypeTest() {
        final LocalDate date =
                LocalDate.now();

        mealDataAccessObject.saveMeal(
                "Ethan",
                createMealEntry(
                        "Apple",
                        MealType.BREAKFAST,
                        date
                )
        );

        assertFalse(
                mealDataAccessObject.hasMeal(
                        "Ethan",
                        date,
                        MealType.DINNER
                )
        );
    }

    @Test
    void hasMealReturnsFalseForWrongDateTest() {
        final LocalDate date =
                LocalDate.now();

        mealDataAccessObject.saveMeal(
                "Ethan",
                createMealEntry(
                        "Apple",
                        MealType.BREAKFAST,
                        date
                )
        );

        assertFalse(
                mealDataAccessObject.hasMeal(
                        "Ethan",
                        date.plusDays(1),
                        MealType.BREAKFAST
                )
        );
    }

    @Test
    void returnedListCannotBeModifiedTest() {
        final LocalDate date =
                LocalDate.now();

        mealDataAccessObject.saveMeal(
                "Ethan",
                createMealEntry(
                        "Apple",
                        MealType.BREAKFAST,
                        date
                )
        );

        final List<MealEntry> meals =
                mealDataAccessObject.getMeals(
                        "Ethan",
                        date
                );

        assertThrows(
                UnsupportedOperationException.class,
                () -> meals.add(
                        createMealEntry(
                                "Chicken",
                                MealType.LUNCH,
                                date
                        )
                )
        );
    }

    private static MealEntry createMealEntry(
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
}
