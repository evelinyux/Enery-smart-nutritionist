package enery.data_access.HealthAssessment_database;

import enery.use_case.health_assessment.HealthAssessmentDataAccessInterface;
import enery.use_case.tracking.TrackingDataAccessInterface;
import enery.use_case.tracking.TrackingOutputData;
import enery.use_case.tracking.TrackingPeriod;

import java.time.LocalDate;

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

    @Override
    public TrackingOutputData getTrackingData(String userId, LocalDate startDate, LocalDate endDate) {

        double totalCaloriesIn = 0.0;
        double totalCaloriesOut = 0.0;
        double totalProtein = 0.0;
        double totalCarbs = 0.0;
        double totalFat = 0.0;

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            totalCaloriesIn += TrackingOutputData.getCaloriesConsumed();
            totalCaloriesOut += TrackingOutputData.getCaloriesBurned();
            totalProtein += TrackingOutputData.getProteinInGrams();
            totalFat += TrackingOutputData.getFatInGrams();
            totalCarbs += TrackingOutputData.getCarbohydratesInGrams();
        }

        return new TrackingOutputData(
                TrackingPeriod.WEEKLY,
                startDate,
                endDate,
                totalCaloriesIn,
                totalCaloriesOut,
                0,
                totalProtein,
                totalCarbs,
                totalFat,
                0,
                0,
                0,
                0,
                0,
                0,
                new java.util.ArrayList<>()
        );
    }
}