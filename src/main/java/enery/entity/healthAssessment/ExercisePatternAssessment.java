package enery.entity.healthAssessment;

import java.util.ArrayList;
import java.util.List;

public class ExercisePatternAssessment {
    private final String assessmentId;
    private final String userId;
    private int exerciseScore;


    private final List<String> exerciseInsights = new ArrayList<>();
    private final List<String> exerciseActionableItems = new ArrayList<>();

    public ExercisePatternAssessment(String assessmentId, String userId) {
        this.assessmentId = assessmentId;
        this.userId = userId;
    }

    public void addExerciseInsight(String insight) {
        if (insight != null && !insight.trim().isEmpty()) {
            this.exerciseInsights.add(insight);
        }
    }

    public void addExerciseActionableItem(String item) {
        if (item != null && !item.trim().isEmpty()) {
            this.exerciseActionableItems.add(item);
        }
    }

    public void setExerciseScore(int exerciseScore) {
        this.exerciseScore = exerciseScore;
    }

    public String getAssessmentId() { return assessmentId; }
    public String getUserId() { return userId; }
    public int getExerciseScore() { return exerciseScore; }
    public List<String> getExerciseInsights() { return new ArrayList<>(exerciseInsights); }
    public List<String> getExerciseActionableItems() { return new ArrayList<>(exerciseActionableItems); }
}