package enery.view;

import java.awt.Component;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import enery.entity.FoodItem;
import enery.entity.MealType;
import enery.interface_adapter.ViewManagerModel;
import enery.interface_adapter.change_password.LoggedInViewModel;
import enery.interface_adapter.log_meal.LogMealController;
import enery.interface_adapter.log_meal.LogMealState;
import enery.interface_adapter.log_meal.LogMealViewModel;
import enery.interface_adapter.search_food.SearchFoodController;
import enery.interface_adapter.search_food.SearchFoodState;
import enery.interface_adapter.search_food.SearchFoodViewModel;

/**
 * Swing view for searching for food and logging a selected meal.
 */
public final class LogMealView extends JPanel
        implements PropertyChangeListener {

    public static final String VIEW_NAME = "log meal";

    private final LogMealViewModel logMealViewModel;
    private final SearchFoodViewModel searchFoodViewModel;
    private final ViewManagerModel viewManagerModel;
    private final LoggedInViewModel loggedInViewModel;

    private LogMealController logMealController;
    private SearchFoodController searchFoodController;

    private final JTextField queryField =
            new JTextField(20);

    private final JComboBox<FoodItem> resultsBox =
            new JComboBox<>();

    private final JComboBox<MealType> mealTypeBox =
            new JComboBox<>(MealType.values());

    private final JTextField servingsField =
            new JTextField("1.0", 10);

    private final JLabel nutritionLabel =
            new JLabel("Select a food result.");

    private final JLabel statusLabel =
            new JLabel();

    public LogMealView(
            LogMealViewModel logMealViewModel,
            SearchFoodViewModel searchFoodViewModel,
            ViewManagerModel viewManagerModel,
            LoggedInViewModel loggedInViewModel) {

        this.logMealViewModel = logMealViewModel;
        this.searchFoodViewModel = searchFoodViewModel;
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;

        logMealViewModel.addPropertyChangeListener(this);
        searchFoodViewModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(
                15,
                15,
                15,
                15
        ));

        final JLabel title =
                new JLabel("Search and Log Meal");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel formPanel =
                new JPanel(new GridLayout(0, 2, 5, 5));

        formPanel.add(new JLabel("Food query"));
        formPanel.add(queryField);

        formPanel.add(new JLabel("Search results"));
        formPanel.add(resultsBox);

        formPanel.add(new JLabel("Meal type"));
        formPanel.add(mealTypeBox);

        formPanel.add(new JLabel("Servings"));
        formPanel.add(servingsField);

        final JButton searchButton =
                new JButton("Search");

        final JButton saveButton =
                new JButton("Save Meal");

        final JButton backButton =
                new JButton("Back to Home");

        searchButton.addActionListener(event ->
                searchForFood());

        saveButton.addActionListener(event ->
                saveSelectedMeal());

        backButton.addActionListener(event -> {
            viewManagerModel.setState(HomeView.VIEW_NAME);
            viewManagerModel.firePropertyChanged();
        });

        resultsBox.addActionListener(event ->
                updateNutritionDisplay());

        add(title);
        add(formPanel);
        add(nutritionLabel);
        add(searchButton);
        add(saveButton);
        add(backButton);
        add(statusLabel);
    }

    private void searchForFood() {
        if (searchFoodController == null) {
            statusLabel.setText(
                    "Food search is not configured.");
            return;
        }

        searchFoodController.execute(
                queryField.getText());
    }

    private void saveSelectedMeal() {
        if (logMealController == null) {
            statusLabel.setText(
                    "Meal logging is not configured.");
            return;
        }

        final FoodItem selected =
                (FoodItem) resultsBox.getSelectedItem();

        if (selected == null) {
            statusLabel.setText(
                    "Select a food result first.");
            return;
        }

        try {
            final double servings =
                    Double.parseDouble(
                            servingsField.getText());

            final MealType mealType =
                    (MealType) mealTypeBox.getSelectedItem();

            final String username =
                    loggedInViewModel
                            .getState()
                            .getUsername();

            logMealController.execute(
                    username,
                    selected,
                    mealType,
                    servings,
                    LocalDateTime.now()
            );
        }
        catch (NumberFormatException exception) {
            statusLabel.setText(
                    "Servings must be a number.");
        }
    }

    private void updateNutritionDisplay() {
        final FoodItem selected =
                (FoodItem) resultsBox.getSelectedItem();

        if (selected == null) {
            nutritionLabel.setText(
                    "Select a food result.");
            return;
        }

        nutritionLabel.setText(
                selected.getName()
                        + " | "
                        + selected.getServingSizeInGrams()
                        + " g | "
                        + selected.getCalories()
                        + " kcal | Protein "
                        + selected.getProteinInGrams()
                        + " g | Carbs "
                        + selected.getCarbohydratesInGrams()
                        + " g | Fat "
                        + selected.getFatInGrams()
                        + " g"
        );
    }

    @Override
    public void propertyChange(
            PropertyChangeEvent event) {

        if (!"state".equals(event.getPropertyName())) {
            return;
        }

        if (event.getSource() == searchFoodViewModel) {
            updateSearchState();
        }
        else if (event.getSource() == logMealViewModel) {
            updateLogMealState();
        }
    }

    private void updateSearchState() {
        final SearchFoodState state =
                searchFoodViewModel.getState();

        resultsBox.removeAllItems();

        for (FoodItem foodItem : state.getResults()) {
            resultsBox.addItem(foodItem);
        }

        if (!state.getErrorMessage().isBlank()) {
            statusLabel.setText(
                    state.getErrorMessage());
        }
        else {
            statusLabel.setText(
                    state.getResults().size()
                            + " food result(s) found.");
        }

        updateNutritionDisplay();
    }

    private void updateLogMealState() {
        final LogMealState state =
                logMealViewModel.getState();

        if (!state.getErrorMessage().isBlank()) {
            statusLabel.setText(
                    state.getErrorMessage());
        }
        else {
            statusLabel.setText(
                    state.getSuccessMessage());
        }
    }

    public String getViewName() {
        return VIEW_NAME;
    }

    public void setLogMealController(
            LogMealController controller) {

        this.logMealController = controller;
    }

    public void setSearchFoodController(
            SearchFoodController controller) {

        this.searchFoodController = controller;
    }
}
