package enery.entity.healthAssessment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DietaryPatternAssessment {
    private final String assessmentId;
    private final String userId;
    private int dietScore;
    private List<String> dietInsights;
    private List<String> dietActionableItems;

    public DietaryPatternAssessment(String assessmentId, String userId) {
        this.assessmentId = assessmentId;
        this.userId = userId;
    }

    public void addDietInsights(String dietInsights) {
        if (dietInsights != null && !dietInsights.trim().isEmpty()) {
            this.dietInsights.add(dietInsights);
        }
    }

    public void addDietActionableItem(String item) {
        if (item != null && !item.trim().isEmpty()) {
            this.dietActionableItems.add(item);
        }
    }

    public String getAssessmentId() { return assessmentId; }
    public String getUserId() { return userId; }
    public int getDietScore() { return dietScore; }
}
