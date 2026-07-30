package enery.interface_adapter.profile;

import java.util.List;

import enery.entity.Sex;
import enery.entity.ActivityLevel;
import enery.entity.HealthGoal;
import enery.use_case.profile.ProfileInputBoundary;
import enery.use_case.profile.ProfileInputData;

/**
 * Controller for the Profile Use Case.
 */
public class ProfileController {

    private final ProfileInputBoundary profileInteractor;

    public ProfileController(
            ProfileInputBoundary profileInteractor) {

        this.profileInteractor = profileInteractor;
    }

    /**
     * Executes the Profile Use Case.
     *
     * @param age the user's age
     * @param heightInCentimetres the user's height
     * @param weightInKilograms the user's weight
     * @param activityLevel the user's activity level
     * @param healthGoal the user's health goal
     * @param dietaryRestrictions the user's dietary restrictions
     */
    public void execute(
            int age,
            double heightInCentimetres,
            double weightInKilograms,
            Sex sex,
            ActivityLevel activityLevel,
            HealthGoal healthGoal,
            List<String> dietaryRestrictions) {

        final ProfileInputData profileInputData =
                new ProfileInputData(
                        age,
                        heightInCentimetres,
                        weightInKilograms,
                        sex,
                        activityLevel,
                        healthGoal,
                        dietaryRestrictions
                );

        profileInteractor.execute(profileInputData);
    }

    /**
     * Switches from the profile view to the home view.
     */
    public void switchToHomeView() {
        profileInteractor.switchToHomeView();
    }
}