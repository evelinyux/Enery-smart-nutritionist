package enery.use_case.health_assessment;

import enery.entity.healthAssessment.AssessmentOverview;
import enery.entity.healthAssessment.DietaryPatternAssessment;
import enery.data_access.HealthAssessment_database.NutrientStandards;
import enery.use_case.tracking.TrackingOutputData;
import enery.use_case.tracking.TrackingPeriod;

import java.util.UUID;

public class HealthAssessmentInteractor implements HealthAssessmentInputBoundary {

    private final HealthAssessmentOutputBoundary outputBoundary;

    private final HealthAssessmentDataAccessInterface healthDataAccess;

    public HealthAssessmentInteractor(HealthAssessmentOutputBoundary outputBoundary,
                                      HealthAssessmentDataAccessInterface HealthAssessmentDataAccessInterface) {
        this.outputBoundary = outputBoundary;
        this.healthDataAccess = HealthAssessmentDataAccessInterface;
    }

    @Override
    public void execute(HealthAssessmentInputData inputData) {
        try {
            String assessmentId = UUID.randomUUID().toString();
            String userId = inputData.getUserId();
            java.time.LocalDate endDate = java.time.LocalDate.now();
            java.time.LocalDate startDate = endDate.minusDays(6);
            TrackingOutputData trackingData = healthDataAccess.getTrackingData(userId, startDate, endDate);

            if (trackingData == null) {
                outputBoundary.prepareFailView("No meal records found in the database for the past 7 days. Please log data first.");
                return;
            }

            AssessmentOverview overview = new AssessmentOverview(assessmentId, userId);
            DietaryPatternAssessment dietaryAssessment = new DietaryPatternAssessment(assessmentId, userId);

            NutrientStandards nutrientStandards = healthDataAccess.getNutrientStandards();
            NutrientComparer comparer = new NutrientComparer();

            double totalProtein = trackingData.getProteinInGrams();
            double totalCarbs = trackingData.getCarbohydratesInGrams();
            double totalFat = trackingData.getFatInGrams();

            int days = 7;

            NutrientComparer.DietaryEvaluationSummary dietSummary =
                    comparer.evaluateDiet(totalProtein, totalCarbs, totalFat, days, nutrientStandards);

            dietaryAssessment.setDietScore(dietSummary.averageScore);
            for (String insight : dietSummary.insights) {
                dietaryAssessment.addDietInsight(insight);
            }
            for (String item : dietSummary.actionableItems) {
                dietaryAssessment.addDietActionableItem(item);
            }

            // overview
            overview.setDietaryPatternAssessment(dietaryAssessment);

             HealthAssessmentOutputData outputData = new HealthAssessmentOutputData(overview.getAssessmentId(),
                     overview.getOverallScore(),
                     overview.getSeverity(),
                     dietaryAssessment.getDietScore(),
                     dietaryAssessment.getDietInsights(),
                     dietaryAssessment.getDietActionableItems());
             outputBoundary.prepareSuccessView(outputData);
        } catch (Exception e) {
            outputBoundary.prepareFailView("Fail to assess: " + e.getMessage());
        }
    }
}