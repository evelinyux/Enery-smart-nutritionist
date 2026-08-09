package enery.interface_adapter.profile;

import enery.entity.user.ActivityLevel;
import enery.entity.user.HealthGoal;
import enery.entity.user.Sex;
import enery.use_case.profile.ProfileInputBoundary;
import enery.use_case.profile.ProfileInputData;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProfileControllerTest {

    @Test
    void executeTest() {
        FakeInteractor fakeInteractor =
                new FakeInteractor();

        ProfileController controller =
                new ProfileController(
                        fakeInteractor);

        controller.execute(
                25,
                170.0,
                70.0,
                65.0,
                60,
                Sex.FEMALE,
                ActivityLevel.MODERATELY_ACTIVE,
                HealthGoal.LOSE_WEIGHT,
                List.of("Vegetarian", "Halal")
        );

        assertTrue(
                fakeInteractor.executeCalled);

        assertNotNull(
                fakeInteractor.inputData);

        assertEquals(
                25,
                fakeInteractor.inputData.getAge());

        assertEquals(
                170.0,
                fakeInteractor.inputData
                        .getHeightInCentimetres(),
                0.001);

        assertEquals(
                70.0,
                fakeInteractor.inputData
                        .getWeightInKilograms(),
                0.001);

        assertEquals(
                65.0,
                fakeInteractor.inputData
                        .getTargetWeightInKilograms(),
                0.001);

        assertEquals(
                60,
                fakeInteractor.inputData
                        .getDurationDays());

        assertEquals(
                Sex.FEMALE,
                fakeInteractor.inputData.getSex());

        assertEquals(
                ActivityLevel.MODERATELY_ACTIVE,
                fakeInteractor.inputData
                        .getActivityLevel());

        assertEquals(
                HealthGoal.LOSE_WEIGHT,
                fakeInteractor.inputData
                        .getHealthGoal());

        assertEquals(
                List.of("Vegetarian", "Halal"),
                fakeInteractor.inputData
                        .getDietaryRestrictions());
    }

    @Test
    void switchToHomeViewTest() {
        FakeInteractor fakeInteractor =
                new FakeInteractor();

        ProfileController controller =
                new ProfileController(
                        fakeInteractor);

        controller.switchToHomeView();

        assertTrue(
                fakeInteractor.switchCalled);
    }

    private static class FakeInteractor
            implements ProfileInputBoundary {

        private boolean executeCalled;
        private boolean switchCalled;

        private ProfileInputData inputData;

        @Override
        public void execute(
                ProfileInputData inputData) {

            executeCalled = true;
            this.inputData = inputData;
        }

        @Override
        public void switchToHomeView() {
            switchCalled = true;
        }
    }
}