package enery.use_case.health_assessment;

import enery.data_access.HealthAssessment_database.NutrientStandards;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NutrientComparerTest {

    private NutrientComparer comparer;
    private NutrientStandards standards;

    @BeforeEach
    void setUp() {
        comparer =
                new NutrientComparer();

        standards =
                new NutrientStandards();
    }

    @Test
    void optimalNutrientsScoreOneHundredTest() {
        final NutrientComparer.DietaryEvaluationSummary summary =
                comparer.evaluateDiet(
                        50.0,
                        130.0,
                        10.0,
                        1,
                        standards
                );

        assertEquals(
                100,
                summary.averageScore
        );

        assertEquals(
                3,
                summary.insights.size()
        );

        assertTrue(
                summary.actionableItems.isEmpty()
        );
    }

    @Test
    void slightlyHighNutrientsProduceAdviceTest() {
        final NutrientComparer.DietaryEvaluationSummary summary =
                comparer.evaluateDiet(
                        60.0,
                        156.0,
                        12.0,
                        1,
                        standards
                );

        assertEquals(
                85,
                summary.averageScore
        );

        assertEquals(
                3,
                summary.insights.size()
        );

        assertEquals(
                3,
                summary.actionableItems.size()
        );

        assertTrue(
                summary.insights
                        .get(0)
                        .contains("slightly high")
        );
    }

    @Test
    void slightlyLowNutrientsProduceAdviceTest() {
        final NutrientComparer.DietaryEvaluationSummary summary =
                comparer.evaluateDiet(
                        40.0,
                        104.0,
                        8.0,
                        1,
                        standards
                );

        assertEquals(
                85,
                summary.averageScore
        );

        assertEquals(
                3,
                summary.actionableItems.size()
        );

        assertTrue(
                summary.insights
                        .get(0)
                        .contains("slightly low")
        );
    }

    @Test
    void moderatelyHighNutrientsProduceAdviceTest() {
        final NutrientComparer.DietaryEvaluationSummary summary =
                comparer.evaluateDiet(
                        70.0,
                        182.0,
                        14.0,
                        1,
                        standards
                );

        assertEquals(
                60,
                summary.averageScore
        );

        assertTrue(
                summary.insights
                        .get(0)
                        .contains("moderately high")
        );

        assertTrue(
                summary.actionableItems
                        .get(0)
                        .contains("cut back")
        );
    }

    @Test
    void moderatelyLowNutrientsProduceAdviceTest() {
        final NutrientComparer.DietaryEvaluationSummary summary =
                comparer.evaluateDiet(
                        30.0,
                        78.0,
                        6.0,
                        1,
                        standards
                );

        assertEquals(
                60,
                summary.averageScore
        );

        assertTrue(
                summary.insights
                        .get(0)
                        .contains("moderately low")
        );

        assertTrue(
                summary.actionableItems
                        .get(0)
                        .contains("Aim to add")
        );
    }

    @Test
    void severelyHighNutrientsLowerScoreTest() {
        final NutrientComparer.DietaryEvaluationSummary summary =
                comparer.evaluateDiet(
                        100.0,
                        260.0,
                        20.0,
                        1,
                        standards
                );

        assertEquals(
                15,
                summary.averageScore
        );

        assertEquals(
                3,
                summary.insights.size()
        );

        assertEquals(
                3,
                summary.actionableItems.size()
        );

        assertTrue(
                summary.insights
                        .get(0)
                        .contains("excessively high")
        );
    }

    @Test
    void severelyLowNutrientsLowerScoreTest() {
        final NutrientComparer.DietaryEvaluationSummary summary =
                comparer.evaluateDiet(
                        10.0,
                        26.0,
                        2.0,
                        1,
                        standards
                );

        assertEquals(
                25,
                summary.averageScore
        );

        assertEquals(
                3,
                summary.actionableItems.size()
        );

        assertTrue(
                summary.insights
                        .get(0)
                        .contains("severely deficient")
        );
    }

    @Test
    void mixedNutrientsProduceInsightsAndActionsTest() {
        final NutrientComparer.DietaryEvaluationSummary summary =
                comparer.evaluateDiet(
                        60.0,
                        100.0,
                        15.0,
                        1,
                        standards
                );

        assertFalse(
                summary.insights.isEmpty()
        );

        assertFalse(
                summary.actionableItems.isEmpty()
        );
    }
}
