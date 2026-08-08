package enery.use_case.forgot_password;

import enery.entity.tracking.DailyLog;
import enery.entity.user.NutritionGoal;
import enery.entity.user.User;
import enery.entity.user.UserProfile;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ForgotPasswordInteractorTest {

    @Test
    void successTest() {
        FakeUser user =
                new FakeUser("nicole", "oldPassword");

        FakeDataAccessObject dao =
                new FakeDataAccessObject(user);

        FakePresenter presenter =
                new FakePresenter();

        ForgotPasswordInteractor interactor =
                new ForgotPasswordInteractor(
                        dao,
                        presenter);

        ForgotPasswordInputData inputData =
                new ForgotPasswordInputData(
                        "nicole",
                        "newPassword123",
                        "newPassword123");

        interactor.execute(inputData);

        assertEquals(
                "newPassword123",
                user.getPassword());

        assertTrue(
                dao.changePasswordCalled);

        assertTrue(
                presenter.successCalled);

        assertFalse(
                presenter.failCalled);

        assertNotNull(
                presenter.outputData);

        assertEquals(
                "nicole",
                presenter.outputData.getUsername());
    }

    @Test
    void emptyUsernameTest() {
        FakeDataAccessObject dao =
                new FakeDataAccessObject(null);

        FakePresenter presenter =
                new FakePresenter();

        ForgotPasswordInteractor interactor =
                new ForgotPasswordInteractor(
                        dao,
                        presenter);

        interactor.execute(
                new ForgotPasswordInputData(
                        "",
                        "password",
                        "password"));

        assertTrue(presenter.failCalled);

        assertEquals(
                "Username cannot be empty.",
                presenter.errorMessage);

        assertFalse(
                presenter.successCalled);

        assertFalse(
                dao.changePasswordCalled);
    }

    @Test
    void blankUsernameTest() {
        FakeDataAccessObject dao =
                new FakeDataAccessObject(null);

        FakePresenter presenter =
                new FakePresenter();

        ForgotPasswordInteractor interactor =
                new ForgotPasswordInteractor(
                        dao,
                        presenter);

        interactor.execute(
                new ForgotPasswordInputData(
                        "   ",
                        "password",
                        "password"));

        assertTrue(presenter.failCalled);

        assertEquals(
                "Username cannot be empty.",
                presenter.errorMessage);
    }

    @Test
    void userDoesNotExistTest() {
        FakeDataAccessObject dao =
                new FakeDataAccessObject(null);

        FakePresenter presenter =
                new FakePresenter();

        ForgotPasswordInteractor interactor =
                new ForgotPasswordInteractor(
                        dao,
                        presenter);

        interactor.execute(
                new ForgotPasswordInputData(
                        "missingUser",
                        "password",
                        "password"));

        assertTrue(presenter.failCalled);

        assertEquals(
                "User does not exist.",
                presenter.errorMessage);

        assertFalse(
                presenter.successCalled);

        assertFalse(
                dao.changePasswordCalled);
    }

    @Test
    void emptyNewPasswordTest() {
        FakeUser user =
                new FakeUser("nicole", "oldPassword");

        FakeDataAccessObject dao =
                new FakeDataAccessObject(user);

        FakePresenter presenter =
                new FakePresenter();

        ForgotPasswordInteractor interactor =
                new ForgotPasswordInteractor(
                        dao,
                        presenter);

        interactor.execute(
                new ForgotPasswordInputData(
                        "nicole",
                        "",
                        ""));

        assertTrue(presenter.failCalled);

        assertEquals(
                "New password cannot be empty.",
                presenter.errorMessage);

        assertFalse(
                dao.changePasswordCalled);
    }

    @Test
    void passwordsDoNotMatchTest() {
        FakeUser user =
                new FakeUser("nicole", "oldPassword");

        FakeDataAccessObject dao =
                new FakeDataAccessObject(user);

        FakePresenter presenter =
                new FakePresenter();

        ForgotPasswordInteractor interactor =
                new ForgotPasswordInteractor(
                        dao,
                        presenter);

        interactor.execute(
                new ForgotPasswordInputData(
                        "nicole",
                        "newPassword",
                        "differentPassword"));

        assertTrue(presenter.failCalled);

        assertEquals(
                "Passwords do not match.",
                presenter.errorMessage);

        assertFalse(
                dao.changePasswordCalled);

        assertEquals(
                "oldPassword",
                user.getPassword());
    }

    @Test
    void switchToLoginViewTest() {
        FakePresenter presenter =
                new FakePresenter();

        ForgotPasswordInteractor interactor =
                new ForgotPasswordInteractor(
                        new FakeDataAccessObject(null),
                        presenter);

        interactor.switchToLoginView();

        assertTrue(
                presenter.switchToLoginCalled);
    }

    private static class FakeDataAccessObject
            implements ForgotPasswordUserDataAccessInterface {

        private final User user;

        private boolean changePasswordCalled;

        FakeDataAccessObject(User user) {
            this.user = user;
        }

        @Override
        public boolean existsByName(String username) {
            return user != null
                    && user.getName().equals(username);
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

    private static class FakePresenter
            implements ForgotPasswordOutputBoundary {

        private boolean successCalled;
        private boolean failCalled;
        private boolean switchToLoginCalled;

        private String errorMessage;
        private ForgotPasswordOutputData outputData;

        @Override
        public void prepareSuccessView(
                ForgotPasswordOutputData outputData) {

            successCalled = true;
            this.outputData = outputData;
        }

        @Override
        public void prepareFailView(
                String error) {

            failCalled = true;
            errorMessage = error;
        }

        @Override
        public void switchToLoginView() {
            switchToLoginCalled = true;
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

        FakeUser(
                String name,
                String password) {

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
}