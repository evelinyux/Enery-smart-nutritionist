package enery.use_case.profile;

import enery.entity.user.ActivityLevel;
import enery.entity.user.HealthGoal;
import enery.entity.user.Sex;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProfileInputDataTest {

    @Test
    void constructorAndGettersTest() {
        List<String> restrictions =
                new ArrayList<>(
                        List.of("Vegetarian", "Halal"));

        ProfileInputData inputData =
                new ProfileInputData(
                        25,
                        170.0,
                        70.0,
                        65.0,
                        60,
                        Sex.FEMALE,
                        ActivityLevel.MODERATELY_ACTIVE,
                        HealthGoal.LOSE_WEIGHT,
                        restrictions
                );

        assertEquals(25, inputData.getAge());
        assertEquals(
                170.0,
                inputData.getHeightInCentimetres(),
                0.001);
        assertEquals(
                70.0,
                inputData.getWeightInKilograms(),
                0.001);
        assertEquals(
                65.0,
                inputData.getTargetWeightInKilograms(),
                0.001);
        assertEquals(60, inputData.getDurationDays());

        assertEquals(
                Sex.FEMALE,
                inputData.getSex());

        assertEquals(
                ActivityLevel.MODERATELY_ACTIVE,
                inputData.getActivityLevel());

        assertEquals(
                HealthGoal.LOSE_WEIGHT,
                inputData.getHealthGoal());

        assertEquals(
                List.of("Vegetarian", "Halal"),
                inputData.getDietaryRestrictions());
    }

    @Test
    void dietaryRestrictionsAreDefensivelyCopiedTest() {
        List<String> restrictions =
                new ArrayList<>(
                        List.of("Vegetarian"));

        ProfileInputData inputData =
                new ProfileInputData(
                        25,
                        170.0,
                        70.0,
                        65.0,
                        60,
                        Sex.FEMALE,
                        ActivityLevel.MODERATELY_ACTIVE,
                        HealthGoal.LOSE_WEIGHT,
                        restrictions
                );

        restrictions.add("Halal");

        assertEquals(
                List.of("Vegetarian"),
                inputData.getDietaryRestrictions());
    }

    @Test
    void dietaryRestrictionsCannotBeModifiedTest() {
        ProfileInputData inputData =
                new ProfileInputData(
                        25,
                        170.0,
                        70.0,
                        65.0,
                        60,
                        Sex.FEMALE,
                        ActivityLevel.MODERATELY_ACTIVE,
                        HealthGoal.LOSE_WEIGHT,
                        List.of("Vegetarian")
                );

        assertThrows(
                UnsupportedOperationException.class,
                () -> inputData
                        .getDietaryRestrictions()
                        .add("Halal"));
    }
}