//package enery.use_case.profile;
//
//import java.time.LocalDate;
//
//import enery.entity.user.HealthGoal;
//import enery.entity.user.User;
//import enery.entity.user.UserProfile;
//import enery.entity.user.WeightGoalPlan;
//import enery.entity.strategy.DailyCalorieGoalCalculator;
//import enery.entity.user.NutritionGoal;
//
///**
// * The Profile Interactor.
// */
//public class ProfileInteractor implements ProfileInputBoundary {
//
//    private final ProfileUserDataAccessInterface userDataAccessObject;
//    private final ProfileOutputBoundary profilePresenter;
//
//    private final DailyCalorieGoalCalculator dailyGoalCalculator =
//            new DailyCalorieGoalCalculator();
//
//    public ProfileInteractor(
//            ProfileUserDataAccessInterface profileDataAccessInterface,
//            ProfileOutputBoundary profileOutputBoundary) {
//
//        this.userDataAccessObject =
//                profileDataAccessInterface;
//
//        this.profilePresenter =
//                profileOutputBoundary;
//    }
//
//    @Override
//    public void execute(
//            ProfileInputData profileInputData) {
//
//        if (profileInputData.getAge() <= 0) {
//            profilePresenter.prepareFailView(
//                    "Age must be greater than zero.");
//        }
//        else if (profileInputData
//                .getHeightInCentimetres() <= 0) {
//
//            profilePresenter.prepareFailView(
//                    "Height must be greater than zero.");
//        }
//        else if (profileInputData
//                .getWeightInKilograms() <= 0) {
//
//            profilePresenter.prepareFailView(
//                    "Current weight must be greater than zero.");
//        }
//        else if (profileInputData
//                .getTargetWeightInKilograms() <= 0) {
//
//            profilePresenter.prepareFailView(
//                    "Target weight must be greater than zero.");
//        }
//        else if (profileInputData.getDurationDays() <= 0) {
//            profilePresenter.prepareFailView(
//                    "Plan duration must be greater than zero.");
//        }
//        else if (profileInputData.getSex() == null) {
//            profilePresenter.prepareFailView(
//                    "Please select a sex.");
//        }
//        else if (profileInputData.getActivityLevel() == null) {
//            profilePresenter.prepareFailView(
//                    "Please select an activity level.");
//        }
//        else if (profileInputData.getHealthGoal() == null) {
//            profilePresenter.prepareFailView(
//                    "Please select a health goal.");
//        }
//        else if (profileInputData.getAge() < 18
//                && profileInputData.getHealthGoal()
//                != HealthGoal.MAINTAIN_WEIGHT) {
//
//            profilePresenter.prepareFailView(
//                    "Users under 18 can only create "
//                            + "a maintenance plan.");
//        }
//        else if (profileInputData.getAge() < 18
//                && Double.compare(
//                profileInputData
//                        .getWeightInKilograms(),
//                profileInputData
//                        .getTargetWeightInKilograms()) != 0) {
//
//            profilePresenter.prepareFailView(
//                    "For users under 18, target weight "
//                            + "must equal current weight.");
//        }
//        else {
//            saveProfile(profileInputData);
//        }
//    }
//
//    /**
//     * Creates and saves the user's profile and plan.
//     *
//     * @param profileInputData profile and plan input
//     */
//    private void saveProfile(
//            ProfileInputData profileInputData) {
//
//        final String username =
//                userDataAccessObject.getCurrentUsername();
//
//        if (username == null) {
//            profilePresenter.prepareFailView(
//                    "No user is currently logged in.");
//            return;
//        }
//
//        final User user =
//                userDataAccessObject.get(username);
//
//        if (user == null) {
//            profilePresenter.prepareFailView(
//                    "The current user could not be found.");
//            return;
//        }
//
//        try {
//            final UserProfile userProfile =
//                    createUserProfile(profileInputData);
//
//            final double dailyCalorieGoal =
//                    dailyGoalCalculator.calculate(
//                            userProfile);
//
//            final WeightGoalPlan weightGoalPlan =
//                    new WeightGoalPlan(
//                            LocalDate.now(),
//                            profileInputData.getDurationDays(),
//                            profileInputData
//                                    .getWeightInKilograms(),
//                            profileInputData
//                                    .getTargetWeightInKilograms(),
//                            profileInputData.getHealthGoal(),
//                            dailyCalorieGoal
//                    );
//
//            userProfile.setActivePlan(
//                    weightGoalPlan);
//
//            user.setUserProfile(userProfile);
//            userDataAccessObject.save(user);
//
//            final ProfileOutputData outputData =
//                    new ProfileOutputData(
//                            username,
//                            userProfile,
//                            false
//                    );
//
//            profilePresenter.prepareSuccessView(
//                    outputData);
//        }
//        catch (IllegalArgumentException exception) {
//            profilePresenter.prepareFailView(
//                    exception.getMessage());
//        }
//    }
//
//    /**
//     * Creates the UserProfile entity from the input data.
//     *
//     * @param profileInputData profile input
//     * @return newly created user profile
//     */
//    private UserProfile createUserProfile(
//            ProfileInputData profileInputData) {
//
//        return new UserProfile(
//                profileInputData.getAge(),
//                profileInputData
//                        .getHeightInCentimetres(),
//                profileInputData
//                        .getWeightInKilograms(),
//                profileInputData.getSex(),
//                profileInputData.getActivityLevel(),
//                profileInputData.getHealthGoal(),
//                profileInputData
//                        .getDietaryRestrictions()
//        );
//    }
//
//    @Override
//    public void switchToHomeView() {
//        profilePresenter.switchToHomeView();
//    }
//}
package enery.use_case.profile;

import java.time.LocalDate;

import enery.entity.user.HealthGoal;
import enery.entity.user.User;
import enery.entity.user.UserProfile;
import enery.entity.user.WeightGoalPlan;
import enery.entity.strategy.DailyCalorieGoalCalculator;
import enery.entity.user.NutritionGoal;

/**
 * The Profile Interactor.
 */
public class ProfileInteractor implements ProfileInputBoundary {

    private final ProfileUserDataAccessInterface userDataAccessObject;
    private final ProfileOutputBoundary profilePresenter;

    private final DailyCalorieGoalCalculator dailyGoalCalculator =
            new DailyCalorieGoalCalculator();

    public ProfileInteractor(
            ProfileUserDataAccessInterface profileDataAccessInterface,
            ProfileOutputBoundary profileOutputBoundary) {

        this.userDataAccessObject =
                profileDataAccessInterface;

        this.profilePresenter =
                profileOutputBoundary;
    }

    @Override
    public void execute(
            ProfileInputData profileInputData) {

        if (profileInputData.getAge() <= 0) {
            profilePresenter.prepareFailView(
                    "Age must be greater than zero.");
        }
        else if (profileInputData
                .getHeightInCentimetres() <= 0) {

            profilePresenter.prepareFailView(
                    "Height must be greater than zero.");
        }
        else if (profileInputData
                .getWeightInKilograms() <= 0) {

            profilePresenter.prepareFailView(
                    "Current weight must be greater than zero.");
        }
        else if (profileInputData
                .getTargetWeightInKilograms() <= 0) {

            profilePresenter.prepareFailView(
                    "Target weight must be greater than zero.");
        }
        else if (profileInputData.getDurationDays() <= 0) {
            profilePresenter.prepareFailView(
                    "Plan duration must be greater than zero.");
        }
        else if (profileInputData.getSex() == null) {
            profilePresenter.prepareFailView(
                    "Please select a sex.");
        }
        else if (profileInputData.getActivityLevel() == null) {
            profilePresenter.prepareFailView(
                    "Please select an activity level.");
        }
        else if (profileInputData.getHealthGoal() == null) {
            profilePresenter.prepareFailView(
                    "Please select a health goal.");
        }
        else if (profileInputData.getAge() < 18
                && profileInputData.getHealthGoal()
                != HealthGoal.MAINTAIN_WEIGHT) {

            profilePresenter.prepareFailView(
                    "Users under 18 can only create "
                            + "a maintenance plan.");
        }
        else if (profileInputData.getAge() < 18
                && Double.compare(
                profileInputData
                        .getWeightInKilograms(),
                profileInputData
                        .getTargetWeightInKilograms()) != 0) {

            profilePresenter.prepareFailView(
                    "For users under 18, target weight "
                            + "must equal current weight.");
        }
        else {
            saveProfile(profileInputData);
        }
    }

    /**
     * Creates and saves the user's profile and plan.
     *
     * @param profileInputData profile and plan input
     */
    private void saveProfile(
            ProfileInputData profileInputData) {

        final String username =
                userDataAccessObject.getCurrentUsername();

        if (username == null) {
            profilePresenter.prepareFailView(
                    "No user is currently logged in.");
            return;
        }

        final User user =
                userDataAccessObject.get(username);

        if (user == null) {
            profilePresenter.prepareFailView(
                    "The current user could not be found.");
            return;
        }

        try {
            final UserProfile userProfile =
                    createUserProfile(profileInputData);

            final double dailyCalorieGoal =
                    dailyGoalCalculator.calculate(
                            userProfile);

            final WeightGoalPlan weightGoalPlan =
                    new WeightGoalPlan(
                            LocalDate.now(),
                            profileInputData.getDurationDays(),
                            profileInputData
                                    .getWeightInKilograms(),
                            profileInputData
                                    .getTargetWeightInKilograms(),
                            profileInputData.getHealthGoal(),
                            dailyCalorieGoal
                    );

            userProfile.setActivePlan(
                    weightGoalPlan);

            user.setUserProfile(userProfile);
            // Create and set the NutritionGoal using the calculated daily calories
            user.setNutritionGoal(new NutritionGoal(dailyCalorieGoal));
            userDataAccessObject.save(user);

            final ProfileOutputData outputData =
                    new ProfileOutputData(
                            username,
                            userProfile,
                            false
                    );

            profilePresenter.prepareSuccessView(
                    outputData);
        }
        catch (IllegalArgumentException exception) {
            profilePresenter.prepareFailView(
                    exception.getMessage());
        }
    }

    /**
     * Creates the UserProfile entity from the input data.
     *
     * @param profileInputData profile input
     * @return newly created user profile
     */
    private UserProfile createUserProfile(
            ProfileInputData profileInputData) {

        return new UserProfile(
                profileInputData.getAge(),
                profileInputData
                        .getHeightInCentimetres(),
                profileInputData
                        .getWeightInKilograms(),
                profileInputData.getSex(),
                profileInputData.getActivityLevel(),
                profileInputData.getHealthGoal(),
                profileInputData
                        .getDietaryRestrictions()
        );
    }

    @Override
    public void switchToHomeView() {
        profilePresenter.switchToHomeView();
    }
}
