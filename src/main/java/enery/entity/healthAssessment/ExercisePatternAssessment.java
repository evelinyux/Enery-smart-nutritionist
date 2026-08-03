package enery.entity.healthAssessment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExercisePatternAssessment {
    private final String assessmentId;
    private final String userId;
    private int exerciseScore;
    private List<String> exerciseInsights;
    private List<String> exerciseActionableItems;

    public ExercisePatternAssessment(String assessmentId, String userId) {
        this.assessmentId = assessmentId;
        this.userId = userId;
    }
    public void addExerciseInsight(String exerciseInsights) {
        if (exerciseInsights != null && !exerciseInsights.trim().isEmpty()) {
            this.exerciseInsights.add(exerciseInsights);
        }
    }

    public void addExerciseActionableItem(String item) {
        if (item != null && !item.trim().isEmpty()) {
            this.exerciseActionableItems.add(item);
        }
    }
    public String getAssessmentId() { return assessmentId; }
    public String getUserId() { return userId; }
    public int getExerciseScore() { return exerciseScore; }
}
