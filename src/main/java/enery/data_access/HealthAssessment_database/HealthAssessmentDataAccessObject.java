package enery.data_access.HealthAssessment_database;

import enery.use_case.health_assessment.HealthAssessmentDataAccessInterface;

public class HealthAssessmentDataAccessObject {
    private final NutrientStandards standards = new NutrientStandards();

    public NutrientStandards getNutrientStandards() {
        return standards;
    }
}
