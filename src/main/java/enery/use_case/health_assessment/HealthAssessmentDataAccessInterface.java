package enery.use_case.health_assessment;

import enery.data_access.HealthAssessment_database.NutrientStandards;
import enery.use_case.tracking.TrackingOutputData;
import java.time.LocalDate;

public interface HealthAssessmentDataAccessInterface {
    double getProteinInGrams();
    double getCarbohydratesInGrams();
    double getFatInGrams();
    NutrientStandards getNutrientStandards();
    TrackingOutputData getTrackingData(String userId, LocalDate startDate, LocalDate endDate);
}
