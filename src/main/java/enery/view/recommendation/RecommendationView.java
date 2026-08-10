package enery.view.recommendation;

import enery.entity.recipe.Recipe;
import enery.interface_adapter.recipe.RecipePresenter;
import enery.interface_adapter.recipe.RecipeViewModel;
import enery.interface_adapter.recommendation.RecommendationController;
import enery.interface_adapter.recommendation.RecommendationState;
import enery.interface_adapter.change_password.LoggedInState;
import enery.interface_adapter.change_password.LoggedInViewModel;
import enery.interface_adapter.recommendation.RecommendationViewModel;

import javax.swing.*;
import java.awt.*;
import enery.interface_adapter.shared.ViewManagerModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import enery.view.home.HomeView;


public class RecommendationView extends JPanel implements PropertyChangeListener {


    private final LoggedInViewModel loggedInViewModel;
    public static final String VIEW_NAME = "recommendation";
    private final RecommendationViewModel viewModel;
    private final RecipePresenter recipePresenter;
    private final JPanel recipesContainer = new JPanel();
    //    private final CardLayout cardLayout;
//    private final JPanel viewsPanel;
    private RecommendationController recommendationController;
    private ViewManagerModel viewManagerModel;

    // Swing UI Components
    private final JPanel recipesPanel = new JPanel();
    private final JPanel workoutsPanel = new JPanel();

    //    public RecommendationView(RecommendationViewModel viewModel,
//                              LoggedInViewModel loggedInViewModel,
//                              RecipePresenter recipePresenter,
//                              CardLayout cardLayout,
//                              JPanel viewsPanel) {
//        this.viewModel = viewModel;
//        this.recipePresenter = recipePresenter;
//        this.cardLayout = cardLayout;
//        this.viewsPanel = viewsPanel;
//        this.loggedInViewModel = loggedInViewModel;
    public RecommendationView(RecommendationViewModel viewModel,
                              LoggedInViewModel loggedInViewModel,
                              RecipePresenter recipePresenter,
                              ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.recipePresenter = recipePresenter;
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;



        this.viewModel.addPropertyChangeListener(this);
//        this.loggedInViewModel.addPropertyChangeListener(this);

        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                System.out.println("[DEBUG RecView] View shown on screen via CardLayout.");
                RecommendationState state = viewModel.getState();
                if (state == null || state.getRecommendedRecipes() == null || state.getRecommendedRecipes().isEmpty() || viewModel.isStale()) {
                    fetchRecommendations();
                }
            }
        });

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
        add(Box.createRigidArea(new Dimension(0, 20)));
        JButton backButton = new JButton("Back to Home");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
//        backButton.addActionListener(e -> cardLayout.show(viewsPanel, HomeView.VIEW_NAME));
        backButton.addActionListener(e -> {switchView(HomeView.VIEW_NAME);});
        add(backButton);

        updateViewFromState(viewModel.getState());
    }

    private void switchView(String viewName) {
        viewManagerModel.setState(viewName);
        viewManagerModel.firePropertyChanged();
    }

    private void setupSectionHeaders() {
        recipesPanel.setLayout(new BoxLayout(recipesPanel, BoxLayout.Y_AXIS));
        recipesPanel.setBorder(BorderFactory.createTitledBorder("Recommended Recipes"));

        workoutsPanel.setLayout(new BoxLayout(workoutsPanel, BoxLayout.Y_AXIS));
        workoutsPanel.setBorder(BorderFactory.createTitledBorder("Recommended Workouts"));
    }

    private void updateViewFromState(RecommendationState state) {
        System.out.println("[DEBUG RecView] Updating view UI components from state.");
        // --- Populate Recipes (as clickable links to RecipeView) ---
        recipesPanel.removeAll();
        workoutsPanel.removeAll();
        List<Recipe> recipes = state.getRecommendedRecipes();

        if (recipes == null || recipes.isEmpty()) {
            System.out.println("[DEBUG RecView] No recipes present in state.");
            recipesPanel.add(new JLabel("No recipe recommendations available."));
        } else {
            System.out.println("Rendering " + recipes.size() + " recipes.");

            for (int i = 0; i < Math.min(3, recipes.size()); i++) {
                Recipe recipe = recipes.get(i);

                // Create a button styled like a link for the recipe title
                JButton linkButton = new JButton("<html><a href=''>" + recipe.getName() + "</a></html>");
                linkButton.setHorizontalAlignment(SwingConstants.LEFT);
                linkButton.setBorderPainted(false);
                linkButton.setOpaque(false);
                linkButton.setBackground(Color.WHITE);
                linkButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

                // Action Listener: when clicked, pass recipe to presenter and switch view
                linkButton.addActionListener(e -> {
                    System.out.println("[DEBUG RecView] Clicked recipe: " + recipe.getName());
                    if (recipePresenter != null) {
                        recipePresenter.prepareRecipeView(recipe);

                        // Tell ViewManagerModel to switch to the recipe view
                        viewManagerModel.setState(RecipeViewModel.VIEW_NAME);
                        viewManagerModel.firePropertyChanged();
                    }
                });

                recipesPanel.add(linkButton);
            }

        }

        // --- Populate Workouts (Non-clickable names) ---
        workoutsPanel.removeAll();
        List<String> workouts = state.getRecommendedExercises();

        if (workouts == null || workouts.isEmpty()) {
            System.out.println("No workouts present in state.");
            workoutsPanel.add(new JLabel("No workout recommendations available."));
        } else {
            System.out.println("[DEBUG RecView] Rendering " + workouts.size() + " workouts.");
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
        System.out.println("[DEBUG RecView] PropertyChangeEvent received: propertyName="
                + evt.getPropertyName() + ", source=" + evt.getSource().getClass().getSimpleName());
        if ("state".equals(evt.getPropertyName())) {
            if (evt.getSource() == viewModel) {
                RecommendationState state = (RecommendationState) evt.getNewValue();

                if (state.getRecommendationError() != null) {
                    JOptionPane.showMessageDialog(this, state.getRecommendationError(), "Error", JOptionPane.ERROR_MESSAGE);
                    System.out.println(state.getRecommendationError());
                    return;
                }

                updateViewFromState(state);
                // If successful state update received then update stale flag
                viewModel.setStale(false);


            } else if (evt.getSource() == loggedInViewModel) {
                RecommendationState state = viewModel.getState();
                if (state == null || state.getRecommendedRecipes() == null || state.getRecommendedRecipes().isEmpty()) {
                    System.out.println("fetching recommendations in propertyChane");
                    fetchRecommendations();
                }

            }
        }
    }
    /**
     * Triggers recommendation execution for the currently logged in user.
     */
    public void fetchRecommendations() {

        System.out.println("[DEBUG RecView] fetchRecommendations() invoked.");
        if (recommendationController != null&& loggedInViewModel != null) {
            LoggedInState state = loggedInViewModel.getState();
            if (state != null && state.getUsername() != null && !state.getUsername().isBlank()) {
                recommendationController.execute(state.getUsername());
            }
        }
    }
    public void setRecommendationController(RecommendationController recommendationController) {
        this.recommendationController = recommendationController;
        System.out.println("[DEBUG RecView] setRecommendationController() invoked.");
    }

}
