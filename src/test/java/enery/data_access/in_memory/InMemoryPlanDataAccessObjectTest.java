package enery.data_access.in_memory;

import java.time.LocalDate;
import java.util.List;

import enery.entity.meal.FoodItem;
import enery.entity.meal.MealEntry;
import enery.entity.meal.MealType;
import enery.entity.user.ActivityLevel;
import enery.entity.user.CommonUser;
import enery.entity.user.HealthGoal;
import enery.entity.user.UserProfile;
import enery.entity.user.WeightGoalPlan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class InMemoryPlanDataAccessObjectTest {

    private static final double DELTA = 0.0001;

    private InMemoryUserDataAccessObject userDataAccess;
    private InMemoryMealDataAccessObject mealDataAccess;
    private InMemoryPlanDataAccessObject planDataAccess;

    private UserProfile profile;

    @BeforeEach
    void setUp() {
        userDataAccess =
                new InMemoryUserDataAccessObject();

        mealDataAccess =
                new InMemoryMealDataAccessObject(
                        userDataAccess
                );

        planDataAccess =
                new InMemoryPlanDataAccessObject(
                        userDataAccess,
                        mealDataAccess
                );

        final CommonUser user =
                new CommonUser(
                        "Ethan",
                        "password"
                );

        profile =
                new UserProfile(
                        20,
                        173.0,
                        70.0,
                        ActivityLevel.MODERATELY_ACTIVE,
                        HealthGoal.MAINTAIN_WEIGHT,
                        List.of()
                );

        final WeightGoalPlan plan =
                new WeightGoalPlan(
                        LocalDate.now()
                                .minusDays(2),
                        14,
                        70.0,
                        70.0,
                        HealthGoal.MAINTAIN_WEIGHT,
                        2000.0
                );

        profile.setActivePlan(
                plan
        );

        user.setUserProfile(
                profile
        );

        userDataAccess.save(
                user
        );
    }

    @Test
    void getUserProfileTest() {
        assertSame(
                profile,
                planDataAccess.getUserProfile(
                        "Ethan"
                )
        );
    }

    @Test
    void dailyCalorieGoalTest() {
        assertEquals(
                2000.0,
                planDataAccess.getDailyCalorieGoal(
                        "Ethan"
                ),
                DELTA
        );
    }

    @Test
    void todaysCaloriesAreSummedTest() {
        addMeal(
                LocalDate.now(),
                100.0,
                2.0
        );

        addMeal(
                LocalDate.now(),
                200.0,
                1.0
        );

        assertEquals(
                400.0,
                planDataAccess.getTodaysCalories(
                        "Ethan"
                ),
                DELTA
        );
    }

    @Test
    void todaysProteinIsSummedTest() {
        addMeal(
                LocalDate.now(),
                100.0,
                2.0
        );

        addMeal(
                LocalDate.now(),
                200.0,
                1.0
        );

        assertEquals(
                30.0,
                planDataAccess.getTodaysProtein(
                        "Ethan"
                ),
                DELTA
        );
    }

    @Test
    void todaysCarbohydratesAreSummedTest() {
        addMeal(
                LocalDate.now(),
                100.0,
                2.0
        );

        addMeal(
                LocalDate.now(),
                200.0,
                1.0
        );

        assertEquals(
                60.0,
                planDataAccess
                        .getTodaysCarbohydrates(
                                "Ethan"
                        ),
                DELTA
        );
    }

    @Test
    void todaysFatsAreSummedTest() {
        addMeal(
                LocalDate.now(),
                100.0,
                2.0
        );

        addMeal(
                LocalDate.now(),
                200.0,
                1.0
        );

        assertEquals(
                15.0,
                planDataAccess.getTodaysFats(
                        "Ethan"
                ),
                DELTA
        );
    }

    @Test
    void totalCalorieGoalUsesPlanDurationTest() {
        assertEquals(
                28000.0,
                planDataAccess.getTotalCalorieGoal(
                        "Ethan"
                ),
                DELTA
        );
    }

    @Test
    void totalCaloriesConsumedIncludesMultipleDatesTest() {
        final LocalDate today =
                LocalDate.now();

        addMeal(
                today.minusDays(2),
                100.0,
                1.0
        );

        addMeal(
                today.minusDays(1),
                200.0,
                1.0
        );

        addMeal(
                today,
                300.0,
                1.0
        );

        assertEquals(
                600.0,
                planDataAccess
                        .getTotalCaloriesConsumed(
                                "Ethan"
                        ),
                DELTA
        );
    }

    @Test
    void daysSoFarIncludesStartAndTodayTest() {
        assertEquals(
                3,
                planDataAccess.getDaysSoFar(
                        "Ethan"
                )
        );
    }

    @Test
    void mealsFromOtherDaysAreNotIncludedInTodayTest() {
        addMeal(
                LocalDate.now()
                        .minusDays(1),
                500.0,
                1.0
        );

        assertEquals(
                0.0,
                planDataAccess.getTodaysCalories(
                        "Ethan"
                ),
                DELTA
        );
    }

    private void addMeal(
            LocalDate date,
            double calories,
            double servings) {

        final FoodItem food =
                new FoodItem(
                        "Test Food",
                        100.0,
                        calories,
                        10.0,
                        20.0,
                        5.0,
                        2.0,
                        5.0,
                        50.0
                );

        final MealEntry entry =
                new MealEntry(
                        food,
                        MealType.LUNCH,
                        servings,
                        date
                );

        mealDataAccess.saveMeal(
                "Ethan",
                entry
        );
    }
}
