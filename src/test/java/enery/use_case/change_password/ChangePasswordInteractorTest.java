package enery.use_case.change_password;

import enery.entity.tracking.DailyLog;
import enery.entity.user.NutritionGoal;
import enery.entity.user.User;
import enery.entity.user.UserProfile;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ChangePasswordInteractorTest {

    @Test
    void successTest() {
        // Arrange
        FakeUser user = new FakeUser("nicole", "oldPassword");

        FakeDataAccessObject dataAccessObject =
                new FakeDataAccessObject(user);

        FakePresenter presenter =
                new FakePresenter();

        ChangePasswordInteractor interactor =
                new ChangePasswordInteractor(
                        dataAccessObject,
                        presenter);

        ChangePasswordInputData inputData =
                new ChangePasswordInputData(
                        "nicole",
                        "newPassword123");

        // Act
        interactor.execute(inputData);

        // Assert
        assertEquals("newPassword123", user.getPassword());

        assertTrue(dataAccessObject.changePasswordCalled);

        assertTrue(presenter.successCalled);
        assertFalse(presenter.failCalled);

        assertNotNull(presenter.outputData);
        assertEquals(
                "nicole",
                presenter.outputData.getUsername());

        assertFalse(
                presenter.outputData.isUseCaseFailed());
    }

    @Test
    void userDoesNotExistTest() {
        // Arrange
        FakeDataAccessObject dataAccessObject =
                new FakeDataAccessObject(null);

        FakePresenter presenter =
                new FakePresenter();

        ChangePasswordInteractor interactor =
                new ChangePasswordInteractor(
                        dataAccessObject,
                        presenter);

        ChangePasswordInputData inputData =
                new ChangePasswordInputData(
                        "missingUser",
                        "newPassword123");

        // Act
        interactor.execute(inputData);

        // Assert
        assertTrue(presenter.failCalled);
        assertFalse(presenter.successCalled);

        assertEquals(
                "User does not exist.",
                presenter.errorMessage);

        assertFalse(
                dataAccessObject.changePasswordCalled);
    }

    /**
     * Fake DAO used only for testing.
     */
    private static class FakeDataAccessObject
            implements ChangePasswordUserDataAccessInterface {

        private final User user;
        private boolean changePasswordCalled;

        FakeDataAccessObject(User user) {
            this.user = user;
        }

        @Override
        public User get(String username) {
            return user;
        }

        @Override
        public void changePassword(User user) {
            changePasswordCalled = true;
        }
    }

    /**
     * Fake presenter used only for testing.
     */
    private static class FakePresenter
            implements ChangePasswordOutputBoundary {

        private boolean successCalled;
        private boolean failCalled;
        private String errorMessage;
        private ChangePasswordOutputData outputData;

        @Override
        public void prepareSuccessView(
                ChangePasswordOutputData outputData) {

            successCalled = true;
            this.outputData = outputData;
        }

        @Override
        public void prepareFailView(
                String errorMessage) {

            failCalled = true;
            this.errorMessage = errorMessage;
        }
    }

    /**
     * Minimal fake User used only for this test.
     */
    private static class FakeUser implements User {

        private final String name;
        private String password;
        private UserProfile userProfile;
        private NutritionGoal nutritionGoal;

        private final Map<LocalDate, DailyLog> dailyLogs =
                new HashMap<>();

        FakeUser(String name, String password) {
            this.name = name;
            this.password = password;
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
        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public UserProfile getUserProfile() {
            return userProfile;
        }

        @Override
        public void setUserProfile(
                UserProfile userProfile) {

            this.userProfile = userProfile;
        }

        @Override
        public NutritionGoal getNutritionGoal() {
            return nutritionGoal;
        }

        @Override
        public void setNutritionGoal(
                NutritionGoal nutritionGoal) {

            this.nutritionGoal = nutritionGoal;
        }

        @Override
        public DailyLog getDailyLog(LocalDate date) {
            return dailyLogs.get(date);
        }

        @Override
        public Map<LocalDate, DailyLog> getDailyLogs() {
            return dailyLogs;
        }
    }
}