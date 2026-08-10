package enery.interface_adapter.health_assessment;

import enery.data_access.HealthAssessment_database.HealthAssessmentDataAccessObject;
import enery.entity.exercise.ExerciseEntry;
import enery.entity.meal.MealEntry;
import enery.use_case.tracking.TrackingDataAccessInterface;
import enery.use_case.tracking.TrackingOutputData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HealthAssessmentDataAccessObjectTest {

    private TrackingDataAccessInterface mockTrackingDAO;
    private HealthAssessmentDataAccessObject healthDAO;

    @BeforeEach
    void setUp() {
        mockTrackingDAO = mock(TrackingDataAccessInterface.class);
        healthDAO = new HealthAssessmentDataAccessObject(mockTrackingDAO);
    }

    @Test
    void testGetTrackingData_CalculatesTotalsCorrectly() {
        String userId = "testUser";
        LocalDate start = LocalDate.now().minusDays(6);
        LocalDate end = LocalDate.now();

        enery.entity.meal.FoodItem food1 = new enery.entity.meal.FoodItem(
                "LunchFood",
                100.0,  // servingSizeInGrams
                500.0,  // calories
                30.0,   // protein
                40.0,   // carbs
                10.0,   // fat
                0.0,    // fiber
                0.0,    // sugar
                0.0     // sodium
        );
        MealEntry meal1 = new MealEntry(food1, null, 1.0, start);

        enery.entity.meal.FoodItem food2 = new enery.entity.meal.FoodItem(
                "DinnerFood",
                100.0,  // servingSizeInGrams
                300.0,  // calories
                20.0,   // protein
                30.0,   // carbs
                5.0,    // fat
                0.0,    // fiber
                0.0,    // sugar
                0.0     // sodium
        );
        MealEntry meal2 = new MealEntry(food2, null, 1.0, start);

        when(mockTrackingDAO.getMeals(userId, start, end))
                .thenReturn(Arrays.asList(meal1, meal2));

        TrackingOutputData result = healthDAO.getTrackingData(userId, start, end);

        assertEquals(800.0, result.getCaloriesConsumed());
        assertEquals(50.0, result.getProteinInGrams());
        assertEquals(70.0, result.getCarbohydratesInGrams());
        assertEquals(15.0, result.getFatInGrams());
    }
}