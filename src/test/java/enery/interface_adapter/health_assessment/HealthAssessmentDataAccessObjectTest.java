package enery.interface_adapter.health_assessment;

import enery.data_access.HealthAssessment_database.HealthAssessmentDataAccessObject;
import enery.entity.meal.MealEntry;
import enery.use_case.tracking.TrackingDataAccessInterface;
import enery.use_case.tracking.TrackingOutputData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;

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

        MealEntry meal1 = mock(MealEntry.class);
        when(meal1.getTotalCalories()).thenReturn(500.0);
        when(meal1.getTotalProteinInGrams()).thenReturn(30.0);
        when(meal1.getTotalCarbohydratesInGrams()).thenReturn(40.0);
        when(meal1.getTotalFatInGrams()).thenReturn(10.0);

        MealEntry meal2 = mock(MealEntry.class);
        when(meal2.getTotalCalories()).thenReturn(300.0);
        when(meal2.getTotalProteinInGrams()).thenReturn(20.0);
        when(meal2.getTotalCarbohydratesInGrams()).thenReturn(30.0);
        when(meal2.getTotalFatInGrams()).thenReturn(5.0);


        when(mockTrackingDAO.getMeals(userId, start, end)).thenReturn(Arrays.asList(meal1, meal2));

        TrackingOutputData result = healthDAO.getTrackingData(userId, start, end);

        assertEquals(800.0, result.getCaloriesConsumed());
        assertEquals(200.0, result.getCaloriesBurned());
        assertEquals(600.0, result.getNetCalories());
        assertEquals(50.0, result.getProteinInGrams());
        assertEquals(70.0, result.getCarbohydratesInGrams());
        assertEquals(15.0, result.getFatInGrams());
    }
}
