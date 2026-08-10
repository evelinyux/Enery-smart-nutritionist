package enery.use_case.profile;

import enery.entity.user.ActivityLevel;
import enery.entity.user.HealthGoal;
import enery.entity.user.Sex;
import enery.entity.user.UserProfile;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProfileOutputDataTest {

    @Test
    void constructorAndGettersTest() {
        UserProfile profile =
                new UserProfile(
                        25,
                        170.0,
                        70.0,
                        Sex.FEMALE,
                        ActivityLevel.MODERATELY_ACTIVE,
                        HealthGoal.MAINTAIN_WEIGHT,
                        List.of("Vegetarian")
                );

        ProfileOutputData outputData =
                new ProfileOutputData(
                        "nicole",
                        profile,
                        false);

        assertEquals(
                "nicole",
                outputData.getUsername());

        assertSame(
                profile,
                outputData.getUserProfile());

        assertFalse(
                outputData.isUseCaseFailed());
    }
}