package enery.use_case.health_assessment;

import enery.data_access.HealthAssessment_database.NutrientStandards;

public interface HealthAssessmentDataAccessInterface {
    double getProteinInGrams();
    double getCarbohydratesInGrams();
    double getFatInGrams();
    NutrientStandards getNutrientStandards();
}
