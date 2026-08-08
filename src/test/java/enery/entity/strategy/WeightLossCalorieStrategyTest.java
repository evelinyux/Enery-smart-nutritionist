package enery.entity.strategy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WeightLossCalorieStrategyTest {

    @Test
    void calculateTargetTest() {
        WeightLossCalorieStrategy strategy =
                new WeightLossCalorieStrategy();

        assertEquals(
                1800.0,
                strategy.calculateTarget(2000.0),
                0.001);
    }
}