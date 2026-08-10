package enery.data_access.in_memory;

import java.time.LocalDate;
import java.util.List;

import enery.entity.meal.FoodItem;
import enery.entity.meal.MealEntry;
import enery.entity.meal.MealType;
import enery.entity.user.ActivityLevel;
import enery.entity.user.CommonUser;
import enery.entity.user.HealthGoal;
import enery.entity.user.User;
import enery.entity.user.UserProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryUserDataAccessObjectTest {

    private InMemoryUserDataAccessObject dataAccessObject;

    @BeforeEach
    void setUp() {
        dataAccessObject =
                new InMemoryUserDataAccessObject();
    }

    @Test
    void savedUserExistsAndCanBeRetrievedTest() {
        final User user =
                new CommonUser(
                        "Ethan",
                        "password"
                );

        assertFalse(
                dataAccessObject.existsByName(
                        "Ethan"
                )
        );

        dataAccessObject.save(user);

        assertTrue(
                dataAccessObject.existsByName(
                        "Ethan"
                )
        );

        assertSame(
                user,
                dataAccessObject.get(
                        "Ethan"
                )
        );
    }

    @Test
    void unknownUserReturnsNullTest() {
        assertNull(
                dataAccessObject.get(
                        "unknown"
                )
        );
    }

    @Test
    void currentUsernameCanBeStoredTest() {
        dataAccessObject.setCurrentUsername(
                "Ethan"
        );

        assertEquals(
                "Ethan",
                dataAccessObject.getCurrentUsername()
        );
    }

    @Test
    void passwordChangeUpdatesStoredUserTest() {
        final User user =
                new CommonUser(
                        "Ethan",
                        "oldPassword"
                );

        dataAccessObject.save(user);

        user.setPassword(
                "newPassword"
        );

        dataAccessObject.changePassword(
                user
        );

        assertEquals(
                "newPassword",
                dataAccessObject
                        .get("Ethan")
                        .getPassword()
        );
    }

    @Test
    void getUserProfileReturnsProfileTest() {
        final CommonUser user =
                new CommonUser(
                        "Ethan",
                        "password"
                );

        final UserProfile profile =
                new UserProfile(
                        20,
                        173.0,
                        70.0,
                        ActivityLevel.MODERATELY_ACTIVE,
                        HealthGoal.MAINTAIN_WEIGHT,
                        List.of()
                );

        user.setUserProfile(
                profile
        );

        dataAccessObject.save(
                user
        );

        assertSame(
                profile,
                dataAccessObject.getUserProfile(
                        "Ethan"
                )
        );
    }

    @Test
    void getUserProfileReturnsNullForMissingUserTest() {
        assertNull(
                dataAccessObject.getUserProfile(
                        "unknown"
                )
        );
    }

    @Test
    void saveMealAddsMealToUsersDailyLogTest() {
        final CommonUser user =
                new CommonUser(
                        "Ethan",
                        "password"
                );

        dataAccessObject.save(
                user
        );

        final LocalDate date =
                LocalDate.of(
                        2026,
                        8,
                        10
                );

        final MealEntry meal =
                createMeal(
                        "Apple",
                        date
                );

        dataAccessObject.saveMeal(
                "Ethan",
                meal
        );

        assertEquals(
                1,
                dataAccessObject
                        .getMeals(
                                "Ethan",
                                date
                        )
                        .size()
        );

        assertSame(
                meal,
                dataAccessObject
                        .getMeals(
                                "Ethan",
                                date
                        )
                        .get(0)
        );
    }

    @Test
    void saveMealDoesNothingForMissingUserTest() {
        final LocalDate date =
                LocalDate.now();

        dataAccessObject.saveMeal(
                "missing",
                createMeal(
                        "Apple",
                        date
                )
        );

        assertTrue(
                dataAccessObject
                        .getMeals(
                                "missing",
                                date
                        )
                        .isEmpty()
        );
    }

    @Test
    void saveMealDoesNothingForNullMealTest() {
        final CommonUser user =
                new CommonUser(
                        "Ethan",
                        "password"
                );

        dataAccessObject.save(user);

        dataAccessObject.saveMeal(
                "Ethan",
                null
        );

        assertTrue(
                dataAccessObject
                        .getMeals(
                                "Ethan",
                                LocalDate.now()
                        )
                        .isEmpty()
        );
    }

    @Test
    void missingUserMealsReturnsEmptyListTest() {
        assertTrue(
                dataAccessObject
                        .getMeals(
                                "missing",
                                LocalDate.now()
                        )
                        .isEmpty()
        );
    }

    private static MealEntry createMeal(
            String name,
            LocalDate date) {

        final FoodItem food =
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
                food,
                MealType.BREAKFAST,
                1.0,
                date
        );
    }
}
