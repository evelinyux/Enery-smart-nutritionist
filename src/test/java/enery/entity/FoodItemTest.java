package enery.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FoodItemTest {

    @Test
    void storesNutritionValues() {
        final FoodItem apple = new FoodItem(
                "Apple",
                95.0,
                0.5,
                25.0,
                0.3,
                4.4,
                19.0,
                2.0
        );

        assertEquals("Apple", apple.getName());
        assertEquals(95.0, apple.getCalories());
        assertEquals(0.5, apple.getProteinInGrams());
        assertEquals(25.0, apple.getCarbohydratesInGrams());
        assertEquals(0.3, apple.getFatInGrams());
        assertEquals(4.4, apple.getFiberInGrams());
        assertEquals(19.0, apple.getSugarInGrams());
        assertEquals(2.0, apple.getSodiumInMilligrams());
    }
}
