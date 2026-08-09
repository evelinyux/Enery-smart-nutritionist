package enery.view.recommendation;

import enery.entity.recipe.Recipe;
import enery.interface_adapter.recipe.RecipePresenter;
import enery.interface_adapter.recommendation.RecommendationState;
import enery.interface_adapter.recommendation.RecommendationViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class RecommendationView extends JPanel implements PropertyChangeListener {

    public final String viewName = "recommendation";
    private final RecommendationViewModel viewModel;
    private final RecipePresenter recipePresenter;
    private final CardLayout cardLayout;
    private final JPanel viewsPanel;

    // Swing UI Components
    private final JPanel recipesPanel = new JPanel();
    private final JPanel workoutsPanel = new JPanel();

    public RecommendationView(RecommendationViewModel viewModel,
                              RecipePresenter recipePresenter,
                              CardLayout cardLayout,
                              JPanel viewsPanel) {
        this.viewModel = viewModel;
        this.recipePresenter = recipePresenter;
        this.cardLayout = cardLayout;
        this.viewsPanel = viewsPanel;

        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel title = new JLabel("Your Daily Recommendations");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(title);
        add(Box.createRigidArea(new Dimension(0, 20)));

        // Setup Recipe & Workout Panels
        setupSectionHeaders();

        add(recipesPanel);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(workoutsPanel);

        updateViewFromState(viewModel.getState());
    }

    private void setupSectionHeaders() {
        recipesPanel.setLayout(new BoxLayout(recipesPanel, BoxLayout.Y_AXIS));
        recipesPanel.setBorder(BorderFactory.createTitledBorder("Recommended Recipes"));

        workoutsPanel.setLayout(new BoxLayout(workoutsPanel, BoxLayout.Y_AXIS));
        workoutsPanel.setBorder(BorderFactory.createTitledBorder("Recommended Workouts"));
    }

    private void updateViewFromState(RecommendationState state) {
        // --- Populate Recipes (as clickable links to RecipeView) ---
        recipesPanel.removeAll();
        List<Recipe> recipes = state.getRecommendedRecipes();

        if (recipes == null || recipes.isEmpty()) {
            recipesPanel.add(new JLabel("No recipe recommendations available."));
        } else {
            for (int i = 0; i < Math.min(3, recipes.size()); i++) {
                Recipe recipe = recipes.get(i);

                JButton linkButton = new JButton("<html><a href=''>" + recipe.getName() + "</a></html>");
                linkButton.setHorizontalAlignment(SwingConstants.LEFT);
                linkButton.setBorderPainted(false);
                linkButton.setOpaque(false);
                linkButton.setBackground(Color.WHITE);
                linkButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

                // Click action: populate RecipeState and switch screen
                linkButton.addActionListener(e -> {
                    recipePresenter.prepareRecipeView(recipe);
                    cardLayout.show(viewsPanel, "recipe view");
                });

                recipesPanel.add(linkButton);
            }
        }

        // --- Populate Workouts (Non-clickable names) ---
        workoutsPanel.removeAll();
        List<String> workouts = state.getRecommendedExercises();

        if (workouts == null || workouts.isEmpty()) {
            workoutsPanel.add(new JLabel("No workout recommendations available."));
        } else {
            for (int i = 0; i < Math.min(3, workouts.size()); i++) {
                JLabel workoutLabel = new JLabel((i + 1) + ". " + workouts.get(i));
                workoutLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                workoutLabel.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
                workoutsPanel.add(workoutLabel);
            }
        }

        recipesPanel.revalidate();
        recipesPanel.repaint();
        workoutsPanel.revalidate();
        workoutsPanel.repaint();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            RecommendationState state = (RecommendationState) evt.getNewValue();

            if (state.getRecommendationError() != null) {
                JOptionPane.showMessageDialog(this, state.getRecommendationError(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            updateViewFromState(state);
        }
    }
}