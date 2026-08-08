package enery.entity.healthAssessment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class DietaryPatternAssessmentTest {

    private DietaryPatternAssessment assessment;

    @BeforeEach
    void setUp() {
        assessment = new DietaryPatternAssessment("test-id-123", "user-yuxin");
    }

    @Test
    void testInitialization() {
        assertEquals("test-id-123", assessment.getAssessmentId());
        assertEquals("user-yuxin", assessment.getUserId());
        assertEquals(0, assessment.getDietScore());
        assertTrue(assessment.getDietInsights().isEmpty());
    }

    @Test
    void testAddDietInsight_ValidString() {
        assessment.addDietInsight("Protein intake is good.");
        List<String> insights = assessment.getDietInsights();
        assertEquals(1, insights.size());
        assertEquals("Protein intake is good.", insights.get(0));
    }

    @Test
    void testAddDietInsight_NullOrEmptyString_ShouldNotAdd() {
        assessment.addDietInsight(null);
        assessment.addDietInsight("");
        assessment.addDietInsight("   ");
        assertTrue(assessment.getDietInsights().isEmpty());
    }

    @Test
    void testGetListsAreDefensiveCopies() {
        assessment.addDietInsight("Insight 1");
        List<String> insights = assessment.getDietInsights();
        insights.add("Malicious Insight");
        assertEquals(1, assessment.getDietInsights().size());
    }
}