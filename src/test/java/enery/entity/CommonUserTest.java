package enery.entity;

import java.util.List;

import enery.entity.user.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CommonUserTest {

    @Test
    void storesProfileAndNutritionGoal() {
        final CommonUser user = new CommonUser(
                "Paul",
                "password"
        );

        assertNull(user.getUserProfile());
        assertNull(user.getNutritionGoal());

        final UserProfile profile = new UserProfile(
                20,
                173.0,
                70.0,
                ActivityLevel.MODERATELY_ACTIVE,
                HealthGoal.MAINTAIN_WEIGHT,
                List.of("Vegetarian")
        );

        final NutritionGoal goal = new NutritionGoal(
                2200.0
        );

        user.setUserProfile(profile);
        user.setNutritionGoal(goal);

        assertEquals(profile, user.getUserProfile());
        assertEquals(goal, user.getNutritionGoal());
    }
}
