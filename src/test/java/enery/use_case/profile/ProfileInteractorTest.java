package enery.use_case.profile;

import enery.entity.tracking.DailyLog;
import enery.entity.user.ActivityLevel;
import enery.entity.user.HealthGoal;
import enery.entity.user.NutritionGoal;
import enery.entity.user.Sex;
import enery.entity.user.User;
import enery.entity.user.UserProfile;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ProfileInteractorTest {

    @Test
    void successTest() {
        FakeUser user =
                new FakeUser("nicole");

        FakeDataAccessObject dao =
                new FakeDataAccessObject(
                        "nicole",
                        user);

        FakePresenter presenter =
                new FakePresenter();

        ProfileInteractor interactor =
                new ProfileInteractor(
                        dao,
                        presenter);

        interactor.execute(validInput());

        assertTrue(dao.saveCalled);

        assertNotNull(
                user.getUserProfile());

        assertEquals(
                25,
                user.getUserProfile().getAge());

        assertEquals(
                170.0,
                user.getUserProfile()
                        .getHeightInCentimetres(),
                0.001);

        assertEquals(
                70.0,
                user.getUserProfile()
                        .getWeightInKilograms(),
                0.001);

        assertEquals(
                Sex.FEMALE,
                user.getUserProfile().getSex());

        assertEquals(
                ActivityLevel.MODERATELY_ACTIVE,
                user.getUserProfile()
                        .getActivityLevel());

        assertEquals(
                HealthGoal.MAINTAIN_WEIGHT,
                user.getUserProfile()
                        .getHealthGoal());

        assertNotNull(
                user.getUserProfile()
                        .getActivePlan());

        assertTrue(presenter.successCalled);
        assertFalse(presenter.failCalled);

        assertNotNull(presenter.outputData);

        assertEquals(
                "nicole",
                presenter.outputData
                        .getUsername());

        assertFalse(
                presenter.outputData
                        .isUseCaseFailed());
    }

    @Test
    void invalidAgeTest() {
        assertValidationFailure(
                new ProfileInputData(
                        0,
                        170,
                        70,
                        70,
                        30,
                        Sex.FEMALE,
                        ActivityLevel.MODERATELY_ACTIVE,
                        HealthGoal.MAINTAIN_WEIGHT,
                        List.of()
                ),
                "Age must be greater than zero."
        );
    }

    @Test
    void invalidHeightTest() {
        assertValidationFailure(
                new ProfileInputData(
                        25,
                        0,
                        70,
                        70,
                        30,
                        Sex.FEMALE,
                        ActivityLevel.MODERATELY_ACTIVE,
                        HealthGoal.MAINTAIN_WEIGHT,
                        List.of()
                ),
                "Height must be greater than zero."
        );
    }

    @Test
    void invalidCurrentWeightTest() {
        assertValidationFailure(
                new ProfileInputData(
                        25,
                        170,
                        0,
                        70,
                        30,
                        Sex.FEMALE,
                        ActivityLevel.MODERATELY_ACTIVE,
                        HealthGoal.MAINTAIN_WEIGHT,
                        List.of()
                ),
                "Current weight must be greater than zero."
        );
    }

    @Test
    void invalidTargetWeightTest() {
        assertValidationFailure(
                new ProfileInputData(
                        25,
                        170,
                        70,
                        0,
                        30,
                        Sex.FEMALE,
                        ActivityLevel.MODERATELY_ACTIVE,
                        HealthGoal.MAINTAIN_WEIGHT,
                        List.of()
                ),
                "Target weight must be greater than zero."
        );
    }

    @Test
    void invalidDurationTest() {
        assertValidationFailure(
                new ProfileInputData(
                        25,
                        170,
                        70,
                        70,
                        0,
                        Sex.FEMALE,
                        ActivityLevel.MODERATELY_ACTIVE,
                        HealthGoal.MAINTAIN_WEIGHT,
                        List.of()
                ),
                "Plan duration must be greater than zero."
        );
    }

    @Test
    void missingSexTest() {
        assertValidationFailure(
                new ProfileInputData(
                        25,
                        170,
                        70,
                        70,
                        30,
                        null,
                        ActivityLevel.MODERATELY_ACTIVE,
                        HealthGoal.MAINTAIN_WEIGHT,
                        List.of()
                ),
                "Please select a sex."
        );
    }

    @Test
    void missingActivityLevelTest() {
        assertValidationFailure(
                new ProfileInputData(
                        25,
                        170,
                        70,
                        70,
                        30,
                        Sex.FEMALE,
                        null,
                        HealthGoal.MAINTAIN_WEIGHT,
                        List.of()
                ),
                "Please select an activity level."
        );
    }

    @Test
    void missingHealthGoalTest() {
        assertValidationFailure(
                new ProfileInputData(
                        25,
                        170,
                        70,
                        70,
                        30,
                        Sex.FEMALE,
                        ActivityLevel.MODERATELY_ACTIVE,
                        null,
                        List.of()
                ),
                "Please select a health goal."
        );
    }

    @Test
    void under18CannotLoseWeightTest() {
        assertValidationFailure(
                new ProfileInputData(
                        17,
                        170,
                        70,
                        65,
                        30,
                        Sex.FEMALE,
                        ActivityLevel.MODERATELY_ACTIVE,
                        HealthGoal.LOSE_WEIGHT,
                        List.of()
                ),
                "Users under 18 can only create "
                        + "a maintenance plan."
        );
    }

    @Test
    void under18TargetMustEqualCurrentWeightTest() {
        assertValidationFailure(
                new ProfileInputData(
                        17,
                        170,
                        70,
                        71,
                        30,
                        Sex.FEMALE,
                        ActivityLevel.MODERATELY_ACTIVE,
                        HealthGoal.MAINTAIN_WEIGHT,
                        List.of()
                ),
                "For users under 18, target weight "
                        + "must equal current weight."
        );
    }

    @Test
    void noLoggedInUserTest() {
        FakeDataAccessObject dao =
                new FakeDataAccessObject(
                        null,
                        null);

        FakePresenter presenter =
                new FakePresenter();

        ProfileInteractor interactor =
                new ProfileInteractor(
                        dao,
                        presenter);

        interactor.execute(validInput());

        assertTrue(presenter.failCalled);

        assertEquals(
                "No user is currently logged in.",
                presenter.errorMessage);

        assertFalse(dao.saveCalled);
        assertFalse(presenter.successCalled);
    }

    @Test
    void currentUserNotFoundTest() {
        FakeDataAccessObject dao =
                new FakeDataAccessObject(
                        "nicole",
                        null);

        FakePresenter presenter =
                new FakePresenter();

        ProfileInteractor interactor =
                new ProfileInteractor(
                        dao,
                        presenter);

        interactor.execute(validInput());

        assertTrue(presenter.failCalled);

        assertEquals(
                "The current user could not be found.",
                presenter.errorMessage);

        assertFalse(dao.saveCalled);
        assertFalse(presenter.successCalled);
    }

    @Test
    void switchToHomeViewTest() {
        FakePresenter presenter =
                new FakePresenter();

        ProfileInteractor interactor =
                new ProfileInteractor(
                        new FakeDataAccessObject(
                                "nicole",
                                new FakeUser("nicole")),
                        presenter);

        interactor.switchToHomeView();

        assertTrue(
                presenter.switchToHomeCalled);
    }

    private static ProfileInputData validInput() {
        return new ProfileInputData(
                25,
                170.0,
                70.0,
                70.0,
                30,
                Sex.FEMALE,
                ActivityLevel.MODERATELY_ACTIVE,
                HealthGoal.MAINTAIN_WEIGHT,
                List.of("Vegetarian", "Halal")
        );
    }

    private static void assertValidationFailure(
            ProfileInputData inputData,
            String expectedMessage) {

        FakeUser user =
                new FakeUser("nicole");

        FakeDataAccessObject dao =
                new FakeDataAccessObject(
                        "nicole",
                        user);

        FakePresenter presenter =
                new FakePresenter();

        ProfileInteractor interactor =
                new ProfileInteractor(
                        dao,
                        presenter);

        interactor.execute(inputData);

        assertTrue(presenter.failCalled);

        assertEquals(
                expectedMessage,
                presenter.errorMessage);

        assertFalse(
                presenter.successCalled);

        assertFalse(
                dao.saveCalled);
    }

    private static class FakeDataAccessObject
            implements ProfileUserDataAccessInterface {

        private final String currentUsername;
        private final User user;

        private boolean saveCalled;

        FakeDataAccessObject(
                String currentUsername,
                User user) {

            this.currentUsername =
                    currentUsername;

            this.user = user;
        }

        @Override
        public String getCurrentUsername() {
            return currentUsername;
        }

        @Override
        public User get(String username) {
            return user;
        }

        @Override
        public void save(User user) {
            saveCalled = true;
        }
    }

    private static class FakePresenter
            implements ProfileOutputBoundary {

        private boolean successCalled;
        private boolean failCalled;
        private boolean switchToHomeCalled;

        private String errorMessage;
        private ProfileOutputData outputData;

        @Override
        public void prepareSuccessView(
                ProfileOutputData outputData) {

            successCalled = true;
            this.outputData = outputData;
        }

        @Override
        public void prepareFailView(
                String errorMessage) {

            failCalled = true;
            this.errorMessage = errorMessage;
        }

        @Override
        public void switchToHomeView() {
            switchToHomeCalled = true;
        }
    }

    private static class FakeUser
            implements User {

        private final String name;

        private String password;
        private UserProfile userProfile;
        private NutritionGoal nutritionGoal;

        private final Map<LocalDate, DailyLog>
                dailyLogs = new HashMap<>();

        FakeUser(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getPassword() {
            return password;
        }

        @Override
        public void setPassword(
                String password) {

            this.password = password;
        }

        @Override
        public UserProfile getUserProfile() {
            return userProfile;
        }

        @Override
        public void setUserProfile(
                UserProfile userProfile) {

            this.userProfile =
                    userProfile;
        }

        @Override
        public NutritionGoal getNutritionGoal() {
            return nutritionGoal;
        }

        @Override
        public void setNutritionGoal(
                NutritionGoal nutritionGoal) {

            this.nutritionGoal =
                    nutritionGoal;
        }

        @Override
        public DailyLog getDailyLog(
                LocalDate date) {

            return dailyLogs.get(date);
        }

        @Override
        public Map<LocalDate, DailyLog>
        getDailyLogs() {

            return dailyLogs;
        }
    }
    @Test
    void planDurationTooShortTest() {
        FakeUser user =
                new FakeUser("nicole");

        FakeDataAccessObject dao =
                new FakeDataAccessObject(
                        "nicole",
                        user);

        FakePresenter presenter =
                new FakePresenter();

        ProfileInteractor interactor =
                new ProfileInteractor(
                        dao,
                        presenter);

        ProfileInputData inputData =
                new ProfileInputData(
                        25,
                        170.0,
                        70.0,
                        70.0,
                        5,
                        Sex.FEMALE,
                        ActivityLevel.MODERATELY_ACTIVE,
                        HealthGoal.MAINTAIN_WEIGHT,
                        List.of()
                );

        interactor.execute(inputData);

        assertTrue(
                presenter.failCalled);

        assertEquals(
                "Plan duration must be between 14 and 180 days.",
                presenter.errorMessage);

        assertFalse(
                presenter.successCalled);

        assertFalse(
                dao.saveCalled);
    }
    @Test
    void invalidWeightLossDirectionTest() {
        FakeUser user =
                new FakeUser("nicole");

        FakeDataAccessObject dao =
                new FakeDataAccessObject(
                        "nicole",
                        user);

        FakePresenter presenter =
                new FakePresenter();

        ProfileInteractor interactor =
                new ProfileInteractor(
                        dao,
                        presenter);

        ProfileInputData inputData =
                new ProfileInputData(
                        25,
                        170.0,
                        70.0,
                        75.0,
                        90,
                        Sex.FEMALE,
                        ActivityLevel.MODERATELY_ACTIVE,
                        HealthGoal.LOSE_WEIGHT,
                        List.of()
                );

        interactor.execute(inputData);

        assertTrue(
                presenter.failCalled);

        assertEquals(
                "A weight-loss target must be below the starting weight.",
                presenter.errorMessage);

        assertFalse(
                presenter.successCalled);

        assertFalse(
                dao.saveCalled);
    }
}