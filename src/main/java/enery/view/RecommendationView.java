package view;

import enery.interface_adapter.recommendation.RecommendationState;
import enery.interface_adapter.recommendation.RecommendationViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URI;
import java.util.List;

public class RecommendationView extends JPanel implements PropertyChangeListener {

    public final String viewName = "recommendation";
    private final RecommendationViewModel viewModel;

    // Swing UI Components
    private final JPanel recipesPanel = new JPanel();
    private final JPanel workoutsPanel = new JPanel();

    public RecommendationView(RecommendationViewModel viewModel) {
        this.viewModel = viewModel;
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
        // --- Populate Recipes (as clickable links) ---
        recipesPanel.removeAll();
        List<String> recipes = state.getRecipeTitles();
        List<String> urls = state.getRecipeUrls();

        if (recipes.isEmpty()) {
            recipesPanel.add(new JLabel("No recipe recommendations available."));
        } else {
            for (int i = 0; i < Math.min(3, recipes.size()); i++) {
                String title = recipes.get(i);
                String url = (i < urls.size()) ? urls.get(i) : null;

                // Styled JButton that looks like a hyperlink
                JButton linkButton = new JButton("<html><a href=''>" + title + "</a></html>");
                linkButton.setHorizontalAlignment(SwingConstants.LEFT);
                linkButton.setBorderPainted(false);
                linkButton.setOpaque(false);
                linkButton.setBackground(Color.WHITE);
                linkButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

                linkButton.addActionListener(e -> {
                    if (url != null && !url.isEmpty()) {
                        openWebpage(url);
                    } else {
                        JOptionPane.showMessageDialog(this, "Opening recipe: " + title);
                    }
                });

                recipesPanel.add(linkButton);
            }
        }

        // --- Populate Workouts (Names) ---
        workoutsPanel.removeAll();
        List<String> workouts = state.getWorkoutNames();

        if (workouts.isEmpty()) {
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

    private void openWebpage(String urlString) {
        try {
            Desktop.getDesktop().browse(new URI(urlString));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Could not open link: " + urlString);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            RecommendationState state = (RecommendationState) evt.getNewValue();
            updateViewFromState(state);
        }
    }
}