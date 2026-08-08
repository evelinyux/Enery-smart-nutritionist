package enery.interface_adapter.profile;

import enery.entity.user.ActivityLevel;
import enery.entity.user.HealthGoal;
import enery.entity.user.Sex;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProfileStateTest {

    @Test
    void settersAndGettersTest() {
        ProfileState state =
                new ProfileState();

        state.setUsername("nicole");
        state.setAge(25);
        state.setHeightInCentimetres(170.0);
        state.setWeightInKilograms(70.0);
        state.setSex(Sex.FEMALE);

        state.setActivityLevel(
                ActivityLevel.MODERATELY_ACTIVE);

        state.setHealthGoal(
                HealthGoal.LOSE_WEIGHT);

        state.setTargetWeightInKilograms(65.0);
        state.setDurationDays(60);

        state.setStartDate("2026-08-08");
        state.setEndDate("2026-10-06");

        state.setDailyCalorieGoal(2000.0);

        state.setDietaryRestrictions(
                List.of("Vegetarian", "Halal"));

        state.setProfileError("error");
        state.setSuccessMessage("success");

        assertEquals("nicole",
                state.getUsername());

        assertEquals(25,
                state.getAge());

        assertEquals(170.0,
                state.getHeightInCentimetres(),
                0.001);

        assertEquals(70.0,
                state.getWeightInKilograms(),
                0.001);

        assertEquals(Sex.FEMALE,
                state.getSex());

        assertEquals(
                ActivityLevel.MODERATELY_ACTIVE,
                state.getActivityLevel());

        assertEquals(
                HealthGoal.LOSE_WEIGHT,
                state.getHealthGoal());

        assertEquals(65.0,
                state.getTargetWeightInKilograms(),
                0.001);

        assertEquals(60,
                state.getDurationDays());

        assertEquals(
                "2026-08-08",
                state.getStartDate());

        assertEquals(
                "2026-10-06",
                state.getEndDate());

        assertEquals(
                2000.0,
                state.getDailyCalorieGoal(),
                0.001);

        assertEquals(
                List.of("Vegetarian", "Halal"),
                state.getDietaryRestrictions());

        assertEquals(
                "error",
                state.getProfileError());

        assertEquals(
                "success",
                state.getSuccessMessage());
    }

    @Test
    void dietaryRestrictionsAreDefensivelyCopiedTest() {
        ProfileState state =
                new ProfileState();

        List<String> restrictions =
                new ArrayList<>();

        restrictions.add("Vegetarian");

        state.setDietaryRestrictions(
                restrictions);

        restrictions.add("Halal");

        assertEquals(
                List.of("Vegetarian"),
                state.getDietaryRestrictions());
    }

    @Test
    void dietaryRestrictionsCannotBeModifiedTest() {
        ProfileState state =
                new ProfileState();

        state.setDietaryRestrictions(
                List.of("Vegetarian"));

        assertThrows(
                UnsupportedOperationException.class,
                () -> state
                        .getDietaryRestrictions()
                        .add("Halal"));
    }
}