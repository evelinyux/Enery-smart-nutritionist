package enery.view.meal;

import enery.entity.meal.FoodItem;
import enery.entity.meal.MealType;
import enery.interface_adapter.change_password.LoggedInViewModel;
import enery.interface_adapter.log_meal.LogMealController;
import enery.interface_adapter.log_meal.LogMealState;
import enery.interface_adapter.log_meal.LogMealViewModel;
import enery.interface_adapter.search_food.SearchFoodController;
import enery.interface_adapter.search_food.SearchFoodState;
import enery.interface_adapter.search_food.SearchFoodViewModel;
import enery.interface_adapter.shared.ViewManagerModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Component;
import java.awt.Container;
import java.beans.PropertyChangeEvent;
import java.lang.reflect.Field;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class LogMealViewTest {

    private LogMealViewModel logMealViewModel;
    private SearchFoodViewModel searchFoodViewModel;
    private ViewManagerModel viewManagerModel;
    private LoggedInViewModel loggedInViewModel;

    private LogMealView view;

    @BeforeEach
    void setUp() {
        logMealViewModel = mock(LogMealViewModel.class);
        searchFoodViewModel = mock(SearchFoodViewModel.class);
        viewManagerModel = mock(ViewManagerModel.class);
        loggedInViewModel = mock(LoggedInViewModel.class);

        view = new LogMealView(
                logMealViewModel,
                searchFoodViewModel,
                viewManagerModel,
                loggedInViewModel
        );
    }

    @Test
    void getViewNameTest() {
        assertEquals("log meal", view.getViewName());
        assertEquals(LogMealView.VIEW_NAME, view.getViewName());
    }

    @Test
    void constructorRegistersListenersTest() {
        verify(logMealViewModel).addPropertyChangeListener(view);
        verify(searchFoodViewModel).addPropertyChangeListener(view);
    }

    @Test
    void searchWithoutControllerTest() {
        JButton searchButton = findButton(view, "Search");

        assertNotNull(searchButton);

        searchButton.doClick();

        JLabel status = getField(view, "statusLabel", JLabel.class);

        assertEquals(
                "Food search is not configured.",
                status.getText()
        );
    }

    @Test
    void searchWithControllerTest() {
        SearchFoodController controller =
                mock(SearchFoodController.class);

        view.setSearchFoodController(controller);

        JTextField query =
                getField(view, "queryField", JTextField.class);

        query.setText("banana");

        JButton searchButton = findButton(view, "Search");

        assertNotNull(searchButton);

        searchButton.doClick();

        verify(controller).execute("banana");
    }

    @Test
    void saveWithoutControllerTest() {
        JButton saveButton = findButton(view, "Save Meal");

        assertNotNull(saveButton);

        saveButton.doClick();

        JLabel status =
                getField(view, "statusLabel", JLabel.class);

        assertEquals(
                "Meal logging is not configured.",
                status.getText()
        );
    }

    @Test
    void saveWithoutSelectedFoodTest() {
        LogMealController controller =
                mock(LogMealController.class);

        view.setLogMealController(controller);

        JComboBox<?> results =
                getField(view, "resultsBox", JComboBox.class);

        results.removeAllItems();

        JButton saveButton = findButton(view, "Save Meal");

        assertNotNull(saveButton);

        saveButton.doClick();

        JLabel status =
                getField(view, "statusLabel", JLabel.class);

        assertEquals(
                "Select a food result first.",
                status.getText()
        );

        verifyNoInteractions(controller);
    }

    @Test
    void invalidBlankQuantityTest() {
        LogMealController controller =
                mock(LogMealController.class);

        view.setLogMealController(controller);

        FoodItem food = createFoodItem();

        JComboBox<FoodItem> results =
                getField(view, "resultsBox", JComboBox.class);

        results.addItem(food);
        results.setSelectedItem(food);

        JTextField quantity =
                getField(view, "quantityField", JTextField.class);

        quantity.setText("");

        JButton saveButton = findButton(view, "Save Meal");

        assertNotNull(saveButton);

        saveButton.doClick();

        JLabel status =
                getField(view, "statusLabel", JLabel.class);

        assertEquals("Quantity is required.", status.getText());

        verifyNoInteractions(controller);
    }

    @Test
    void invalidZeroQuantityTest() {
        LogMealController controller =
                mock(LogMealController.class);

        view.setLogMealController(controller);

        FoodItem food = createFoodItem();

        JComboBox<FoodItem> results =
                getField(view, "resultsBox", JComboBox.class);

        results.addItem(food);
        results.setSelectedItem(food);

        JTextField quantity =
                getField(view, "quantityField", JTextField.class);

        quantity.setText("0");

        JButton saveButton = findButton(view, "Save Meal");

        assertNotNull(saveButton);

        saveButton.doClick();

        JLabel status =
                getField(view, "statusLabel", JLabel.class);

        assertEquals(
                "Quantity must be greater than zero.",
                status.getText()
        );

        verifyNoInteractions(controller);
    }

    @Test
    void invalidNegativeQuantityTest() {
        LogMealController controller =
                mock(LogMealController.class);

        view.setLogMealController(controller);

        FoodItem food = createFoodItem();

        JComboBox<FoodItem> results =
                getField(view, "resultsBox", JComboBox.class);

        results.addItem(food);
        results.setSelectedItem(food);

        JTextField quantity =
                getField(view, "quantityField", JTextField.class);

        quantity.setText("-5");

        JButton saveButton = findButton(view, "Save Meal");

        assertNotNull(saveButton);

        saveButton.doClick();

        JLabel status =
                getField(view, "statusLabel", JLabel.class);

        assertEquals(
                "Quantity must be greater than zero.",
                status.getText()
        );
    }

    @Test
    void invalidTextQuantityTest() {
        LogMealController controller =
                mock(LogMealController.class);

        view.setLogMealController(controller);

        FoodItem food = createFoodItem();

        JComboBox<FoodItem> results =
                getField(view, "resultsBox", JComboBox.class);

        results.addItem(food);
        results.setSelectedItem(food);

        JTextField quantity =
                getField(view, "quantityField", JTextField.class);

        quantity.setText("abc");

        JButton saveButton = findButton(view, "Save Meal");

        assertNotNull(saveButton);

        saveButton.doClick();

        JLabel status =
                getField(view, "statusLabel", JLabel.class);

        assertEquals(
                "Quantity must be a number.",
                status.getText()
        );
    }

    @Test
    void invalidServingSizeTest() {
        LogMealController controller =
                mock(LogMealController.class);

        view.setLogMealController(controller);

        FoodItem food = mock(FoodItem.class);

        when(food.getServingSizeInGrams()).thenReturn(0.0);

        JComboBox<FoodItem> results =
                getField(view, "resultsBox", JComboBox.class);

        results.addItem(food);
        results.setSelectedItem(food);

        JTextField quantity =
                getField(view, "quantityField", JTextField.class);

        quantity.setText("100");

        JButton saveButton = findButton(view, "Save Meal");

        assertNotNull(saveButton);

        saveButton.doClick();

        JLabel status =
                getField(view, "statusLabel", JLabel.class);

        assertEquals(
                "The selected food has an invalid serving size.",
                status.getText()
        );

        verifyNoInteractions(controller);
    }

    @Test
    void invalidInfiniteServingSizeTest() {
        LogMealController controller =
                mock(LogMealController.class);

        view.setLogMealController(controller);

        FoodItem food = mock(FoodItem.class);

        when(food.getServingSizeInGrams())
                .thenReturn(Double.POSITIVE_INFINITY);

        JComboBox<FoodItem> results =
                getField(view, "resultsBox", JComboBox.class);

        results.addItem(food);
        results.setSelectedItem(food);

        JTextField quantity =
                getField(view, "quantityField", JTextField.class);

        quantity.setText("100");

        JButton saveButton = findButton(view, "Save Meal");

        assertNotNull(saveButton);

        saveButton.doClick();

        JLabel status =
                getField(view, "statusLabel", JLabel.class);

        assertEquals(
                "The selected food has an invalid serving size.",
                status.getText()
        );
    }

    @Test
    void selectedFoodUpdatesReferenceLabelsTest() {
        FoodItem food = createFoodItem();

        JComboBox<FoodItem> results =
                getField(view, "resultsBox", JComboBox.class);

        results.addItem(food);
        results.setSelectedItem(food);

        assertEquals(
                "100.0 g",
                getField(
                        view,
                        "referenceServingLabel",
                        JLabel.class
                ).getText()
        );

        assertEquals(
                "200.0 kcal",
                getField(
                        view,
                        "referenceCaloriesLabel",
                        JLabel.class
                ).getText()
        );

        assertEquals(
                "10.0 g",
                getField(
                        view,
                        "referenceProteinLabel",
                        JLabel.class
                ).getText()
        );

        assertEquals(
                "30.0 g",
                getField(
                        view,
                        "referenceCarbohydratesLabel",
                        JLabel.class
                ).getText()
        );

        assertEquals(
                "5.0 g",
                getField(
                        view,
                        "referenceFatLabel",
                        JLabel.class
                ).getText()
        );

        assertEquals(
                "4.0 g",
                getField(
                        view,
                        "referenceFiberLabel",
                        JLabel.class
                ).getText()
        );

        assertEquals(
                "8.0 g",
                getField(
                        view,
                        "referenceSugarLabel",
                        JLabel.class
                ).getText()
        );

        assertEquals(
                "150.0 mg",
                getField(
                        view,
                        "referenceSodiumLabel",
                        JLabel.class
                ).getText()
        );
    }

    @Test
    void quantityScalingTest() {
        FoodItem food = createFoodItem();

        JComboBox<FoodItem> results =
                getField(view, "resultsBox", JComboBox.class);

        results.addItem(food);
        results.setSelectedItem(food);

        JTextField quantity =
                getField(view, "quantityField", JTextField.class);

        quantity.setText("50");

        assertEquals(
                "100.0 kcal",
                getField(
                        view,
                        "quantityCaloriesLabel",
                        JLabel.class
                ).getText()
        );

        assertEquals(
                "5.0 g",
                getField(
                        view,
                        "quantityProteinLabel",
                        JLabel.class
                ).getText()
        );

        assertEquals(
                "15.0 g",
                getField(
                        view,
                        "quantityCarbohydratesLabel",
                        JLabel.class
                ).getText()
        );

        assertEquals(
                "2.5 g",
                getField(
                        view,
                        "quantityFatLabel",
                        JLabel.class
                ).getText()
        );

        assertEquals(
                "2.0 g",
                getField(
                        view,
                        "quantityFiberLabel",
                        JLabel.class
                ).getText()
        );

        assertEquals(
                "4.0 g",
                getField(
                        view,
                        "quantitySugarLabel",
                        JLabel.class
                ).getText()
        );

        assertEquals(
                "75.0 mg",
                getField(
                        view,
                        "quantitySodiumLabel",
                        JLabel.class
                ).getText()
        );
    }

    @Test
    void invalidQuantityClearsTotalsTest() {
        FoodItem food = createFoodItem();

        JComboBox<FoodItem> results =
                getField(view, "resultsBox", JComboBox.class);

        results.addItem(food);
        results.setSelectedItem(food);

        JTextField quantity =
                getField(view, "quantityField", JTextField.class);

        quantity.setText("abc");

        assertEquals(
                "-",
                getField(
                        view,
                        "quantityCaloriesLabel",
                        JLabel.class
                ).getText()
        );
    }

    @Test
    void zeroQuantityClearsTotalsTest() {
        FoodItem food = createFoodItem();

        JComboBox<FoodItem> results =
                getField(view, "resultsBox", JComboBox.class);

        results.addItem(food);
        results.setSelectedItem(food);

        JTextField quantity =
                getField(view, "quantityField", JTextField.class);

        quantity.setText("0");

        assertEquals(
                "-",
                getField(
                        view,
                        "quantityCaloriesLabel",
                        JLabel.class
                ).getText()
        );
    }

    @Test
    void removingSelectedFoodClearsDisplayTest() {
        FoodItem food = createFoodItem();

        JComboBox<FoodItem> results =
                getField(view, "resultsBox", JComboBox.class);

        results.addItem(food);
        results.setSelectedItem(food);

        results.removeAllItems();

        assertEquals(
                "-",
                getField(
                        view,
                        "referenceServingLabel",
                        JLabel.class
                ).getText()
        );

        assertEquals(
                "-",
                getField(
                        view,
                        "quantityCaloriesLabel",
                        JLabel.class
                ).getText()
        );
    }

    @Test
    void backButtonTest() {
        JButton backButton =
                findButton(view, "Back to Home");

        assertNotNull(backButton);

        backButton.doClick();

        verify(viewManagerModel).setState(anyString());
        verify(viewManagerModel).firePropertyChanged();
    }

    @Test
    void ignoresUnrelatedPropertyChangeTest() {
        view.propertyChange(
                new PropertyChangeEvent(
                        new Object(),
                        "somethingElse",
                        null,
                        null
                )
        );

        verify(searchFoodViewModel, never()).getState();
        verify(logMealViewModel, never()).getState();
    }

    @Test
    void searchErrorStateTest() {
        SearchFoodState state = mock(SearchFoodState.class);

        when(searchFoodViewModel.getState())
                .thenReturn(state);

        when(state.getResults())
                .thenReturn(Collections.emptyList());

        when(state.getErrorMessage())
                .thenReturn("Search failed");

        view.propertyChange(
                new PropertyChangeEvent(
                        searchFoodViewModel,
                        "state",
                        null,
                        state
                )
        );

        JLabel status =
                getField(view, "statusLabel", JLabel.class);

        assertEquals("Search failed", status.getText());
    }

    @Test
    void emptySearchSuccessStateTest() {
        SearchFoodState state = mock(SearchFoodState.class);

        when(searchFoodViewModel.getState())
                .thenReturn(state);

        when(state.getResults())
                .thenReturn(Collections.emptyList());

        when(state.getErrorMessage())
                .thenReturn("");

        view.propertyChange(
                new PropertyChangeEvent(
                        searchFoodViewModel,
                        "state",
                        null,
                        state
                )
        );

        JLabel status =
                getField(view, "statusLabel", JLabel.class);

        assertEquals(
                "0 food result(s) found.",
                status.getText()
        );
    }

    @Test
    void searchSuccessWithFoodTest() {
        SearchFoodState state = mock(SearchFoodState.class);
        FoodItem food = createFoodItem();

        when(searchFoodViewModel.getState())
                .thenReturn(state);

        when(state.getResults())
                .thenReturn(Collections.singletonList(food));

        when(state.getErrorMessage())
                .thenReturn("");

        view.propertyChange(
                new PropertyChangeEvent(
                        searchFoodViewModel,
                        "state",
                        null,
                        state
                )
        );

        JComboBox<?> results =
                getField(view, "resultsBox", JComboBox.class);

        assertEquals(1, results.getItemCount());

        JLabel status =
                getField(view, "statusLabel", JLabel.class);

        assertEquals(
                "1 food result(s) found.",
                status.getText()
        );
    }

    @Test
    void logMealErrorStateTest() {
        LogMealState state = mock(LogMealState.class);

        when(logMealViewModel.getState())
                .thenReturn(state);

        when(state.getErrorMessage())
                .thenReturn("Could not save meal");

        view.propertyChange(
                new PropertyChangeEvent(
                        logMealViewModel,
                        "state",
                        null,
                        state
                )
        );

        JLabel status =
                getField(view, "statusLabel", JLabel.class);

        assertEquals(
                "Could not save meal",
                status.getText()
        );
    }

    @Test
    void logMealSuccessStateTest() {
        LogMealState state = mock(LogMealState.class);

        when(logMealViewModel.getState())
                .thenReturn(state);

        when(state.getErrorMessage())
                .thenReturn("");

        when(state.getSuccessMessage())
                .thenReturn("Meal saved");

        view.propertyChange(
                new PropertyChangeEvent(
                        logMealViewModel,
                        "state",
                        null,
                        state
                )
        );

        JLabel status =
                getField(view, "statusLabel", JLabel.class);

        assertEquals("Meal saved", status.getText());
    }

    @Test
    void unavailableNutritionValueTest() {
        FoodItem food = createFoodItem();

        when(food.getCalories()).thenReturn(Double.NaN);

        JComboBox<FoodItem> results =
                getField(view, "resultsBox", JComboBox.class);

        results.addItem(food);
        results.setSelectedItem(food);

        JLabel calories =
                getField(
                        view,
                        "referenceCaloriesLabel",
                        JLabel.class
                );

        assertEquals("Unavailable", calories.getText());
    }

    private FoodItem createFoodItem() {
        FoodItem food = mock(FoodItem.class);

        when(food.getServingSizeInGrams()).thenReturn(100.0);
        when(food.getCalories()).thenReturn(200.0);
        when(food.getProteinInGrams()).thenReturn(10.0);
        when(food.getCarbohydratesInGrams()).thenReturn(30.0);
        when(food.getFatInGrams()).thenReturn(5.0);
        when(food.getFiberInGrams()).thenReturn(4.0);
        when(food.getSugarInGrams()).thenReturn(8.0);
        when(food.getSodiumInMilligrams()).thenReturn(150.0);

        return food;
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
