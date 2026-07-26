package enery.entity;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserProfileTest {

    @Test
    void createsProfileWithExpectedValues() {
        final UserProfile profile = new UserProfile(
                20,
                173.0,
                70.0,
                ActivityLevel.MODERATELY_ACTIVE,
                HealthGoal.MAINTAIN_WEIGHT,
                List.of("Vegetarian")
        );

        assertEquals(20, profile.getAge());
        assertEquals(173.0, profile.getHeightInCentimetres());
        assertEquals(70.0, profile.getWeightInKilograms());
        assertEquals(
                ActivityLevel.MODERATELY_ACTIVE,
                profile.getActivityLevel()
        );
        assertEquals(
                HealthGoal.MAINTAIN_WEIGHT,
                profile.getHealthGoal()
        );
        assertTrue(
                profile.getDietaryRestrictions().contains("Vegetarian")
        );
    }

    @Test
    void updatesProfileValues() {
        final UserProfile profile = new UserProfile(
                20,
                173.0,
                70.0,
                ActivityLevel.SEDENTARY,
                HealthGoal.LOSE_WEIGHT,
                List.of()
        );

        profile.setAge(21);
        profile.setHeightInCentimetres(174.0);
        profile.setWeightInKilograms(68.5);
        profile.setActivityLevel(ActivityLevel.VERY_ACTIVE);
        profile.setHealthGoal(HealthGoal.MAINTAIN_WEIGHT);
        profile.addDietaryRestriction("Peanut allergy");

        assertEquals(21, profile.getAge());
        assertEquals(174.0, profile.getHeightInCentimetres());
        assertEquals(68.5, profile.getWeightInKilograms());
        assertEquals(ActivityLevel.VERY_ACTIVE, profile.getActivityLevel());
        assertEquals(HealthGoal.MAINTAIN_WEIGHT, profile.getHealthGoal());
        assertTrue(
                profile.getDietaryRestrictions().contains("Peanut allergy")
        );
    }

    @Test
    void dietaryRestrictionListCannotBeChangedDirectly() {
        final UserProfile profile = new UserProfile(
                20,
                173.0,
                70.0,
                ActivityLevel.SEDENTARY,
                HealthGoal.MAINTAIN_WEIGHT,
                List.of("Vegetarian")
        );

        assertThrows(
                UnsupportedOperationException.class,
                () -> profile.getDietaryRestrictions().add("Vegan")
        );
    }
}
