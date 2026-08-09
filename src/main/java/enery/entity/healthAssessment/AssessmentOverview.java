package enery.entity.healthAssessment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AssessmentOverview {

    private final String assessmentId;
    private final String userId;

    private LocalDate periodStartDate;
    private LocalDate periodEndDate;

    private int overallScore;

    private String severity;

    private static final float DIET_WEIGHT = 1;
//    private static final float EXERCISE_WEIGHT = 0.4F;

    private DietaryPatternAssessment dietaryPatternAssessment;
//    private ExercisePatternAssessment exercisePatternAssessment;


    public AssessmentOverview(String assessmentId, String userId) {
        this.assessmentId = assessmentId;
        this.userId = userId;
    }

    // Data Injection & Behavior
    public void setPeriod(LocalDate startDate, LocalDate endDate) {
        this.periodStartDate = startDate;
        this.periodEndDate = endDate;
    }

    public void setDietaryPatternAssessment(DietaryPatternAssessment dietaryPatternAssessment) {
        this.dietaryPatternAssessment = dietaryPatternAssessment;
        updateAssessmentState();
    }

//    public void setExercisePatternAssessment(ExercisePatternAssessment exercisePatternAssessment) {
//        this.exercisePatternAssessment = exercisePatternAssessment;
//        updateAssessmentState();
//    }

//    public void addOverallInsight(String insight) {
//        if (insight != null && !insight.trim().isEmpty()) {
//            this.overallInsights.add(insight);
//        }
//    }

    // Core Enterprise Business Rules
    private void updateAssessmentState() {
        if (this.dietaryPatternAssessment != null) {
            float calculated = (this.dietaryPatternAssessment.getDietScore() * DIET_WEIGHT);
            //                  + (this.exercisePatternAssessment.getExerciseScore() * EXERCISE_WEIGHT
            this.overallScore = Math.round(calculated);

            determineSeverity();
        }
    }

    private void determineSeverity() {
        if (overallScore >= 90) {
            this.severity = "optimal";
        } else if (overallScore >= 70) {
            this.severity = "healthy";
        } else if (overallScore >= 50) {
            this.severity = "needs improvement";
        } else if (overallScore >= 30) {
            this.severity = "at health risk";
        } else {
            this.severity = "critical";
        }
    }

    // Getters
    public String getAssessmentId() { return assessmentId; }
    public int getOverallScore() { return overallScore; }
    public String getSeverity() { return severity; }

}