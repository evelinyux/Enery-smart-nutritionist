package enery.entity.strategy;

import enery.entity.user.ActivityLevel;
import enery.entity.user.HealthGoal;
import enery.entity.user.Sex;
import enery.entity.user.UserProfile;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DailyCalorieGoalCalculatorTest {

    @Test
    void femaleMaintenanceCalculationTest() {
        UserProfile profile =
                new UserProfile(
                        25,
                        170.0,
                        70.0,
                        Sex.FEMALE,
                        ActivityLevel.MODERATELY_ACTIVE,
                        HealthGoal.MAINTAIN_WEIGHT,
                        List.of()
                );

        DailyCalorieGoalCalculator calculator =
                new DailyCalorieGoalCalculator();

        double result =
                calculator.calculate(profile);

        // BMR:
        // 10*70 + 6.25*170 - 5*25 - 161
        // = 1476.5
        //
        // Moderately active:
        // 1476.5 * 1.55 = 2288.575

        assertEquals(
                2288.575,
                result,
                0.001);
    }

    @Test
    void femaleWeightLossCalculationTest() {
        UserProfile profile =
                new UserProfile(
                        25,
                        170.0,
                        70.0,
                        Sex.FEMALE,
                        ActivityLevel.MODERATELY_ACTIVE,
                        HealthGoal.LOSE_WEIGHT,
                        List.of()
                );

        DailyCalorieGoalCalculator calculator =
                new DailyCalorieGoalCalculator();

        double result =
                calculator.calculate(profile);

        // maintenance = 2288.575
        // weight loss strategy = 90%

        assertEquals(
                2059.7175,
                result,
                0.001);
    }

    @Test
    void femaleWeightGainCalculationTest() {
        UserProfile profile =
                new UserProfile(
                        25,
                        170.0,
                        70.0,
                        Sex.FEMALE,
                        ActivityLevel.MODERATELY_ACTIVE,
                        HealthGoal.GAIN_WEIGHT,
                        List.of()
                );

        DailyCalorieGoalCalculator calculator =
                new DailyCalorieGoalCalculator();

        double result =
                calculator.calculate(profile);

        // maintenance = 2288.575
        // weight gain strategy = 110%

        assertEquals(
                2517.4325,
                result,
                0.001);
    }

    @Test
    void maleSedentaryCalculationTest() {
        UserProfile profile =
                new UserProfile(
                        30,
                        180.0,
                        80.0,
                        Sex.MALE,
                        ActivityLevel.SEDENTARY,
                        HealthGoal.MAINTAIN_WEIGHT,
                        List.of()
                );

        DailyCalorieGoalCalculator calculator =
                new DailyCalorieGoalCalculator();

        double result =
                calculator.calculate(profile);

        // BMR:
        // 10*80 + 6.25*180 - 5*30 + 5
        // = 1780
        //
        // Sedentary:
        // 1780 * 1.2 = 2136

        assertEquals(
                2136.0,
                result,
                0.001);
    }

    @Test
    void unknownSexCalculationTest() {
        UserProfile profile =
                new UserProfile(
                        25,
                        170.0,
                        70.0,
                        Sex.UNKNOWN,
                        ActivityLevel.MODERATELY_ACTIVE,
                        HealthGoal.MAINTAIN_WEIGHT,
                        List.of()
                );

        DailyCalorieGoalCalculator calculator =
                new DailyCalorieGoalCalculator();

        double result =
                calculator.calculate(profile);

        // UNKNOWN adjustment:
        // (5 + -161) / 2 = -78
        //
        // BMR:
        // 700 + 1062.5 - 125 - 78
        // = 1559.5
        //
        // 1559.5 * 1.55 = 2417.225

        assertEquals(
                2417.225,
                result,
                0.001);
    }
}