package enery.use_case.profile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import enery.entity.user.ActivityLevel;
import enery.entity.user.HealthGoal;
import enery.entity.user.Sex;

/**
 * The Input Data for the Profile Use Case.
 */
public class ProfileInputData {

    private final int age;
    private final double heightInCentimetres;
    private final double weightInKilograms;
    private final double targetWeightInKilograms;
    private final int durationDays;
    private final Sex sex;
    private final ActivityLevel activityLevel;
    private final HealthGoal healthGoal;
    private final List<String> dietaryRestrictions;

    public ProfileInputData(
            int age,
            double heightInCentimetres,
            double weightInKilograms,
            double targetWeightInKilograms,
            int durationDays,
            Sex sex,
            ActivityLevel activityLevel,
            HealthGoal healthGoal,
            List<String> dietaryRestrictions) {

        this.age = age;
        this.heightInCentimetres = heightInCentimetres;
        this.weightInKilograms = weightInKilograms;
        this.targetWeightInKilograms =
                targetWeightInKilograms;
        this.durationDays = durationDays;
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

    public double getTargetWeightInKilograms() {
        return targetWeightInKilograms;
    }

    public int getDurationDays() {
        return durationDays;
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