package enery.view.recommendation;
////
////import enery.interface_adapter.recipe.RecipeState;
////import enery.interface_adapter.recipe.RecipeViewModel;
////
////import javax.swing.*;
////import java.awt.*;
////import java.beans.PropertyChangeEvent;
////import java.beans.PropertyChangeListener;
////
////public class RecipeView extends JPanel implements PropertyChangeListener {
////    public final String viewName = "recipe view";
////
////    private final RecipeViewModel recipeViewModel;
////
////    private final JLabel titleLabel = new JLabel("", SwingConstants.CENTER);
////    private final JLabel nutritionSummaryLabel = new JLabel("", SwingConstants.CENTER);
////    private final JTextArea descriptionArea = new JTextArea(3, 30);
////    private final JTextArea ingredientsArea = new JTextArea(8, 30);
////    private final JTextArea instructionsArea = new JTextArea(8, 30);
////    private final JButton backButton = new JButton("Back");
////
////    public RecipeView(RecipeViewModel recipeViewModel, CardLayout cardLayout, JPanel viewsPanel) {
////        this.recipeViewModel = recipeViewModel;
////        this.recipeViewModel.addPropertyChangeListener(this);
////
////        setLayout(new BorderLayout(10, 10));
////        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
////
////        // --- Header Section ---
////        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
////        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
////        nutritionSummaryLabel.setFont(new Font("Arial", Font.ITALIC, 13));
////        headerPanel.add(titleLabel);
////        headerPanel.add(nutritionSummaryLabel);
////        add(headerPanel, BorderLayout.NORTH);
////
////        // --- Center Details Section ---
////        JPanel contentPanel = new JPanel();
////        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
////
////        // Description
////        descriptionArea.setEditable(false);
////        descriptionArea.setLineWrap(true);
////        descriptionArea.setWrapStyleWord(true);
////        descriptionArea.setOpaque(false);
////
////        // Ingredients Area
////        ingredientsArea.setEditable(false);
////        ingredientsArea.setLineWrap(true);
////        ingredientsArea.setWrapStyleWord(true);
////
////        // Instructions Area
////        instructionsArea.setEditable(false);
////        instructionsArea.setLineWrap(true);
////        instructionsArea.setWrapStyleWord(true);
////
////        contentPanel.add(descriptionArea);
////        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
////        contentPanel.add(new JLabel("Ingredients:"));
////        contentPanel.add(new JScrollPane(ingredientsArea));
////        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
////        contentPanel.add(new JLabel("Instructions:"));
////        contentPanel.add(new JScrollPane(instructionsArea));
////
////        add(contentPanel, BorderLayout.CENTER);
////
////        // --- Footer Navigation Section ---
////        backButton.addActionListener(e -> cardLayout.show(viewsPanel, "recommendation view"));
////        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
////        buttonPanel.add(backButton);
////        add(buttonPanel, BorderLayout.SOUTH);
////    }
////
////    @Override
////    public void propertyChange(PropertyChangeEvent evt) {
////        if ("state".equals(evt.getPropertyName())) {
////            RecipeState state = (RecipeState) evt.getNewValue();
////
////            if (state.getRecipeError() != null) {
////                JOptionPane.showMessageDialog(this, state.getRecipeError(), "Error", JOptionPane.ERROR_MESSAGE);
////                return;
////            }
////
////            titleLabel.setText(state.getRecipeName());
////            descriptionArea.setText(state.getDescription());
////
////            // Build single aggregated string for nutrition header
////            String nutritionText = String.format("Calories: %s | Protein: %s | Carbs: %s | Fat: %s | Sugar: %s",
////                    state.getCalories(), state.getProtein(), state.getCarbs(), state.getFat(), state.getSugar());
////            nutritionSummaryLabel.setText(nutritionText);
////
////            // Directly passes multi-line String to UI
////            ingredientsArea.setText(state.getIngredients());
////            instructionsArea.setText(state.getInstructions());
////        }
////    }
////}
//package enery.view;
//
//import enery.interface_adapter.ViewManagerModel;
//import enery.interface_adapter.recipe.RecipeState;
//import enery.interface_adapter.recipe.RecipeViewModel;
//import enery.interface_adapter.recommendation.RecommendationViewModel;
//
//import javax.swing.*;
//import java.awt.*;
//import java.beans.PropertyChangeEvent;
//import java.beans.PropertyChangeListener;
//
//public class RecipeView extends JPanel implements PropertyChangeListener {
//    public static final String VIEW_NAME = "recipe view"; // Updated to static final constant
//
//    private final RecipeViewModel recipeViewModel;
//    private final ViewManagerModel viewManagerModel; // Replaced CardLayout/JPanel with ViewManagerModel
//
//    private final JLabel titleLabel = new JLabel("", SwingConstants.CENTER);
//    private final JLabel nutritionSummaryLabel = new JLabel("", SwingConstants.CENTER);
//    private final JTextArea descriptionArea = new JTextArea(3, 30);
//    private final JTextArea ingredientsArea = new JTextArea(8, 30);
//    private final JTextArea instructionsArea = new JTextArea(8, 30);
//    private final JButton backButton = new JButton("Back");
//
//    public RecipeView(RecipeViewModel recipeViewModel, ViewManagerModel viewManagerModel) {
//        this.recipeViewModel = recipeViewModel;
//        this.viewManagerModel = viewManagerModel;
//        this.recipeViewModel.addPropertyChangeListener(this);
//
//        setLayout(new BorderLayout(10, 10));
//        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
//
//        // --- Header Section ---
//        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
//        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
//        nutritionSummaryLabel.setFont(new Font("Arial", Font.ITALIC, 13));
//        headerPanel.add(titleLabel);
//        headerPanel.add(nutritionSummaryLabel);
//        add(headerPanel, BorderLayout.NORTH);
//
//        // --- Center Details Section ---
//        JPanel contentPanel = new JPanel();
//        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
//
//        // Description
//        descriptionArea.setEditable(false);
//        descriptionArea.setLineWrap(true);
//        descriptionArea.setWrapStyleWord(true);
//        descriptionArea.setOpaque(false);
//
//        // Ingredients Area
//        ingredientsArea.setEditable(false);
//        ingredientsArea.setLineWrap(true);
//        ingredientsArea.setWrapStyleWord(true);
//
//        // Instructions Area
//        instructionsArea.setEditable(false);
//        instructionsArea.setLineWrap(true);
//        instructionsArea.setWrapStyleWord(true);
//
//        contentPanel.add(descriptionArea);
//        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
//        contentPanel.add(new JLabel("Ingredients:"));
//        contentPanel.add(new JScrollPane(ingredientsArea));
//        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
//        contentPanel.add(new JLabel("Instructions:"));
//        contentPanel.add(new JScrollPane(instructionsArea));
//
//        add(contentPanel, BorderLayout.CENTER);
//
//        // --- Footer Navigation Section ---
//        backButton.addActionListener(e -> switchView(RecommendationViewModel.VIEW_NAME));
//        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//        buttonPanel.add(backButton);
//        add(buttonPanel, BorderLayout.SOUTH);
//    }
//
//    private void switchView(String targetViewName) {
//        viewManagerModel.setState(targetViewName);
//        viewManagerModel.firePropertyChanged();
//    }
//
//    @Override
//    public void propertyChange(PropertyChangeEvent evt) {
//        if ("state".equals(evt.getPropertyName())) {
//            RecipeState state = (RecipeState) evt.getNewValue();
//
//            if (state.getRecipeError() != null) {
//                JOptionPane.showMessageDialog(this, state.getRecipeError(), "Error", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//
//            titleLabel.setText(state.getRecipeName());
//            descriptionArea.setText(state.getDescription());
//
//            // Build single aggregated string for nutrition header
//            String nutritionText = String.format("Calories: %s | Protein: %s | Carbs: %s | Fat: %s | Sugar: %s",
//                    state.getCalories(), state.getProtein(), state.getCarbs(), state.getFat(), state.getSugar());
//            nutritionSummaryLabel.setText(nutritionText);
//
//            // Directly passes multi-line String to UI
//            ingredientsArea.setText(state.getIngredients());
//            instructionsArea.setText(state.getInstructions());
//        }
//    }
//}


import enery.interface_adapter.shared.ViewManagerModel;
import enery.interface_adapter.recipe.RecipeState;
import enery.interface_adapter.recipe.RecipeViewModel;
import enery.interface_adapter.recommendation.RecommendationViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class RecipeView extends JPanel implements PropertyChangeListener {
    public static final String VIEW_NAME = "recipe view"; // Updated to static final constant

    private final RecipeViewModel recipeViewModel;
    private final ViewManagerModel viewManagerModel; // Replaced CardLayout/JPanel with ViewManagerModel

    private final JLabel titleLabel = new JLabel("", SwingConstants.CENTER);
    private final JLabel nutritionSummaryLabel = new JLabel("", SwingConstants.CENTER);
    private final JTextArea descriptionArea = new JTextArea(3, 30);
    private final JTextArea ingredientsArea = new JTextArea(8, 30);
    private final JTextArea instructionsArea = new JTextArea(8, 30);
    private final JButton backButton = new JButton("Back");

    public RecipeView(RecipeViewModel recipeViewModel, ViewManagerModel viewManagerModel) {
        this.recipeViewModel = recipeViewModel;
        this.viewManagerModel = viewManagerModel;
        this.recipeViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // --- Header Section ---
        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        nutritionSummaryLabel.setFont(new Font("Arial", Font.ITALIC, 13));
        headerPanel.add(titleLabel);
        headerPanel.add(nutritionSummaryLabel);
        add(headerPanel, BorderLayout.NORTH);

        // --- Center Details Section ---
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Description
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setOpaque(false);

        // Ingredients Area
        ingredientsArea.setEditable(false);
        ingredientsArea.setLineWrap(true);
        ingredientsArea.setWrapStyleWord(true);

        // Instructions Area
        instructionsArea.setEditable(false);
        instructionsArea.setLineWrap(true);
        instructionsArea.setWrapStyleWord(true);

        contentPanel.add(descriptionArea);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(new JLabel("Ingredients:"));
        contentPanel.add(new JScrollPane(ingredientsArea));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(new JLabel("Instructions:"));
        contentPanel.add(new JScrollPane(instructionsArea));

        add(contentPanel, BorderLayout.CENTER);

        // --- Footer Navigation Section ---
        backButton.addActionListener(e -> switchView(RecommendationViewModel.VIEW_NAME));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void switchView(String targetViewName) {
        viewManagerModel.setState(targetViewName);
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            RecipeState state = (RecipeState) evt.getNewValue();

            if (state.getRecipeError() != null) {
                JOptionPane.showMessageDialog(this, state.getRecipeError(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            titleLabel.setText(state.getRecipeName());
            descriptionArea.setText(state.getDescription());

            // Build single aggregated string for nutrition header
            String nutritionText = String.format("Calories: %s | Protein: %s | Carbs: %s | Fat: %s | Sugar: %s",
                    state.getCalories(), state.getProtein(), state.getCarbs(), state.getFat(), state.getSugar());
            nutritionSummaryLabel.setText(nutritionText);

            // Directly passes multi-line String to UI
            ingredientsArea.setText(state.getIngredients());
            instructionsArea.setText(state.getInstructions());
        }
    }
}