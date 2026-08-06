package enery.data_access.HealthAssessment_database;

public class NutrientStandards {
    private final double proteinInGrams = 50;
    private final double carbohydratesInGrams = 130;
    private final double fatInGrams = 10;
    public double getProteinInGrams() {
            return proteinInGrams;
        }

    public double getCarbohydratesInGrams() {
        return carbohydratesInGrams;
    }

    public double getFatInGrams() {
        return fatInGrams;
    }
}
