package enery.entity.healthAssessment;

import java.time.LocalDate;

    public class AssessmentOverview {
        // Fundamental Attributes
        private final String assessmentId;
        private final String userId;

        private LocalDate periodStartDate;
        private LocalDate periodEndDate;

        private int overallScore;
        private String severity; // optimal/healthy/needs_improvement/health_risk/critical
        private String insights;

        private float dietWeight = 0.6F;
        private float exerciseWeight = 0.4F;

        // Contains 2 sub-assessments
        private DietaryPatternAssessment dietaryAssessment;
        private ExercisePatternAssessment exerciseAssessment;


        public AssessmentOverview(String assessmentId, String userId) {
            this.assessmentId = assessmentId;
            this.userId = userId;
        }

        private void calculateOverallScore() {
            if (this.dietaryAssessment != null && this.exerciseAssessment != null) {
                float calculated = (this.dietaryAssessment.getDietScore() * dietWeight) +
                        (this.exerciseAssessment.getExerciseScore() * exerciseWeight);
                this.overallScore = Math.round(calculated);
            }
        }

        public DietaryPatternAssessment getDietaryAssessment() {
            return dietaryAssessment;
        }
        public String getAssessmentId() { return assessmentId; }
        public String getUserId() { return userId; }
        public int getOverallScore() { return overallScore; }
        public String getSeverity() { return severity; }
}
