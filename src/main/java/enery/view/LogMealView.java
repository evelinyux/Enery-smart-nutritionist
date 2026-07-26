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
import enery.interface_adapter.change_password.LoggedInViewModel;
import enery.interface_adapter.log_meal.LogMealController;
import enery.interface_adapter.log_meal.LogMealState;
import enery.interface_adapter.log_meal.LogMealViewModel;
import enery.interface_adapter.ViewManagerModel;


/**
 * Swing view for logging a meal.
 */
public final class LogMealView extends JPanel
        implements PropertyChangeListener {

    public static final String VIEW_NAME = "log meal";

    private final LoggedInViewModel loggedInViewModel;
    private final LogMealViewModel viewModel;
    private final ViewManagerModel viewManagerModel;


    private LogMealController controller;

    private final JTextField foodNameField =
            new JTextField(15);

    private final JTextField caloriesField =
            new JTextField(15);

    private final JTextField proteinField =
            new JTextField(15);

    private final JTextField carbohydratesField =
            new JTextField(15);

    private final JTextField fatField =
            new JTextField(15);

    private final JTextField fiberField =
            new JTextField(15);

    private final JTextField sugarField =
            new JTextField(15);

    private final JTextField sodiumField =
            new JTextField(15);

    private final JTextField servingsField =
            new JTextField("1.0", 15);

    private final JComboBox<MealType> mealTypeBox =
            new JComboBox<>(MealType.values());

    private final JLabel statusLabel = new JLabel();

    public LogMealView(
            LogMealViewModel viewModel,
            ViewManagerModel viewManagerModel,
            LoggedInViewModel loggedInViewModel) {

        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;

        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(
                15,
                15,
                15,
                15
        ));

        final JLabel title = new JLabel("Log Meal");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel formPanel =
                new JPanel(new GridLayout(0, 2, 5, 5));

        formPanel.add(new JLabel("Food name"));
        formPanel.add(foodNameField);

        formPanel.add(new JLabel("Calories"));
        formPanel.add(caloriesField);

        formPanel.add(new JLabel("Protein (g)"));
        formPanel.add(proteinField);

        formPanel.add(new JLabel("Carbohydrates (g)"));
        formPanel.add(carbohydratesField);

        formPanel.add(new JLabel("Fat (g)"));
        formPanel.add(fatField);

        formPanel.add(new JLabel("Fiber (g)"));
        formPanel.add(fiberField);

        formPanel.add(new JLabel("Sugar (g)"));
        formPanel.add(sugarField);

        formPanel.add(new JLabel("Sodium (mg)"));
        formPanel.add(sodiumField);

        formPanel.add(new JLabel("Meal type"));
        formPanel.add(mealTypeBox);

        formPanel.add(new JLabel("Servings"));
        formPanel.add(servingsField);

        final JButton backButton = new JButton("Back to Home");

        backButton.addActionListener(event -> {
            viewManagerModel.setState(HomeView.VIEW_NAME);
            viewManagerModel.firePropertyChanged();
        });

        final JButton saveButton =
                new JButton("Save Meal");

        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        saveButton.addActionListener(event -> saveMeal());

        add(title);
        add(formPanel);
        add(saveButton);
        add(backButton);
        add(statusLabel);
    }

    private void saveMeal() {
        if (controller == null) {
            statusLabel.setText(
                    "Log-meal controller is not configured.");
            return;
        }

        try {
            final FoodItem foodItem = new FoodItem(
                    foodNameField.getText(),
                    Double.parseDouble(
                            caloriesField.getText()),
                    Double.parseDouble(
                            proteinField.getText()),
                    Double.parseDouble(
                            carbohydratesField.getText()),
                    Double.parseDouble(
                            fatField.getText()),
                    Double.parseDouble(
                            fiberField.getText()),
                    Double.parseDouble(
                            sugarField.getText()),
                    Double.parseDouble(
                            sodiumField.getText())
            );

            final MealType mealType =
                    (MealType) mealTypeBox.getSelectedItem();

            final double servings =
                    Double.parseDouble(
                            servingsField.getText());

            final String username =
                    loggedInViewModel.getState().getUsername();

            controller.execute(
                    username,
                    foodItem,
                    mealType,
                    servings,
                    LocalDateTime.now()
            );
        }
        catch (NumberFormatException exception) {
            statusLabel.setText(
                    "Nutrition values and servings must be numbers.");
        }
    }

    @Override
    public void propertyChange(
            PropertyChangeEvent event) {

        if ("state".equals(event.getPropertyName())) {
            final LogMealState state =
                    viewModel.getState();

            if (!state.getErrorMessage().isBlank()) {
                statusLabel.setText(
                        state.getErrorMessage());
            }
            else {
                statusLabel.setText(
                        state.getSuccessMessage());
            }
        }
    }

    public String getViewName() {
        return VIEW_NAME;
    }

    public void setController(
            LogMealController controller) {

        this.controller = controller;
    }
}
