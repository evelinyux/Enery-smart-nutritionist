package enery.use_case.health_assessment;

import enery.data_access.HealthAssessment_database.NutrientStandards;
import enery.use_case.tracking.TrackingOutputData;
import java.util.ArrayList;
import java.util.List;

public class NutrientComparer {

    public static class SingleNutrientResult {
        public final int rating;
        public final String riskInsight;
        public final String actionableItem;

        public SingleNutrientResult(int rating, String riskInsight, String actionableItem) {
            this.rating = rating;
            this.riskInsight = riskInsight;
            this.actionableItem = actionableItem;
        }
    }

    public static class DietaryEvaluationSummary {
        public final int averageScore;
        public final List<String> insights;
        public final List<String> actionableItems;

        public DietaryEvaluationSummary(int averageScore, List<String> insights, List<String> actionableItems) {
            this.averageScore = averageScore;
            this.insights = insights;
            this.actionableItems = actionableItems;
        }
    }

    // Comparing TackingOutputData and Standards

    public DietaryEvaluationSummary evaluateDiet(TrackingOutputData trackingData, NutrientStandards standards) {
        List<String> allInsights = new ArrayList<>();
        List<String> allActionableItems = new ArrayList<>();
        int totalScore = 0;
        int nutrientCount = 0;

        // Period Total -> Daily Average
        int days = trackingData.getDailySummaries() != null && !trackingData.getDailySummaries().isEmpty()
                ? trackingData.getDailySummaries().size()
                : 1;

        double avgProtein = trackingData.getProteinInGrams() / days;
        double avgCarbs = trackingData.getCarbohydratesInGrams() / days;
        double avgFat = trackingData.getFatInGrams() / days;

        // 1. Protein
        SingleNutrientResult proteinRes = compareMetric("Protein", avgProtein, standards.getProteinInGrams(), "g");
        totalScore += proteinRes.rating;
        appendResults(allInsights, allActionableItems, proteinRes);
        nutrientCount++;

        // 2. Carbohydrates
        SingleNutrientResult carbsRes = compareMetric("Carbohydrates", avgCarbs, standards.getCarbohydratesInGrams(), "g");
        totalScore += carbsRes.rating;
        appendResults(allInsights, allActionableItems, carbsRes);
        nutrientCount++;

        // 3. Fat
        SingleNutrientResult fatRes = compareMetric("Fat", avgFat, standards.getFatInGrams(), "g");
        totalScore += fatRes.rating;
        appendResults(allInsights, allActionableItems, fatRes);
        nutrientCount++;

        // Dietary Score
        int averageScore = Math.round((float) totalScore / nutrientCount);

        return new DietaryEvaluationSummary(averageScore, allInsights, allActionableItems);
    }

    private void appendResults(List<String> insights, List<String> actionableItems, SingleNutrientResult result) {
        if (result.riskInsight != null) {
            insights.add(result.riskInsight);
        }
        if (result.actionableItem != null) {
            actionableItems.add(result.actionableItem);
        }
    }

    private SingleNutrientResult compareMetric(String metricName, double actual, double standard, String unit) {
        if (standard <= 0) return new SingleNutrientResult(100, null, null);

        double diff = actual - standard;
        double percentageDiff = Math.abs(diff) / standard;

        int rating = 100;
        String riskInsight = null;
        String actionableItem = null;

        if (actual > standard) {
            if (percentageDiff >= 0.5) {
                rating = 0;
                riskInsight = metricName + " intake is too high, posing a health risk";
            } else {
                rating = 80;
                riskInsight = metricName + " intake is slightly high, but no immediate health risk";
            }
            actionableItem = metricName + " intake needs to be reduced by (" + String.format("%.1f", diff) + unit + ")";

        } else if (actual < standard) {
            if (percentageDiff >= 0.5) {
                rating = 0;
                riskInsight = metricName + " intake is too low, posing a health risk";
                actionableItem = metricName + " intake needs to be increased by (" + String.format("%.1f", Math.abs(diff)) + unit + ")";
            } else {
                rating = 80;
            }
        }

        return new SingleNutrientResult(rating, riskInsight, actionableItem);
    }
}