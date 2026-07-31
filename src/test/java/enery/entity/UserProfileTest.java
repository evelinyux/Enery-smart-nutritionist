package enery.entity;

import java.time.LocalDate;
import java.util.List;

import enery.entity.user.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for UserProfile.
 */
class UserProfileTest {

    private static final double TOLERANCE = 0.001;

    @Test
    void createsProfileWithExpectedValues() {
        final UserProfile profile = new UserProfile(
                20,
                173.0,
                70.0,
                Sex.FEMALE,
                ActivityLevel.MODERATELY_ACTIVE,
                HealthGoal.MAINTAIN_WEIGHT,
                List.of("Vegetarian")
        );

        assertEquals(
                20,
                profile.getAge()
        );

        assertEquals(
                173.0,
                profile.getHeightInCentimetres(),
                TOLERANCE
        );

        assertEquals(
                70.0,
                profile.getWeightInKilograms(),
                TOLERANCE
        );

        assertEquals(
                Sex.FEMALE,
                profile.getSex()
        );

        assertEquals(
                ActivityLevel.MODERATELY_ACTIVE,
                profile.getActivityLevel()
        );

        assertEquals(
                HealthGoal.MAINTAIN_WEIGHT,
                profile.getHealthGoal()
        );

        assertTrue(
                profile.getDietaryRestrictions()
                        .contains("Vegetarian")
        );

        assertNull(
                profile.getActivePlan()
        );
    }

    @Test
    void updatesProfileValues() {
        final UserProfile profile = new UserProfile(
                20,
                173.0,
                70.0,
                Sex.UNKNOWN,
                ActivityLevel.SEDENTARY,
                HealthGoal.LOSE_WEIGHT,
                List.of()
        );

        final WeightGoalPlan plan =
                new WeightGoalPlan(
                        LocalDate.of(2026, 7, 30),
                        70,
                        68.0,
                        65.0,
                        HealthGoal.LOSE_WEIGHT,
                        2000.0
                );

        profile.setAge(21);

        profile.setHeightInCentimetres(
                174.0);

        profile.setWeightInKilograms(
                68.0);

        profile.setSex(
                Sex.MALE);

        profile.setActivityLevel(
                ActivityLevel.VERY_ACTIVE);

        profile.setHealthGoal(
                HealthGoal.LOSE_WEIGHT);

        profile.setActivePlan(
                plan);

        profile.addDietaryRestriction(
                "Peanut allergy");

        assertEquals(
                21,
                profile.getAge()
        );

        assertEquals(
                174.0,
                profile.getHeightInCentimetres(),
                TOLERANCE
        );

        assertEquals(
                68.0,
                profile.getWeightInKilograms(),
                TOLERANCE
        );

        assertEquals(
                Sex.MALE,
                profile.getSex()
        );

        assertEquals(
                ActivityLevel.VERY_ACTIVE,
                profile.getActivityLevel()
        );

        assertEquals(
                HealthGoal.LOSE_WEIGHT,
                profile.getHealthGoal()
        );

        assertSame(
                plan,
                profile.getActivePlan()
        );

        assertEquals(
                2000.0,
                profile.getActivePlan()
                        .getInitialDailyCalorieGoal(),
                TOLERANCE
        );

        assertEquals(
                2000.0,
                profile.getActivePlan()
                        .getCurrentDailyCalorieGoal(),
                TOLERANCE
        );

        assertTrue(
                profile.getDietaryRestrictions()
                        .contains("Peanut allergy")
        );
    }

    @Test
    void removesDietaryRestriction() {
        final UserProfile profile = new UserProfile(
                20,
                173.0,
                70.0,
                Sex.UNKNOWN,
                ActivityLevel.SEDENTARY,
                HealthGoal.MAINTAIN_WEIGHT,
                List.of(
                        "Vegetarian",
                        "Halal"
                )
        );

        profile.removeDietaryRestriction(
                "Vegetarian");

        assertFalse(
                profile.getDietaryRestrictions()
                        .contains("Vegetarian")
        );

        assertTrue(
                profile.getDietaryRestrictions()
                        .contains("Halal")
        );
    }

    @Test
    void dietaryRestrictionListCannotBeChangedDirectly() {
        final UserProfile profile = new UserProfile(
                20,
                173.0,
                70.0,
                Sex.UNKNOWN,
                ActivityLevel.SEDENTARY,
                HealthGoal.MAINTAIN_WEIGHT,
                List.of("Vegetarian")
        );

        assertThrows(
                UnsupportedOperationException.class,
                () -> profile
                        .getDietaryRestrictions()
                        .add("Vegan")
        );
    }
}
