package enery.interface_adapter.health_assessment;

import enery.use_case.tracking.TrackingOutputData;

import java.util.List;

public class HealthAssessmentState {

    private String userId = "";
    private String assessmentId = "";
    private TrackingOutputData trackingData = null;
    private int overallScore = 0;
    private String severity = "";

    // Dietary Data
    private int dietScore = 0;
    private List<String> dietInsights = null;
    private List<String> dietActionableItems = null;

//    // Exercise Data
//    private int exerciseScore = 0;
//    private List<String> exerciseInsights = null;
//    private List<String> exerciseActionableItems = null;

    private String assessmentError = null;

    // Getters
    public String getUserId() { return userId; }
    public String getAssessmentId() { return assessmentId; }
    public TrackingOutputData getTrackingData() { return trackingData; }

    public int getOverallScore() { return overallScore; }
    public String getSeverity() { return severity; }

    public int getDietScore() { return dietScore; }
    public List<String> getDietInsights() { return dietInsights; }
    public List<String> getDietActionableItems() { return dietActionableItems; }

//    public int getExerciseScore() { return exerciseScore; }
//    public List<String> getExerciseInsights() { return exerciseInsights; }
//    public List<String> getExerciseActionableItems() { return exerciseActionableItems; }

    public String getAssessmentError() { return assessmentError; }

    // Setters
    public void setUserId(String userId) { this.userId = userId; }
    public void setAssessmentId(String assessmentId) { this.assessmentId = assessmentId; }

    public void setOverallScore(int overallScore) { this.overallScore = overallScore; }
    public void setSeverity(String severity) { this.severity = severity; }

    public void setDietScore(int dietScore) { this.dietScore = dietScore; }
    public void setDietInsights(List<String> dietInsights) { this.dietInsights = dietInsights; }
    public void setDietActionableItems(List<String> dietActionableItems) { this.dietActionableItems = dietActionableItems; }

//    public void setExerciseScore(int exerciseScore) { this.exerciseScore = exerciseScore; }
//    public void setExerciseInsights(List<String> exerciseInsights) { this.exerciseInsights = exerciseInsights; }
//    public void setExerciseActionableItems(List<String> exerciseActionableItems) { this.exerciseActionableItems = exerciseActionableItems; }

    public void setAssessmentError(String assessmentError) { this.assessmentError = assessmentError; }
}