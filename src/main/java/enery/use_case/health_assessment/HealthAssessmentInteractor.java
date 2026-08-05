package enery.use_case.health_assessment;

import enery.data_access.HealthAssessment_database.HealthAssessmentDataAccessObject;
import enery.entity.healthAssessment.AssessmentOverview;
import enery.entity.healthAssessment.DietaryPatternAssessment;
import enery.entity.healthAssessment.ExercisePatternAssessment;
import enery.data_access.HealthAssessment_database.NutrientStandards;
import enery.use_case.tracking.TrackingOutputData;

import java.util.UUID;

public class HealthAssessmentInteractor implements HealthAssessmentInputBoundary {

    private final HealthAssessmentOutputBoundary outputBoundary;
    private final HealthAssessmentDataAccessObject healthDataAccess;

    public HealthAssessmentInteractor(HealthAssessmentOutputBoundary outputBoundary,
                                      HealthAssessmentDataAccessObject healthDataAccess) {
        this.outputBoundary = outputBoundary;
        this.healthDataAccess = healthDataAccess;
    }

    @Override
    public void execute(HealthAssessmentInputData inputData) {
        try {
            String assessmentId = UUID.randomUUID().toString();
            String userId = inputData.getUserId();
            TrackingOutputData trackingData = inputData.getTrackingData();

            AssessmentOverview overview = new AssessmentOverview(assessmentId, userId);
            overview.setPeriod(trackingData.getStartDate(), trackingData.getEndDate());

            DietaryPatternAssessment dietaryAssessment = new DietaryPatternAssessment(assessmentId, userId);

            NutrientStandards nutrientStandards = healthDataAccess.getNutrientStandards();
            NutrientComparer comparer = new NutrientComparer();
            NutrientComparer.DietaryEvaluationSummary dietSummary =
                    comparer.evaluateDiet(trackingData, nutrientStandards);

            dietaryAssessment.setDietScore(dietSummary.averageScore);
            for (String insight : dietSummary.insights) {
                dietaryAssessment.addDietInsight(insight);
            }
            for (String item : dietSummary.actionableItems) {
                dietaryAssessment.addDietActionableItem(item);
            }

            overview.setDietaryPatternAssessment(dietaryAssessment);


//            ExercisePatternAssessment exerciseAssessment = new ExercisePatternAssessment(assessmentId, userId);
//
//            int testAge = 25;
//            String testGender = "M";
//            double testHeight = 175.0;
//            double testWeight = 70.0;
//
//            double exerciseStandard = healthDataAccess.getExerciseCalorieRequirement(testAge, testGender, testHeight, testWeight);
//            double actualExerciseBurned = trackingData.getAverageCaloriesBurned();
//
//            evaluateExercise(actualExerciseBurned, exerciseStandard, exerciseAssessment);
//            overview.setExercisePatternAssessment(exerciseAssessment);
//
//            HealthAssessmentOutputData outputData = new HealthAssessmentOutputData(
//                    overview.getAssessmentId(),
//                    overview.getOverallScore(),
//                    overview.getSeverity(),
//                    dietaryAssessment.getDietScore(),
//                    dietaryAssessment.getDietInsights(),
//                    dietaryAssessment.getDietActionableItems(),
//                    exerciseAssessment.getExerciseScore(),
//                    exerciseAssessment.getExerciseInsights(),
//                    exerciseAssessment.getExerciseActionableItems()
//            );
//
//            outputBoundary.prepareSuccessView(outputData);
//
//        } catch (Exception e) {
//            outputBoundary.prepareFailView("Fail to assess: " + e.getMessage());
//        }
//    }
//
//    private void evaluateExercise(double actualBurned, double standard, ExercisePatternAssessment assessment) {
//        if (standard <= 0) {
//            assessment.setExerciseScore(100);
//            return;
//        }
//
//        double diff = actualBurned - standard;
//        double percentageDiff = Math.abs(diff) / standard;
//
//        int rating = 100;
//        String metricName = "Exercise Calories";
//        String unit = " kcal";
//
//        if (actualBurned > standard) {
//            if (percentageDiff >= 0.5) {
//                rating = 0;
//                assessment.addExerciseInsight(metricName + " burned too high. Potential risk of overtraining.");
//            } else {
//                rating = 80;
//                assessment.addExerciseInsight(metricName + " burned slightly high. Generally safe.");
//            }
//            assessment.addExerciseActionableItem("Consider reducing activity by " + String.format("%.1f", diff) + unit);
//
//        } else if (actualBurned < standard) {
//            if (percentageDiff >= 0.5) {
//                rating = 0;
//                assessment.addExerciseInsight(metricName + " burned too low. Health risk present.");
//                assessment.addExerciseActionableItem("Increase daily activity by " + String.format("%.1f", Math.abs(diff)) + unit);
//            } else {
//                rating = 80;
//            }
//        }
//
//        assessment.setExerciseScore(rating);
//    }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}