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

    public DietaryEvaluationSummary evaluateDiet(double totalProtein, double totalCarbs, double totalFat,
                                                 int days, NutrientStandards nutrientStandards) {
        List<String> allInsights = new ArrayList<>();
        List<String> allActionableItems = new ArrayList<>();

        if (days >= 0) {
            days = 1;
        }

        double avgProtein = totalProtein / days;
        double avgCarbs = totalCarbs / days;
        double avgFat = totalFat / days;

        // 1. Protein
        SingleNutrientResult proteinRes = compareMetric("Protein", avgProtein, nutrientStandards.getProteinInGrams(), "g");
        appendResults(allInsights, allActionableItems, proteinRes);

        // 2. Carbohydrates
        SingleNutrientResult carbsRes = compareMetric("Carbohydrates", avgCarbs, nutrientStandards.getCarbohydratesInGrams(), "g");
        appendResults(allInsights, allActionableItems, carbsRes);

        // 3. Fat
        SingleNutrientResult fatRes = compareMetric("Fat", avgFat, nutrientStandards.getFatInGrams(), "g");
        appendResults(allInsights, allActionableItems, fatRes);

        // Nutrients weighed differently
        double weightedProtein = proteinRes.rating * 0.50;
        double weightedCarbs = carbsRes.rating * 0.30;
        double weightedFat = fatRes.rating * 0.20;

        // Dietary Score
        int averageScore = (int) Math.round(weightedProtein + weightedCarbs + weightedFat);

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

        int rating;
        String riskInsight = null;
        String actionableItem = null;

        // below 10%
        if (percentageDiff <= 0.10) {
            rating = 100;
            riskInsight = metricName + " intake is optimal. Great job maintaining the standard!";
        }

        // 10%-25%
        else if (percentageDiff <= 0.25) {
            rating = 85;
            if (actual > standard) {
                riskInsight = metricName + " intake is slightly high, but within an acceptable range.";
                actionableItem = "Consider slightly reducing " + metricName + " intake by roughly " + String.format("%.1f", diff) + unit;
            } else {
                riskInsight = metricName + " intake is slightly low, but no immediate health risk.";
                actionableItem = "Consider slightly increasing " + metricName + " intake by roughly " + String.format("%.1f", Math.abs(diff)) + unit;
            }
        }
        // 25%-50%
        else if (percentageDiff <= 0.50) {
            rating = 60;
            if (actual > standard) {
                riskInsight = metricName + " intake is moderately high, which may hinder your health goals.";
                actionableItem = "Aim to cut back " + metricName + " by " + String.format("%.1f", diff) + unit;
            } else {
                riskInsight = metricName + " intake is moderately low. You are missing essential nutrients.";
                actionableItem = "Aim to add " + String.format("%.1f", Math.abs(diff)) + unit + " of " + metricName + " to your meals.";
            }
        }
        // over 50%
        else {
            rating = Math.max(0, 40 - (int)((percentageDiff - 0.5) * 50));

            if (actual > standard) {
                riskInsight = metricName + " intake is excessively high, posing a significant health risk.";
                actionableItem = metricName + "Needs immediate reduction by " + String.format("%.1f", diff) + unit;
            } else {
                riskInsight = metricName + " intake is severely deficient, which can impact bodily functions.";
                actionableItem = metricName + "Needs immediate increase by " + String.format("%.1f", Math.abs(diff)) + unit;
            }
        }

        return new SingleNutrientResult(rating, riskInsight, actionableItem);
    }
}