package enery.data_access.HealthAssessment_database;

import enery.entity.exercise.ExerciseEntry;
import enery.entity.meal.MealEntry;
import enery.use_case.health_assessment.HealthAssessmentDataAccessInterface;
import enery.use_case.tracking.TrackingDataAccessInterface;
import enery.use_case.tracking.TrackingOutputData;
import enery.use_case.tracking.TrackingPeriod;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HealthAssessmentDataAccessObject implements HealthAssessmentDataAccessInterface {

    private final NutrientStandards standards = new NutrientStandards();
    private final TrackingDataAccessInterface trackingDataAccessObject;

    public HealthAssessmentDataAccessObject(TrackingDataAccessInterface trackingDataAccessObject) {
        this.trackingDataAccessObject = trackingDataAccessObject;
    }

    @Override
    public double getProteinInGrams() {
        return standards.getProteinInGrams();
    }

    @Override
    public double getCarbohydratesInGrams() {
        return standards.getCarbohydratesInGrams();
    }

    @Override
    public double getFatInGrams() {
        return standards.getFatInGrams();
    }

    @Override
    public NutrientStandards getNutrientStandards() {
        return standards;
    }

    @Override
    public TrackingOutputData getTrackingData(String userId, LocalDate startDate, LocalDate endDate) {
        List<MealEntry> meals = trackingDataAccessObject.getMeals(userId, startDate, endDate);
        List<ExerciseEntry> exercises = trackingDataAccessObject.getExercises(userId, startDate, endDate);

        double totalCaloriesIn = 0.0;
        double totalProtein = 0.0;
        double totalCarbs = 0.0;
        double totalFat = 0.0;

        for (MealEntry meal : meals) {
            totalCaloriesIn += meal.getTotalCalories();
            totalProtein += meal.getTotalProteinInGrams();
            totalCarbs += meal.getTotalCarbohydratesInGrams();
            totalFat += meal.getTotalFatInGrams();
        }

        double totalCaloriesOut = 0.0;
        for (ExerciseEntry exercise : exercises) {
            totalCaloriesOut += exercise.getCaloriesBurned();
        }

        return new TrackingOutputData(
                TrackingPeriod.WEEKLY,
                startDate,
                endDate,
                totalCaloriesIn,
                totalCaloriesOut,
                totalCaloriesIn - totalCaloriesOut,
                totalProtein,
                totalCarbs,
                totalFat,
                0, 0, 0, 0, 0, 0,
                new ArrayList<>()
        );
    }
}