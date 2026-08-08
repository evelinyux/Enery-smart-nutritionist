package enery.use_case.health_assessment;

import enery.data_access.HealthAssessment_database.NutrientStandards;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NutrientComparerTest {
    @Test
    void testEvaluateDiet_WeightedScoring() {
        NutrientComparer comparer = new NutrientComparer();
        NutrientStandards standards = new NutrientStandards();

        NutrientComparer.DietaryEvaluationSummary summary =
                comparer.evaluateDiet(50.0, 156.0, 15.0, 1, standards);

        assertEquals(88, summary.averageScore);
        assertFalse(summary.insights.isEmpty());
    }
}
