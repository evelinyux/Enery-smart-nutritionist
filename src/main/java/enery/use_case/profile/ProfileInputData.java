package enery.use_case.profile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import enery.entity.Sex;
import enery.entity.ActivityLevel;
import enery.entity.HealthGoal;

/**
 * The Input Data for the Profile Use Case.
 */
public class ProfileInputData {

    private final int age;
    private final double heightInCentimetres;
    private final double weightInKilograms;
    private final Sex sex;
    private final ActivityLevel activityLevel;
    private final HealthGoal healthGoal;
    private final List<String> dietaryRestrictions;

    public ProfileInputData(
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

    public int getAge() {
        return age;
    }

    public double getHeightInCentimetres() {
        return heightInCentimetres;
    }

    public double getWeightInKilograms() {
        return weightInKilograms;
    }

    public Sex getSex() {
        return sex;
    }

    public ActivityLevel getActivityLevel() {
        return activityLevel;
    }

    public HealthGoal getHealthGoal() {
        return healthGoal;
    }

    public List<String> getDietaryRestrictions() {
        return Collections.unmodifiableList(
                dietaryRestrictions);
    }
}