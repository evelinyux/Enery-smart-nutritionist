package enery.use_case.health_assessment;

import java.util.List;

public class HealthAssessmentOutputData {
    public final String assessmentId;
    public final int overallScore;
    public final String severity;

    // Dietary Data
    public final int dietScore;
    public final List<String> dietInsights;
    public final List<String> dietActionableItems;

    // Exercise Data
    public final int exerciseScore;
    public final List<String> exerciseInsights;
    public final List<String> exerciseActionableItems;

    public HealthAssessmentOutputData(String assessmentId, int overallScore, String severity,
                                      int dietScore, List<String> dietInsights, List<String> dietActionableItems,
                                      int exerciseScore, List<String> exerciseInsights, List<String> exerciseActionableItems) {
        this.assessmentId = assessmentId;
        this.overallScore = overallScore;
        this.severity = severity;
        this.dietScore = dietScore;
        this.dietInsights = dietInsights;
        this.dietActionableItems = dietActionableItems;
        this.exerciseScore = exerciseScore;
        this.exerciseInsights = exerciseInsights;
        this.exerciseActionableItems = exerciseActionableItems;
    }
}