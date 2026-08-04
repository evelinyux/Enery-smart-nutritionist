package enery.entity.healthAssessment;

import java.util.ArrayList;
import java.util.List;

public class DietaryPatternAssessment {
    private final String assessmentId;
    private final String userId;
    private int dietScore;


    private final List<String> dietInsights = new ArrayList<>();
    private final List<String> dietActionableItems = new ArrayList<>();

    public DietaryPatternAssessment(String assessmentId, String userId) {
        this.assessmentId = assessmentId;
        this.userId = userId;
    }

    public void addDietInsight(String insight) {
        if (insight != null && !insight.trim().isEmpty()) {
            this.dietInsights.add(insight);
        }
    }

    public void addDietActionableItem(String item) {
        if (item != null && !item.trim().isEmpty()) {
            this.dietActionableItems.add(item);
        }
    }

    public void setDietScore(int dietScore) {
        this.dietScore = dietScore;
    }

    public String getAssessmentId() { return assessmentId; }
    public String getUserId() { return userId; }
    public int getDietScore() { return dietScore; }
    public List<String> getDietInsights() { return new ArrayList<>(dietInsights); }
    public List<String> getDietActionableItems() { return new ArrayList<>(dietActionableItems); }
}