package enery.entity.strategy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaintenanceCalorieStrategyTest {

    @Test
    void calculateTargetTest() {
        MaintenanceCalorieStrategy strategy =
                new MaintenanceCalorieStrategy();

        assertEquals(
                2000.0,
                strategy.calculateTarget(2000.0),
                0.001);
    }
}