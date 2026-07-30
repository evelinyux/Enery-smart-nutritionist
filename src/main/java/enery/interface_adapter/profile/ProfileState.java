package enery.interface_adapter.profile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import enery.entity.user.ActivityLevel;
import enery.entity.user.HealthGoal;
import enery.entity.user.Sex;

/**
 * The state for the Profile View Model.
 */
public class ProfileState {

    private String username = "";

    private int age;
    private double heightInCentimetres;
    private double weightInKilograms;

    private Sex sex;
    private ActivityLevel activityLevel;
    private HealthGoal healthGoal;

    private double targetWeightInKilograms;
    private int durationDays;

    private String startDate = "";
    private String endDate = "";

    private Double dailyCalorieGoal;

    private List<String> dietaryRestrictions =
            new ArrayList<>();

    private String profileError;
    private String successMessage;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public double getTargetWeightInKilograms() {
        return targetWeightInKilograms;
    }

    public void setTargetWeightInKilograms(
            double targetWeightInKilograms) {

        this.targetWeightInKilograms =
                targetWeightInKilograms;
    }

    public int getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(
            int durationDays) {

        this.durationDays = durationDays;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(
            String startDate) {

        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(
            String endDate) {

        this.endDate = endDate;
    }

    public Double getDailyCalorieGoal() {
        return dailyCalorieGoal;
    }

    public void setDailyCalorieGoal(
            Double dailyCalorieGoal) {

        this.dailyCalorieGoal =
                dailyCalorieGoal;
    }

    public List<String> getDietaryRestrictions() {
        return Collections.unmodifiableList(
                dietaryRestrictions);
    }

    public void setDietaryRestrictions(
            List<String> dietaryRestrictions) {

        this.dietaryRestrictions =
                new ArrayList<>(dietaryRestrictions);
    }

    public String getProfileError() {
        return profileError;
    }

    public void setProfileError(
            String profileError) {

        this.profileError = profileError;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(
            String successMessage) {

        this.successMessage =
                successMessage;
    }
}