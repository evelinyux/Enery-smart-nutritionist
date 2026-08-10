package enery.use_case.search_exercise;

import java.util.List;

import enery.entity.exercise.ExerciseSearchResult;
import enery.entity.user.ActivityLevel;
import enery.entity.user.HealthGoal;
import enery.entity.user.UserProfile;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SearchExerciseInteractorTest {

    private CaloriesBurnedDataAccessInterface caloriesDataAccess;
    private SearchExerciseUserDataAccessInterface userDataAccess;
    private SearchExerciseOutputBoundary presenter;
    private SearchExerciseInteractor interactor;

    @BeforeEach
    void setUp() {
        caloriesDataAccess =
                mock(CaloriesBurnedDataAccessInterface.class);

        userDataAccess =
                mock(SearchExerciseUserDataAccessInterface.class);

        presenter =
                mock(SearchExerciseOutputBoundary.class);

        interactor =
                new SearchExerciseInteractor(
                        caloriesDataAccess,
                        userDataAccess,
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
                userDataAccess.getUserProfile(username)
        ).thenReturn(profile);

        final ExerciseSearchResult result =
                new ExerciseSearchResult(
                        "Running",
                        300.0,
                        30,
                        300.0
                );

        when(
                caloriesDataAccess.search(
                        anyString(),
                        anyDouble(),
                        anyInt()
                )
        ).thenReturn(
                List.of(result)
        );

        interactor.execute(
                new SearchExerciseInputData(
                        username,
                        "Running",
                        30
                )
        );

        final ArgumentCaptor<SearchExerciseOutputData>
                outputCaptor =
                ArgumentCaptor.forClass(
                        SearchExerciseOutputData.class
                );

        verify(presenter)
                .prepareSuccessView(
                        outputCaptor.capture()
                );

        verify(
                presenter,
                never()
        ).prepareFailView(
                anyString()
        );

        final SearchExerciseOutputData outputData =
                outputCaptor.getValue();

        assertNotNull(outputData);
        assertEquals(
                1,
                outputData.getResults().size()
        );

        assertEquals(
                "Running",
                outputData
                        .getResults()
                        .get(0)
                        .getExerciseName()
        );
    }

    @Test
    void convertsKilogramsToPoundsTest() {
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
                userDataAccess.getUserProfile(username)
        ).thenReturn(profile);

        when(
                caloriesDataAccess.search(
                        anyString(),
                        anyDouble(),
                        anyInt()
                )
        ).thenReturn(
                List.of()
        );

        interactor.execute(
                new SearchExerciseInputData(
                        username,
                        "Cycling",
                        45
                )
        );

        final ArgumentCaptor<Double>
                weightCaptor =
                ArgumentCaptor.forClass(
                        Double.class
                );

        verify(caloriesDataAccess)
                .search(
                        eq("Cycling"),
                        weightCaptor.capture(),
                        eq(45)
                );

        final double expectedWeight =
                70.0 * 2.2046226218;

        assertEquals(
                expectedWeight,
                weightCaptor.getValue(),
                0.0001
        );
    }

    @Test
    void failsWhenUsernameNullTest() {
        interactor.execute(
                new SearchExerciseInputData(
                        null,
                        "Running",
                        30
                )
        );

        verify(presenter)
                .prepareFailView(
                        "A logged-in user is required."
                );

        verify(
                caloriesDataAccess,
                never()
        ).search(
                anyString(),
                anyDouble(),
                anyInt()
        );
    }

    @Test
    void failsWhenUsernameBlankTest() {
        interactor.execute(
                new SearchExerciseInputData(
                        "   ",
                        "Running",
                        30
                )
        );

        verify(presenter)
                .prepareFailView(
                        "A logged-in user is required."
                );
    }

    @Test
    void failsWhenExerciseNameNullTest() {
        interactor.execute(
                new SearchExerciseInputData(
                        "Ethan",
                        null,
                        30
                )
        );

        verify(presenter)
                .prepareFailView(
                        "Please enter an exercise."
                );
    }

    @Test
    void failsWhenExerciseNameBlankTest() {
        interactor.execute(
                new SearchExerciseInputData(
                        "Ethan",
                        "   ",
                        30
                )
        );

        verify(presenter)
                .prepareFailView(
                        "Please enter an exercise."
                );
    }

    @Test
    void failsWhenDurationZeroTest() {
        interactor.execute(
                new SearchExerciseInputData(
                        "Ethan",
                        "Running",
                        0
                )
        );

        verify(presenter)
                .prepareFailView(
                        "Duration must be greater than 0."
                );
    }

    @Test
    void failsWhenDurationNegativeTest() {
        interactor.execute(
                new SearchExerciseInputData(
                        "Ethan",
                        "Running",
                        -10
                )
        );

        verify(presenter)
                .prepareFailView(
                        "Duration must be greater than 0."
                );
    }

    @Test
    void failsWhenProfileMissingTest() {
        when(
                userDataAccess.getUserProfile("Ethan")
        ).thenReturn(null);

        interactor.execute(
                new SearchExerciseInputData(
                        "Ethan",
                        "Running",
                        30
                )
        );

        verify(presenter)
                .prepareFailView(
                        "Please complete your profile "
                                + "before searching exercises."
                );

        verify(
                caloriesDataAccess,
                never()
        ).search(
                anyString(),
                anyDouble(),
                anyInt()
        );
    }

    @Test
    void failsWhenApiThrowsExceptionTest() {
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
                userDataAccess.getUserProfile("Ethan")
        ).thenReturn(profile);

        when(
                caloriesDataAccess.search(
                        anyString(),
                        anyDouble(),
                        anyInt()
                )
        ).thenThrow(
                new RuntimeException(
                        "API unavailable"
                )
        );

        interactor.execute(
                new SearchExerciseInputData(
                        "Ethan",
                        "Running",
                        30
                )
        );

        verify(presenter)
                .prepareFailView(
                        "Unable to retrieve exercise information."
                );

        verify(
                presenter,
                never()
        ).prepareSuccessView(
                any()
        );
    }
}
