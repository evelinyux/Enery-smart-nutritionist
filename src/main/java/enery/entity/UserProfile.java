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
    private ActivityLevel activityLevel;
    private HealthGoal healthGoal;
    private final List<String> dietaryRestrictions;

    public UserProfile(
            int age,
            double heightInCentimetres,
            double weightInKilograms,
            ActivityLevel activityLevel,
            HealthGoal healthGoal,
            List<String> dietaryRestrictions) {

        this.age = age;
        this.heightInCentimetres = heightInCentimetres;
        this.weightInKilograms = weightInKilograms;
        this.activityLevel = activityLevel;
        this.healthGoal = healthGoal;
        this.dietaryRestrictions = new ArrayList<>(dietaryRestrictions);
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

    public void setHeightInCentimetres(double heightInCentimetres) {
        this.heightInCentimetres = heightInCentimetres;
    }

    public double getWeightInKilograms() {
        return weightInKilograms;
    }

    public void setWeightInKilograms(double weightInKilograms) {
        this.weightInKilograms = weightInKilograms;
    }

    public ActivityLevel getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(ActivityLevel activityLevel) {
        this.activityLevel = activityLevel;
    }

    public HealthGoal getHealthGoal() {
        return healthGoal;
    }

    public void setHealthGoal(HealthGoal healthGoal) {
        this.healthGoal = healthGoal;
    }

    public List<String> getDietaryRestrictions() {
        return Collections.unmodifiableList(dietaryRestrictions);
    }

    public void addDietaryRestriction(String restriction) {
        dietaryRestrictions.add(restriction);
    }

    public void removeDietaryRestriction(String restriction) {
        dietaryRestrictions.remove(restriction);
    }
}
