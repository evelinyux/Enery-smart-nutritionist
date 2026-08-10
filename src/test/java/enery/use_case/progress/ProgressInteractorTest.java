package enery.use_case.progress;

import java.util.List;

import enery.entity.user.ActivityLevel;
import enery.entity.user.HealthGoal;
import enery.entity.user.UserProfile;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProgressInteractorTest {

    private ProgressDataInterface dataAccessObject;
    private ProgressOutputBoundary presenter;
    private ProgressInteractor interactor;

    @BeforeEach
    void setUp() {
        dataAccessObject =
                mock(ProgressDataInterface.class);

        presenter =
                mock(ProgressOutputBoundary.class);

        interactor =
                new ProgressInteractor(
                        dataAccessObject,
                        presenter
                );
    }

    @Test
    void successTest() {
        final String username = "Ethan";

        final UserProfile profile =
                new UserProfile(
                        20,
                        173.0,
                        70.0,
                        ActivityLevel.MODERATELY_ACTIVE,
                        HealthGoal.MAINTAIN_WEIGHT,
                        List.of()
                );

        when(
                dataAccessObject.getUserProfile(username)
        ).thenReturn(profile);

        when(
                dataAccessObject.getTodaysCalories(username)
        ).thenReturn(1500.0);

        when(
                dataAccessObject.getDailyCalorieGoal(username)
        ).thenReturn(2000.0);

        when(
                dataAccessObject.getTodaysProtein(username)
        ).thenReturn(100.0);

        when(
                dataAccessObject.getTodaysCarbohydrates(username)
        ).thenReturn(180.0);

        when(
                dataAccessObject.getTodaysFats(username)
        ).thenReturn(60.0);

        when(
                dataAccessObject.getTotalCalorieGoal(username)
        ).thenReturn(14000.0);

        when(
                dataAccessObject.getTotalCaloriesConsumed(username)
        ).thenReturn(7000.0);

        when(
                dataAccessObject.getDaysSoFar(username)
        ).thenReturn(4);

        interactor.execute(
                new ProgressInputData(username)
        );

        final ArgumentCaptor<ProgressOutputData>
                captor =
                ArgumentCaptor.forClass(
                        ProgressOutputData.class
                );

        verify(presenter)
                .prepareSuccessView(
                        captor.capture()
                );

        verify(
                presenter,
                never()
        ).prepareFailView(
                any()
        );

        final ProgressOutputData outputData =
                captor.getValue();

        assertNotNull(outputData);

        assertEquals(
                1500.0,
                outputData.getTodaysCalories()
        );

        assertEquals(
                2000.0,
                outputData.getDailyCalorieGoal()
        );

        assertEquals(
                0.75,
                outputData.getDailyProgressPercent(),
                0.0001
        );

        assertEquals(
                100.0,
                outputData.getProteinInGrams()
        );

        assertEquals(
                180.0,
                outputData.getCarbohydratesInGrams()
        );

        assertEquals(
                60.0,
                outputData.getFatInGrams()
        );

        assertEquals(
                14000.0,
                outputData.getTotalCalorieGoal()
        );

        assertEquals(
                7000.0,
                outputData.getTotalCalories()
        );

        assertEquals(
                0.5,
                outputData.getTotalProgressPercent(),
                0.0001
        );

        assertEquals(
                4,
                outputData.getDaysSoFar()
        );
    }

    @Test
    void failsWhenUsernameNullTest() {
        interactor.execute(
                new ProgressInputData(null)
        );

        verify(presenter)
                .prepareFailView(
                        "A logged-in user is required."
                );

        verify(
                presenter,
                never()
        ).prepareSuccessView(
                any()
        );
    }

    @Test
    void failsWhenProfileMissingTest() {
        when(
                dataAccessObject.getUserProfile("Ethan")
        ).thenReturn(null);

        interactor.execute(
                new ProgressInputData("Ethan")
        );

        verify(presenter)
                .prepareFailView(
                        "A plan is required."
                );

        verify(
                presenter,
                never()
        ).prepareSuccessView(
                any()
        );
    }

    @Test
    void failsWhenDailyCalorieGoalZeroTest() {
        final UserProfile profile =
                mock(UserProfile.class);

        when(
                dataAccessObject.getUserProfile("Ethan")
        ).thenReturn(profile);

        when(
                dataAccessObject.getTodaysCalories("Ethan")
        ).thenReturn(500.0);

        when(
                dataAccessObject.getDailyCalorieGoal("Ethan")
        ).thenReturn(0.0);

        interactor.execute(
                new ProgressInputData("Ethan")
        );

        verify(presenter)
                .prepareFailView(
                        "Calorie Goal is 0."
                );

        verify(
                presenter,
                never()
        ).prepareSuccessView(
                any()
        );
    }

    @Test
    void failsWhenTotalCalorieGoalZeroTest() {
        final UserProfile profile =
                mock(UserProfile.class);

        when(
                dataAccessObject.getUserProfile("Ethan")
        ).thenReturn(profile);

        when(
                dataAccessObject.getTodaysCalories("Ethan")
        ).thenReturn(500.0);

        when(
                dataAccessObject.getDailyCalorieGoal("Ethan")
        ).thenReturn(2000.0);

        when(
                dataAccessObject.getTodaysProtein("Ethan")
        ).thenReturn(50.0);

        when(
                dataAccessObject.getTodaysCarbohydrates("Ethan")
        ).thenReturn(100.0);

        when(
                dataAccessObject.getTodaysFats("Ethan")
        ).thenReturn(30.0);

        when(
                dataAccessObject.getTotalCalorieGoal("Ethan")
        ).thenReturn(0.0);

        interactor.execute(
                new ProgressInputData("Ethan")
        );

        verify(presenter)
                .prepareFailView(
                        "Plan Calorie Goal is 0."
                );

        verify(
                presenter,
                never()
        ).prepareSuccessView(
                any()
        );
    }

    @Test
    void progressCanExceedOneHundredPercentTest() {
        final UserProfile profile =
                mock(UserProfile.class);

        when(
                dataAccessObject.getUserProfile("Ethan")
        ).thenReturn(profile);

        when(
                dataAccessObject.getTodaysCalories("Ethan")
        ).thenReturn(2500.0);

        when(
                dataAccessObject.getDailyCalorieGoal("Ethan")
        ).thenReturn(2000.0);

        when(
                dataAccessObject.getTodaysProtein("Ethan")
        ).thenReturn(100.0);

        when(
                dataAccessObject.getTodaysCarbohydrates("Ethan")
        ).thenReturn(200.0);

        when(
                dataAccessObject.getTodaysFats("Ethan")
        ).thenReturn(80.0);

        when(
                dataAccessObject.getTotalCalorieGoal("Ethan")
        ).thenReturn(10000.0);

        when(
                dataAccessObject.getTotalCaloriesConsumed("Ethan")
        ).thenReturn(12000.0);

        when(
                dataAccessObject.getDaysSoFar("Ethan")
        ).thenReturn(5);

        interactor.execute(
                new ProgressInputData("Ethan")
        );

        final ArgumentCaptor<ProgressOutputData>
                captor =
                ArgumentCaptor.forClass(
                        ProgressOutputData.class
                );

        verify(presenter)
                .prepareSuccessView(
                        captor.capture()
                );

        assertEquals(
                1.25,
                captor
                        .getValue()
                        .getDailyProgressPercent(),
                0.0001
        );

        assertEquals(
                1.2,
                captor
                        .getValue()
                        .getTotalProgressPercent(),
                0.0001
        );
    }
}
