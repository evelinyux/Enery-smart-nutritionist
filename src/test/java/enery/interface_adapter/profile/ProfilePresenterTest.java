package enery.interface_adapter.profile;

import enery.entity.user.ActivityLevel;
import enery.entity.user.HealthGoal;
import enery.entity.user.Sex;
import enery.entity.user.UserProfile;
import enery.entity.user.WeightGoalPlan;
import enery.interface_adapter.ViewManagerModel;
import enery.use_case.profile.ProfileOutputData;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProfilePresenterTest {

    @Test
    void prepareSuccessViewWithActivePlanTest() {
        // Arrange
        ViewManagerModel viewManagerModel =
                new ViewManagerModel();

        ProfileViewModel profileViewModel =
                new ProfileViewModel();

        List<PropertyChangeEvent> events =
                new ArrayList<>();

        profileViewModel.addPropertyChangeListener(
                events::add);

        ProfilePresenter presenter =
                new ProfilePresenter(
                        viewManagerModel,
                        profileViewModel);

        UserProfile userProfile =
                new UserProfile(
                        25,
                        170.0,
                        70.0,
                        Sex.FEMALE,
                        ActivityLevel.MODERATELY_ACTIVE,
                        HealthGoal.LOSE_WEIGHT,
                        List.of("Vegetarian", "Halal")
                );

        LocalDate startDate =
                LocalDate.of(2026, 8, 8);

        WeightGoalPlan plan =
                new WeightGoalPlan(
                        startDate,
                        60,
                        70.0,
                        65.0,
                        HealthGoal.LOSE_WEIGHT,
                        2000.0
                );

        userProfile.setActivePlan(plan);

        ProfileOutputData outputData =
                new ProfileOutputData(
                        "nicole",
                        userProfile,
                        false);

        // Act
        presenter.prepareSuccessView(outputData);

        // Assert
        ProfileState state =
                profileViewModel.getState();

        assertEquals("nicole",
                state.getUsername());

        assertEquals(25,
                state.getAge());

        assertEquals(170.0,
                state.getHeightInCentimetres(),
                0.001);

        assertEquals(70.0,
                state.getWeightInKilograms(),
                0.001);

        assertEquals(Sex.FEMALE,
                state.getSex());

        assertEquals(
                ActivityLevel.MODERATELY_ACTIVE,
                state.getActivityLevel());

        assertEquals(
                HealthGoal.LOSE_WEIGHT,
                state.getHealthGoal());

        assertEquals(
                List.of("Vegetarian", "Halal"),
                state.getDietaryRestrictions());

        assertEquals(65.0,
                state.getTargetWeightInKilograms(),
                0.001);

        assertEquals(60,
                state.getDurationDays());

        assertEquals(
                "2026-08-08",
                state.getStartDate());

        assertEquals(
                "2026-10-06",
                state.getEndDate());

        assertEquals(
                2000.0,
                state.getDailyCalorieGoal(),
                0.001);

        assertNull(
                state.getProfileError());

        assertEquals(
                "Profile and plan saved successfully.",
                state.getSuccessMessage());

        assertEquals(1, events.size());

        assertEquals(
                "state",
                events.get(0).getPropertyName());
    }

    @Test
    void prepareSuccessViewWithoutActivePlanTest() {
        ViewManagerModel viewManagerModel =
                new ViewManagerModel();

        ProfileViewModel profileViewModel =
                new ProfileViewModel();

        ProfilePresenter presenter =
                new ProfilePresenter(
                        viewManagerModel,
                        profileViewModel);

        UserProfile userProfile =
                new UserProfile(
                        25,
                        170.0,
                        70.0,
                        Sex.FEMALE,
                        ActivityLevel.MODERATELY_ACTIVE,
                        HealthGoal.MAINTAIN_WEIGHT,
                        List.of()
                );

        ProfileOutputData outputData =
                new ProfileOutputData(
                        "nicole",
                        userProfile,
                        false);

        presenter.prepareSuccessView(outputData);

        ProfileState state =
                profileViewModel.getState();

        assertEquals(
                0.0,
                state.getTargetWeightInKilograms(),
                0.001);

        assertEquals(
                0,
                state.getDurationDays());

        assertEquals(
                "",
                state.getStartDate());

        assertEquals(
                "",
                state.getEndDate());

        assertNull(
                state.getDailyCalorieGoal());
    }

    @Test
    void prepareFailViewTest() {
        ViewManagerModel viewManagerModel =
                new ViewManagerModel();

        ProfileViewModel profileViewModel =
                new ProfileViewModel();

        profileViewModel.getState()
                .setSuccessMessage("old success");

        List<PropertyChangeEvent> events =
                new ArrayList<>();

        profileViewModel.addPropertyChangeListener(
                events::add);

        ProfilePresenter presenter =
                new ProfilePresenter(
                        viewManagerModel,
                        profileViewModel);

        presenter.prepareFailView(
                "Invalid profile.");

        ProfileState state =
                profileViewModel.getState();

        assertEquals(
                "Invalid profile.",
                state.getProfileError());

        assertNull(
                state.getSuccessMessage());

        assertEquals(
                1,
                events.size());

        assertEquals(
                "state",
                events.get(0).getPropertyName());
    }

    @Test
    void switchToHomeViewTest() {
        ViewManagerModel viewManagerModel =
                new ViewManagerModel();

        ProfileViewModel profileViewModel =
                new ProfileViewModel();

        List<PropertyChangeEvent> events =
                new ArrayList<>();

        viewManagerModel.addPropertyChangeListener(
                events::add);

        ProfilePresenter presenter =
                new ProfilePresenter(
                        viewManagerModel,
                        profileViewModel);

        presenter.switchToHomeView();

        assertEquals(
                "home",
                viewManagerModel.getState());

        assertEquals(
                1,
                events.size());

        assertEquals(
                "state",
                events.get(0).getPropertyName());
    }
}