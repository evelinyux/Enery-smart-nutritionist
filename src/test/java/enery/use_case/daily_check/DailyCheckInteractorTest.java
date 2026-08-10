package enery.use_case.daily_check;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import enery.entity.meal.MealType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DailyCheckTest {

    // ==========================================
    // 1. Test DailyCheckInputData
    // ==========================================

    @Test
    void testInputDataConstructorAndGetter() {
        final DailyCheckInputData inputData = new DailyCheckInputData("testUser");
        assertEquals("testUser", inputData.getUsername());
    }

    // ==========================================
    // 2. Test DailyCheckInputBoundary
    // ==========================================

    @Test
    void testInputBoundaryInterfaceContract() {
        // Anonymous implementation to verify the interface contract
        final boolean[] executed = {false};
        final DailyCheckInputBoundary boundary = inputData -> executed[0] = true;

        boundary.execute(new DailyCheckInputData("user"));
        assertTrue(executed[0]);
    }

    // ==========================================
    // 3. Test DailyCheckInteractor
    // ==========================================

    @Test
    void testInteractorConstructor() {
        final DailyCheckMealDataAccessInterface mealDAO = (username, date, mealType) -> false;
        final DailyCheckExerciseDataAccessInterface exerciseDAO = (username, date) -> false;
        final DailyCheckOutputBoundary presenter = new DummyPresenter();

        final DailyCheckInteractor interactor = new DailyCheckInteractor(mealDAO, exerciseDAO, presenter);
        assertNotNull(interactor);
    }

    @Test
    void testInteractorExecuteSuccessAllLogged() {
        final DailyCheckMealDataAccessInterface mealDAO = (username, date, mealType) -> true;
        final DailyCheckExerciseDataAccessInterface exerciseDAO = (username, date) -> true;

        final TestPresenter presenter = new TestPresenter();
        final DailyCheckInteractor interactor = new DailyCheckInteractor(mealDAO, exerciseDAO, presenter);

        interactor.execute(new DailyCheckInputData("validUser"));

        assertTrue(presenter.successCalled);
        assertFalse(presenter.failCalled);
        assertTrue(presenter.lastOutputData.isBreakfastLogged());
        assertTrue(presenter.lastOutputData.isLunchLogged());
        assertTrue(presenter.lastOutputData.isDinnerLogged());
        assertTrue(presenter.lastOutputData.isExerciseLogged());
    }

    @Test
    void testInteractorExecuteSuccessPartialLogged() {
        // Breakfast logged, Lunch/Dinner/Exercise missing
        final DailyCheckMealDataAccessInterface mealDAO = (username, date, mealType) -> mealType == MealType.BREAKFAST;
        final DailyCheckExerciseDataAccessInterface exerciseDAO = (username, date) -> false;

        final TestPresenter presenter = new TestPresenter();
        final DailyCheckInteractor interactor = new DailyCheckInteractor(mealDAO, exerciseDAO, presenter);

        interactor.execute(new DailyCheckInputData("validUser"));

        assertTrue(presenter.successCalled);
        assertTrue(presenter.lastOutputData.isBreakfastLogged());
        assertFalse(presenter.lastOutputData.isLunchLogged());
        assertFalse(presenter.lastOutputData.isDinnerLogged());
        assertFalse(presenter.lastOutputData.isExerciseLogged());
    }

    @Test
    void testInteractorExecuteNullUsernameFails() {
        final DailyCheckMealDataAccessInterface mealDAO = (username, date, mealType) -> false;
        final DailyCheckExerciseDataAccessInterface exerciseDAO = (username, date) -> false;

        final TestPresenter presenter = new TestPresenter();
        final DailyCheckInteractor interactor = new DailyCheckInteractor(mealDAO, exerciseDAO, presenter);

        interactor.execute(new DailyCheckInputData(null));

        assertTrue(presenter.failCalled);
        assertFalse(presenter.successCalled);
        assertEquals("User is not logged in.", presenter.lastErrorMessage);
    }

    @Test
    void testInteractorExecuteBlankUsernameFails() {
        final DailyCheckMealDataAccessInterface mealDAO = (username, date, mealType) -> false;
        final DailyCheckExerciseDataAccessInterface exerciseDAO = (username, date) -> false;

        final TestPresenter presenter = new TestPresenter();
        final DailyCheckInteractor interactor = new DailyCheckInteractor(mealDAO, exerciseDAO, presenter);

        interactor.execute(new DailyCheckInputData("   "));

        assertTrue(presenter.failCalled);
        assertFalse(presenter.successCalled);
        assertEquals("User is not logged in.", presenter.lastErrorMessage);
    }

    // ==========================================
    // Helper Test Classes
    // ==========================================

    private static class DummyPresenter implements DailyCheckOutputBoundary {
        @Override
        public void prepareSuccessView(DailyCheckOutputData outputData) {}

        @Override
        public void prepareFailView(String errorMessage) {}
    }

    private static class TestPresenter implements DailyCheckOutputBoundary {
        boolean successCalled = false;
        boolean failCalled = false;
        DailyCheckOutputData lastOutputData;
        String lastErrorMessage;

        @Override
        public void prepareSuccessView(DailyCheckOutputData outputData) {
            this.successCalled = true;
            this.lastOutputData = outputData;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            this.failCalled = true;
            this.lastErrorMessage = errorMessage;
        }
    }
}