package enery.view.profile;

import enery.entity.user.ActivityLevel;
import enery.entity.user.HealthGoal;
import enery.entity.user.Sex;
import enery.interface_adapter.profile.ProfileController;
import enery.interface_adapter.profile.ProfileState;
import enery.interface_adapter.profile.ProfileViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Component;
import java.awt.Container;
import java.beans.PropertyChangeEvent;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ProfileViewTest {

    private ProfileViewModel viewModel;
    private ProfileController controller;
    private ProfileView view;

    @BeforeEach
    void setUp() {
        viewModel = mock(ProfileViewModel.class);
        controller = mock(ProfileController.class);

        view = new ProfileView(viewModel);
    }

    @Test
    void getViewNameTest() {
        assertEquals("profile", view.getViewName());
        assertEquals(ProfileView.VIEW_NAME, view.getViewName());
    }

    @Test
    void constructorRegistersListenerTest() {
        verify(viewModel).addPropertyChangeListener(view);
    }

    @Test
    void saveWithoutControllerTest() {
        JButton save =
                findButton(view, "Save Profile and Plan");

        assertNotNull(save);

        save.doClick();

        JLabel status =
                getField(view, "statusLabel", JLabel.class);

        assertEquals(
                "Profile controller is not configured.",
                status.getText()
        );
    }

    @Test
    void backWithoutControllerTest() {
        JButton back =
                findButton(view, "Back to Home");

        assertNotNull(back);

        back.doClick();

        JLabel status =
                getField(view, "statusLabel", JLabel.class);

        assertEquals(
                "Profile controller is not configured.",
                status.getText()
        );
    }

    @Test
    void backWithControllerTest() {
        view.setProfileController(controller);

        JButton back =
                findButton(view, "Back to Home");

        assertNotNull(back);

        back.doClick();

        verify(controller).switchToHomeView();
    }

    @Test
    void saveValidProfileTest() {
        view.setProfileController(controller);

        getField(view, "ageField", JTextField.class)
                .setText("20");

        getField(view, "heightField", JTextField.class)
                .setText("175.5");

        getField(view, "weightField", JTextField.class)
                .setText("70.0");

        getField(view, "targetWeightField", JTextField.class)
                .setText("65.0");

        getField(view, "durationDaysField", JTextField.class)
                .setText("90");

        JButton save =
                findButton(view, "Save Profile and Plan");

        assertNotNull(save);

        save.doClick();

        verify(controller).execute(
                eq(20),
                eq(175.5),
                eq(70.0),
                eq(65.0),
                eq(90),
                any(Sex.class),
                any(ActivityLevel.class),
                any(HealthGoal.class),
                eq(Collections.emptyList())
        );
    }

    @Test
    void saveAllDietaryRestrictionsTest() {
        view.setProfileController(controller);

        getField(view, "ageField", JTextField.class)
                .setText("22");

        getField(view, "heightField", JTextField.class)
                .setText("180");

        getField(view, "weightField", JTextField.class)
                .setText("80");

        getField(view, "targetWeightField", JTextField.class)
                .setText("75");

        getField(view, "durationDaysField", JTextField.class)
                .setText("100");

        getField(
                view,
                "vegetarianBox",
                JCheckBox.class
        ).setSelected(true);

        getField(
                view,
                "veganBox",
                JCheckBox.class
        ).setSelected(true);

        getField(
                view,
                "glutenFreeBox",
                JCheckBox.class
        ).setSelected(true);

        getField(
                view,
                "dairyFreeBox",
                JCheckBox.class
        ).setSelected(true);

        getField(
                view,
                "halalBox",
                JCheckBox.class
        ).setSelected(true);

        JButton save =
                findButton(view, "Save Profile and Plan");

        assertNotNull(save);

        save.doClick();

        verify(controller).execute(
                eq(22),
                eq(180.0),
                eq(80.0),
                eq(75.0),
                eq(100),
                any(Sex.class),
                any(ActivityLevel.class),
                any(HealthGoal.class),
                eq(Arrays.asList(
                        "Vegetarian",
                        "Vegan",
                        "Gluten Free",
                        "Dairy Free",
                        "Halal"
                ))
        );
    }

    @Test
    void invalidEmptyNumbersTest() {
        view.setProfileController(controller);

        JButton save =
                findButton(view, "Save Profile and Plan");

        assertNotNull(save);

        save.doClick();

        JLabel status =
                getField(view, "statusLabel", JLabel.class);

        assertEquals(
                "Age and plan duration must be whole numbers, "
                        + "and height and weights must be valid numbers.",
                status.getText()
        );

        verifyNoInteractions(controller);
    }

    @Test
    void numericIntegerFilterRejectsLettersTest() {
        JTextField age =
                getField(view, "ageField", JTextField.class);

        age.setText("25");

        assertEquals("25", age.getText());

        age.setText("abc");

        // Invalid replacement should be rejected.
        assertEquals("25", age.getText());
    }

    @Test
    void decimalFilterAcceptsDecimalTest() {
        JTextField height =
                getField(view, "heightField", JTextField.class);

        height.setText("175.5");

        assertEquals("175.5", height.getText());
    }

    @Test
    void decimalFilterRejectsLettersTest() {
        JTextField height =
                getField(view, "heightField", JTextField.class);

        height.setText("175.5");
        height.setText("hello");

        assertEquals("175.5", height.getText());
    }

    @Test
    void integerFilterRejectsDecimalTest() {
        JTextField duration =
                getField(
                        view,
                        "durationDaysField",
                        JTextField.class
                );

        duration.setText("30");

        assertEquals("30", duration.getText());

        duration.setText("30.5");

        assertEquals("30", duration.getText());
    }

    @Test
    void numericFieldsCanBeClearedTest() {
        JTextField age =
                getField(view, "ageField", JTextField.class);

        age.setText("20");

        assertEquals("20", age.getText());

        age.setText("");

        assertEquals("", age.getText());
    }

    @Test
    void ignoresWrongPropertyNameTest() {
        view.propertyChange(
                new PropertyChangeEvent(
                        viewModel,
                        "other",
                        null,
                        null
                )
        );

        verify(viewModel, never()).getState();
    }

    @Test
    void profileErrorTest() {
        ProfileState state = mock(ProfileState.class);

        when(viewModel.getState()).thenReturn(state);
        when(state.getProfileError())
                .thenReturn("Invalid profile");

        view.propertyChange(
                new PropertyChangeEvent(
                        viewModel,
                        "state",
                        null,
                        state
                )
        );

        JLabel status =
                getField(view, "statusLabel", JLabel.class);

        assertEquals(
                "Invalid profile",
                status.getText()
        );
    }

    @Test
    void profileSuccessMessageTest() {
        ProfileState state = createEmptyState();

        when(state.getSuccessMessage())
                .thenReturn("Profile saved");

        when(viewModel.getState()).thenReturn(state);

        view.propertyChange(
                new PropertyChangeEvent(
                        viewModel,
                        "state",
                        null,
                        state
                )
        );

        JLabel status =
                getField(view, "statusLabel", JLabel.class);

        assertEquals(
                "Profile saved",
                status.getText()
        );
    }

    @Test
    void completeProfileStateUpdatesFieldsTest() {
        ProfileState state = createEmptyState();

        when(state.getAge()).thenReturn(21);
        when(state.getHeightInCentimetres())
                .thenReturn(173.5);
        when(state.getWeightInKilograms())
                .thenReturn(70.0);
        when(state.getTargetWeightInKilograms())
                .thenReturn(65.0);
        when(state.getDurationDays())
                .thenReturn(60);

        Sex sex = Sex.values()[0];
        ActivityLevel activity = ActivityLevel.values()[0];
        HealthGoal goal = HealthGoal.values()[0];

        when(state.getSex()).thenReturn(sex);
        when(state.getActivityLevel()).thenReturn(activity);
        when(state.getHealthGoal()).thenReturn(goal);

        when(state.getStartDate())
                .thenReturn("2026-08-10");
        when(state.getEndDate())
                .thenReturn("2026-10-09");

        when(state.getDailyCalorieGoal())
                .thenReturn(2000.0);

        when(state.getDietaryRestrictions())
                .thenReturn(Arrays.asList(
                        "Vegetarian",
                        "Gluten Free",
                        "Halal"
                ));

        when(viewModel.getState()).thenReturn(state);

        view.propertyChange(
                new PropertyChangeEvent(
                        viewModel,
                        "state",
                        null,
                        state
                )
        );

        assertEquals(
                "21",
                getField(
                        view,
                        "ageField",
                        JTextField.class
                ).getText()
        );

        assertEquals(
                "173.5",
                getField(
                        view,
                        "heightField",
                        JTextField.class
                ).getText()
        );

        assertEquals(
                "70.0",
                getField(
                        view,
                        "weightField",
                        JTextField.class
                ).getText()
        );

        assertEquals(
                "65.0",
                getField(
                        view,
                        "targetWeightField",
                        JTextField.class
                ).getText()
        );

        assertEquals(
                "60",
                getField(
                        view,
                        "durationDaysField",
                        JTextField.class
                ).getText()
        );

        assertEquals(
                "2026-08-10",
                getField(
                        view,
                        "startDateLabel",
                        JLabel.class
                ).getText()
        );

        assertEquals(
                "2026-10-09",
                getField(
                        view,
                        "endDateLabel",
                        JLabel.class
                ).getText()
        );

        assertEquals(
                "2000 kcal/day (estimated)",
                getField(
                        view,
                        "dailyGoalLabel",
                        JLabel.class
                ).getText()
        );

        assertEquals(
                sex,
                getField(
                        view,
                        "sexBox",
                        JComboBox.class
                ).getSelectedItem()
        );

        assertTrue(
                getField(
                        view,
                        "vegetarianBox",
                        JCheckBox.class
                ).isSelected()
        );

        assertFalse(
                getField(
                        view,
                        "veganBox",
                        JCheckBox.class
                ).isSelected()
        );

        assertTrue(
                getField(
                        view,
                        "glutenFreeBox",
                        JCheckBox.class
                ).isSelected()
        );

        assertFalse(
                getField(
                        view,
                        "dairyFreeBox",
                        JCheckBox.class
                ).isSelected()
        );

        assertTrue(
                getField(
                        view,
                        "halalBox",
                        JCheckBox.class
                ).isSelected()
        );
    }

    @Test
    void missingPlanDatesTest() {
        ProfileState state = createEmptyState();

        when(state.getStartDate()).thenReturn(null);
        when(state.getEndDate()).thenReturn("");

        when(viewModel.getState()).thenReturn(state);

        view.propertyChange(
                new PropertyChangeEvent(
                        viewModel,
                        "state",
                        null,
                        state
                )
        );

        assertEquals(
                "Not created",
                getField(
                        view,
                        "startDateLabel",
                        JLabel.class
                ).getText()
        );

        assertEquals(
                "Not created",
                getField(
                        view,
                        "endDateLabel",
                        JLabel.class
                ).getText()
        );
    }

    @Test
    void nullDailyCalorieGoalTest() {
        ProfileState state = createEmptyState();

        when(state.getDailyCalorieGoal())
                .thenReturn(null);

        when(viewModel.getState()).thenReturn(state);

        view.propertyChange(
                new PropertyChangeEvent(
                        viewModel,
                        "state",
                        null,
                        state
                )
        );

        assertEquals(
                "Not calculated",
                getField(
                        view,
                        "dailyGoalLabel",
                        JLabel.class
                ).getText()
        );
    }

    @Test
    void emptyProfileDoesNotPopulateNumericFieldsTest() {
        ProfileState state = createEmptyState();

        when(viewModel.getState()).thenReturn(state);

        view.propertyChange(
                new PropertyChangeEvent(
                        viewModel,
                        "state",
                        null,
                        state
                )
        );

        assertEquals(
                "",
                getField(
                        view,
                        "ageField",
                        JTextField.class
                ).getText()
        );

        assertEquals(
                "",
                getField(
                        view,
                        "heightField",
                        JTextField.class
                ).getText()
        );
    }

    private ProfileState createEmptyState() {
        ProfileState state = mock(ProfileState.class);

        when(state.getProfileError()).thenReturn(null);

        when(state.getAge()).thenReturn(0);
        when(state.getHeightInCentimetres()).thenReturn(0.0);
        when(state.getWeightInKilograms()).thenReturn(0.0);
        when(state.getTargetWeightInKilograms()).thenReturn(0.0);
        when(state.getDurationDays()).thenReturn(0);

        when(state.getStartDate()).thenReturn(null);
        when(state.getEndDate()).thenReturn(null);

        when(state.getSex()).thenReturn(null);
        when(state.getActivityLevel()).thenReturn(null);
        when(state.getHealthGoal()).thenReturn(null);

        when(state.getDailyCalorieGoal()).thenReturn(null);

        when(state.getDietaryRestrictions())
                .thenReturn(Collections.emptyList());

        when(state.getSuccessMessage()).thenReturn(null);

        return state;
    }

    private JButton findButton(
            Container container,
            String text) {

        for (Component component : container.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;

                if (text.equals(button.getText())) {
                    return button;
                }
            }

            if (component instanceof Container) {
                JButton result =
                        findButton((Container) component, text);

                if (result != null) {
                    return result;
                }
            }
        }

        return null;
    }

    private <T> T getField(
            Object object,
            String name,
            Class<T> type) {

        try {
            Field field =
                    object.getClass().getDeclaredField(name);

            field.setAccessible(true);

            return type.cast(field.get(object));
        }
        catch (ReflectiveOperationException exception) {
            throw new AssertionError(exception);
        }
    }
}
