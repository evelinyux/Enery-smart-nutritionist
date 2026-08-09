package enery.entity.strategy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WeightGainCalorieStrategyTest {

    @Test
    void calculateTargetTest() {
        WeightGainCalorieStrategy strategy =
                new WeightGainCalorieStrategy();

        assertEquals(
                2200.0,
                strategy.calculateTarget(2000.0),
                0.001);
    }
}