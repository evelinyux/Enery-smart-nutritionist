package enery.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Stores a user's personal and nutrition-related profile information.
 */
public final class UserProfile {

    private int age;
    private double heightInCentimetres;
    private double weightInKilograms;
    private Sex sex;
    private ActivityLevel activityLevel;
    private HealthGoal healthGoal;
    private Double dailyCalorieGoal;
    private final List<String> dietaryRestrictions;


    public UserProfile(
            int age,
            double heightInCentimetres,
            double weightInKilograms,
            Sex sex,
            ActivityLevel activityLevel,
            HealthGoal healthGoal,
            List<String> dietaryRestrictions) {

        this.age = age;
        this.heightInCentimetres = heightInCentimetres;
        this.weightInKilograms = weightInKilograms;
        this.sex = sex;
        this.activityLevel = activityLevel;
        this.healthGoal = healthGoal;
        this.dietaryRestrictions =
                new ArrayList<>(dietaryRestrictions);
    }

    /**
     * Creates a user profile without a specified sex.
     *
     * @param age the user's age
     * @param heightInCentimetres the user's height
     * @param weightInKilograms the user's weight
     * @param activityLevel the user's activity level
     * @param healthGoal the user's health goal
     * @param dietaryRestrictions the user's dietary restrictions
     */
    public UserProfile(
            int age,
            double heightInCentimetres,
            double weightInKilograms,
            ActivityLevel activityLevel,
            HealthGoal healthGoal,
            List<String> dietaryRestrictions) {

        this(
                age,
                heightInCentimetres,
                weightInKilograms,
                Sex.UNKNOWN,
                activityLevel,
                healthGoal,
                dietaryRestrictions
        );
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getHeightInCentimetres() {
        return heightInCentimetres;
    }

    public void setHeightInCentimetres(
            double heightInCentimetres) {

        this.heightInCentimetres =
                heightInCentimetres;
    }

    public double getWeightInKilograms() {
        return weightInKilograms;
    }

    public void setWeightInKilograms(
            double weightInKilograms) {

        this.weightInKilograms =
                weightInKilograms;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public ActivityLevel getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(
            ActivityLevel activityLevel) {

        this.activityLevel = activityLevel;
    }

    public HealthGoal getHealthGoal() {
        return healthGoal;
    }

    public void setHealthGoal(
            HealthGoal healthGoal) {

        this.healthGoal = healthGoal;
    }

    /**
     * Returns the estimated daily calorie goal.
     *
     * @return estimated calories per day, or null if unavailable
     */
    public Double getDailyCalorieGoal() {
        return dailyCalorieGoal;
    }

    /**
     * Sets the estimated daily calorie goal.
     *
     * @param dailyCalorieGoal estimated calories per day
     */
    public void setDailyCalorieGoal(
            Double dailyCalorieGoal) {

        this.dailyCalorieGoal = dailyCalorieGoal;
    }

    public List<String> getDietaryRestrictions() {
        return Collections.unmodifiableList(
                dietaryRestrictions);
    }

    public void addDietaryRestriction(
            String restriction) {

        dietaryRestrictions.add(restriction);
    }

    public void removeDietaryRestriction(
            String restriction) {

        dietaryRestrictions.remove(restriction);
    }
}