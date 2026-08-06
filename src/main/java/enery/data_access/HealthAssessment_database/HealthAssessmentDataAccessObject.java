package enery.data_access.HealthAssessment_database;

import enery.use_case.health_assessment.HealthAssessmentDataAccessInterface;

public class HealthAssessmentDataAccessObject implements HealthAssessmentDataAccessInterface {

    private final NutrientStandards standards = new NutrientStandards();

    @Override
    public double getProteinInGrams() {
        return standards.getProteinInGrams();
    }

    @Override
    public double getCarbohydratesInGrams() {
        return standards.getCarbohydratesInGrams();
    }

    @Override
    public double getFatInGrams() {
        return standards.getFatInGrams();
    }

    @Override
    public NutrientStandards getNutrientStandards() {
        return standards;
    }
}