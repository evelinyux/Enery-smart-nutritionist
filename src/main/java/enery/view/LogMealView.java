package enery.view;

import java.awt.Component;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

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
 *
 * <p>The selected USDA result provides nutrition values per reference serving,
 * usually 100 grams. The user enters the amount they actually consumed in
 * grams, and this view scales the nutrient totals before logging the meal.</p>
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

    private final JLabel referenceServingLabel =
            new JLabel("-");

    private final JLabel referenceCaloriesLabel =
            new JLabel("-");

    private final JLabel referenceProteinLabel =
            new JLabel("-");

    private final JLabel referenceCarbohydratesLabel =
            new JLabel("-");

    private final JLabel referenceFatLabel =
            new JLabel("-");

    private final JLabel referenceFiberLabel =
            new JLabel("-");

    private final JLabel referenceSugarLabel =
            new JLabel("-");

    private final JLabel referenceSodiumLabel =
            new JLabel("-");

    private final JTextField quantityField =
            new JTextField("100.0", 10);

    private final JLabel quantityCaloriesLabel =
            new JLabel("-");

    private final JLabel quantityProteinLabel =
            new JLabel("-");

    private final JLabel quantityCarbohydratesLabel =
            new JLabel("-");

    private final JLabel quantityFatLabel =
            new JLabel("-");

    private final JLabel quantityFiberLabel =
            new JLabel("-");

    private final JLabel quantitySugarLabel =
            new JLabel("-");

    private final JLabel quantitySodiumLabel =
            new JLabel("-");

    private final JComboBox<MealType> mealTypeBox =
            new JComboBox<>(MealType.values());

    private final JLabel statusLabel =
            new JLabel();

    /**
     * Creates the combined food-search and meal-logging screen.
     *
     * @param logMealViewModel meal-logging view model
     * @param searchFoodViewModel food-search view model
     * @param viewManagerModel application navigation model
     * @param loggedInViewModel active-user view model
     */
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

        configureView();
        configureQuantityListener();
    }

    /**
     * Creates and arranges all Swing components.
     */
    private void configureView() {
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

        formPanel.add(new JLabel("Reference serving"));
        formPanel.add(referenceServingLabel);

        formPanel.add(new JLabel("Calories per reference serving"));
        formPanel.add(referenceCaloriesLabel);

        formPanel.add(new JLabel("Protein per reference serving"));
        formPanel.add(referenceProteinLabel);

        formPanel.add(new JLabel("Carbohydrates per reference serving"));
        formPanel.add(referenceCarbohydratesLabel);

        formPanel.add(new JLabel("Fat per reference serving"));
        formPanel.add(referenceFatLabel);

        formPanel.add(new JLabel("Fiber per reference serving"));
        formPanel.add(referenceFiberLabel);

        formPanel.add(new JLabel("Sugar per reference serving"));
        formPanel.add(referenceSugarLabel);

        formPanel.add(new JLabel("Sodium per reference serving"));
        formPanel.add(referenceSodiumLabel);

        formPanel.add(new JLabel("Quantity to log (g)"));
        formPanel.add(quantityField);

        formPanel.add(new JLabel("Calories for entered quantity"));
        formPanel.add(quantityCaloriesLabel);

        formPanel.add(new JLabel("Protein for entered quantity"));
        formPanel.add(quantityProteinLabel);

        formPanel.add(new JLabel("Carbohydrates for entered quantity"));
        formPanel.add(quantityCarbohydratesLabel);

        formPanel.add(new JLabel("Fat for entered quantity"));
        formPanel.add(quantityFatLabel);

        formPanel.add(new JLabel("Fiber for entered quantity"));
        formPanel.add(quantityFiberLabel);

        formPanel.add(new JLabel("Sugar for entered quantity"));
        formPanel.add(quantitySugarLabel);

        formPanel.add(new JLabel("Sodium for entered quantity"));
        formPanel.add(quantitySodiumLabel);

        formPanel.add(new JLabel("Meal type"));
        formPanel.add(mealTypeBox);

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

        backButton.addActionListener(event ->
                returnHome());

        resultsBox.addActionListener(event ->
                updateSelectedFood());

        add(title);
        add(formPanel);
        add(searchButton);
        add(saveButton);
        add(backButton);
        add(statusLabel);
    }

    /**
     * Updates scaled nutrient totals whenever the quantity field changes.
     */
    private void configureQuantityListener() {
        quantityField.getDocument().addDocumentListener(
                new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent event) {
                        updateQuantityTotals();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent event) {
                        updateQuantityTotals();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent event) {
                        updateQuantityTotals();
                    }
                }
        );
    }

    /**
     * Executes a food search using the entered text.
     */
    private void searchForFood() {
        if (searchFoodController == null) {
            statusLabel.setText(
                    "Food search is not configured.");
            return;
        }

        searchFoodController.execute(
                queryField.getText());
    }

    /**
     * Logs the currently selected food using the entered quantity.
     */
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
            final double quantityInGrams =
                    parsePositiveNumber(
                            quantityField.getText(),
                            "Quantity");

            final double referenceServing =
                    selected.getServingSizeInGrams();

            if (!Double.isFinite(referenceServing)
                    || referenceServing <= 0.0) {

                throw new IllegalArgumentException(
                        "The selected food has an invalid serving size.");
            }

            final double servings =
                    quantityInGrams / referenceServing;

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
        catch (IllegalArgumentException exception) {
            statusLabel.setText(
                    exception.getMessage());
        }
    }

    /**
     * Updates reference nutrition values when a search result is selected.
     */
    private void updateSelectedFood() {
        final FoodItem selected =
                (FoodItem) resultsBox.getSelectedItem();

        if (selected == null) {
            clearNutritionDisplay();
            return;
        }

        referenceServingLabel.setText(
                formatNumber(
                        selected.getServingSizeInGrams(),
                        "g"));

        referenceCaloriesLabel.setText(
                formatNumber(
                        selected.getCalories(),
                        "kcal"));

        referenceProteinLabel.setText(
                formatNumber(
                        selected.getProteinInGrams(),
                        "g"));

        referenceCarbohydratesLabel.setText(
                formatNumber(
                        selected.getCarbohydratesInGrams(),
                        "g"));

        referenceFatLabel.setText(
                formatNumber(
                        selected.getFatInGrams(),
                        "g"));

        referenceFiberLabel.setText(
                formatNumber(
                        selected.getFiberInGrams(),
                        "g"));

        referenceSugarLabel.setText(
                formatNumber(
                        selected.getSugarInGrams(),
                        "g"));

        referenceSodiumLabel.setText(
                formatNumber(
                        selected.getSodiumInMilligrams(),
                        "mg"));

        quantityField.setText(
                Double.toString(
                        selected.getServingSizeInGrams()));

        updateQuantityTotals();
    }

    /**
     * Scales reference nutrient values to the entered quantity.
     */
    private void updateQuantityTotals() {
        final FoodItem selected =
                (FoodItem) resultsBox.getSelectedItem();

        if (selected == null) {
            clearQuantityTotals();
            return;
        }

        try {
            final double quantityInGrams =
                    Double.parseDouble(
                            quantityField.getText().trim());

            final double referenceServing =
                    selected.getServingSizeInGrams();

            if (!Double.isFinite(quantityInGrams)
                    || quantityInGrams <= 0.0
                    || !Double.isFinite(referenceServing)
                    || referenceServing <= 0.0) {

                clearQuantityTotals();
                return;
            }

            final double multiplier =
                    quantityInGrams / referenceServing;

            quantityCaloriesLabel.setText(
                    formatNumber(
                            selected.getCalories()
                                    * multiplier,
                            "kcal"));

            quantityProteinLabel.setText(
                    formatNumber(
                            selected.getProteinInGrams()
                                    * multiplier,
                            "g"));

            quantityCarbohydratesLabel.setText(
                    formatNumber(
                            selected.getCarbohydratesInGrams()
                                    * multiplier,
                            "g"));

            quantityFatLabel.setText(
                    formatNumber(
                            selected.getFatInGrams()
                                    * multiplier,
                            "g"));

            quantityFiberLabel.setText(
                    formatNumber(
                            selected.getFiberInGrams()
                                    * multiplier,
                            "g"));

            quantitySugarLabel.setText(
                    formatNumber(
                            selected.getSugarInGrams()
                                    * multiplier,
                            "g"));

            quantitySodiumLabel.setText(
                    formatNumber(
                            selected.getSodiumInMilligrams()
                                    * multiplier,
                            "mg"));
        }
        catch (NumberFormatException exception) {
            clearQuantityTotals();
        }
    }

    /**
     * Clears all nutrition information.
     */
    private void clearNutritionDisplay() {
        referenceServingLabel.setText("-");
        referenceCaloriesLabel.setText("-");
        referenceProteinLabel.setText("-");
        referenceCarbohydratesLabel.setText("-");
        referenceFatLabel.setText("-");
        referenceFiberLabel.setText("-");
        referenceSugarLabel.setText("-");
        referenceSodiumLabel.setText("-");

        quantityField.setText("");
        clearQuantityTotals();
    }

    /**
     * Clears only the scaled quantity totals.
     */
    private void clearQuantityTotals() {
        quantityCaloriesLabel.setText("-");
        quantityProteinLabel.setText("-");
        quantityCarbohydratesLabel.setText("-");
        quantityFatLabel.setText("-");
        quantityFiberLabel.setText("-");
        quantitySugarLabel.setText("-");
        quantitySodiumLabel.setText("-");
    }

    /**
     * Returns to the home screen.
     */
    private void returnHome() {
        viewManagerModel.setState(HomeView.VIEW_NAME);
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Handles changes from the food-search and meal-logging view models.
     *
     * @param event property-change event
     */
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

    /**
     * Displays food-search results.
     */
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

        updateSelectedFood();
    }

    /**
     * Displays the result of saving a meal.
     */
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

    /**
     * Parses a required positive number.
     *
     * @param text entered text
     * @param fieldName name shown in validation messages
     * @return parsed positive value
     */
    private static double parsePositiveNumber(
            String text,
            String fieldName) {

        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException(
                    fieldName + " is required.");
        }

        try {
            final double value =
                    Double.parseDouble(text.trim());

            if (!Double.isFinite(value) || value <= 0.0) {
                throw new IllegalArgumentException(
                        fieldName
                                + " must be greater than zero.");
            }

            return value;
        }
        catch (NumberFormatException exception) {
            throw new IllegalArgumentException(
                    fieldName + " must be a number.",
                    exception);
        }
    }

    /**
     * Formats a nutrient value using one decimal place.
     *
     * @param value numeric value
     * @param unit measurement unit
     * @return formatted text
     */
    private static String formatNumber(
            double value,
            String unit) {

        if (!Double.isFinite(value)) {
            return "Unavailable";
        }

        return String.format(
                Locale.US,
                "%.1f %s",
                value,
                unit
        );
    }

    /**
     * Returns this view's CardLayout name.
     *
     * @return meal view name
     */
    public String getViewName() {
        return VIEW_NAME;
    }

    /**
     * Sets the meal-logging controller.
     *
     * @param controller meal-logging controller
     */
    public void setLogMealController(
            LogMealController controller) {

        this.logMealController = controller;
    }

    /**
     * Sets the reusable food-search controller.
     *
     * @param controller food-search controller
     */
    public void setSearchFoodController(
            SearchFoodController controller) {

        this.searchFoodController = controller;
    }
}
