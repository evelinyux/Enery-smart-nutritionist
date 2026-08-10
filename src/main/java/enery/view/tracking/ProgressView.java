package enery.view.tracking;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import enery.interface_adapter.shared.ViewManagerModel;
import enery.interface_adapter.change_password.LoggedInViewModel;
import enery.interface_adapter.progress.ProgressController;
import enery.interface_adapter.progress.ProgressViewModel;
import enery.data_access.HealthAssessment_database.NutrientStandards;
import enery.view.home.HomeView;

/**
 * Swing view for viewing the user's progress towards their daily goal.
 */
public final class ProgressView extends JPanel implements PropertyChangeListener {
    private static final String VIEW_NAME = "progress";
    private static final double PERCENTAGE_FACTOR = 100.0;
    private static final double TENTHS_ROUNDING_FACTOR = 10.0;
    private static final int SMALL_GAP = 5;
    private static final int MEDIUM_GAP = 10;
    private static final Dimension PROGRESS_BAR_SIZE = new Dimension(250, 20);

    private final ProgressViewModel progressViewModel;
    private final ViewManagerModel viewManagerModel;
    private final LoggedInViewModel loggedInViewModel;

    private final JLabel title = new JLabel("Plan Progress");

    private final JButton updateProgressButton = new JButton("View Plan Progress");
    private final JLabel dailyProgressLabel = new JLabel();
    private final JProgressBar dailyProgressBar = new JProgressBar(0, 100);
    private final JLabel recommendationLabel = new JLabel(
            "We can recommend you a meal to help you reach your goal.");
    private final JButton recommendationButton = new JButton("Recommend a meal");
    private final JTextPane nutrientsLabel = new JTextPane();
    private final JLabel planProgressLabel = new JLabel("");
    private final JProgressBar planProgressBar = new JProgressBar(0, 100);
    private final JButton backButton = new JButton("Home");
    private final JLabel statusLabel = new JLabel();

    private ProgressController progressController;

    public ProgressView(
            ProgressViewModel progressViewModel,
            ViewManagerModel viewManagerModel,
            LoggedInViewModel loggedInViewModel) {

        this.progressViewModel = progressViewModel;
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;

        progressViewModel.addPropertyChangeListener(this);

        configureView();
    }

    private void configureView() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        updateProgressButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        dailyProgressLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        dailyProgressLabel.setVisible(false);

        dailyProgressBar.setAlignmentX(Component.CENTER_ALIGNMENT);
        dailyProgressBar.setValue(0);
        dailyProgressBar.setMaximumSize(PROGRESS_BAR_SIZE);
        dailyProgressBar.setStringPainted(true);
        dailyProgressBar.setVisible(false);

        recommendationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        recommendationLabel.setVisible(false);

        recommendationButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        recommendationButton.setVisible(false);

        nutrientsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nutrientsLabel.setEditable(false);
        nutrientsLabel.setOpaque(false);
        nutrientsLabel.setFocusable(false);
        nutrientsLabel.setVisible(false);
        nutrientsLabel.setFont(UIManager.getFont("Label.font"));

        StyledDocument doc = nutrientsLabel.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        planProgressLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        planProgressLabel.setVisible(false);

        planProgressBar.setAlignmentX(Component.CENTER_ALIGNMENT);
        planProgressBar.setValue(0);
        planProgressBar.setMaximumSize(PROGRESS_BAR_SIZE);
        planProgressBar.setStringPainted(true);
        planProgressBar.setVisible(false);

        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        updateProgressButton.addActionListener(event -> {
            updateProgress();
            if (progressViewModel.getState().getProgressError() == null) {
                updateProgressButton.setText("Update Plan Progress");
            }
        });

        recommendationButton.addActionListener(event -> {
            viewManagerModel.setState("recommendation");
            viewManagerModel.firePropertyChanged();
        });

        backButton.addActionListener(event -> {
            viewManagerModel.setState(HomeView.VIEW_NAME);
            viewManagerModel.firePropertyChanged();
        });

        add(title);
        add(Box.createVerticalStrut(MEDIUM_GAP));
        add(updateProgressButton);
        add(Box.createVerticalStrut(SMALL_GAP));
        add(dailyProgressLabel);
        add(Box.createVerticalStrut(SMALL_GAP));
        add(dailyProgressBar);
        add(Box.createVerticalStrut(SMALL_GAP));
        add(recommendationLabel);
        add(Box.createVerticalStrut(SMALL_GAP));
        add(recommendationButton);
        add(Box.createVerticalStrut(SMALL_GAP));
        add(nutrientsLabel);
        add(Box.createVerticalStrut(SMALL_GAP));
        add(planProgressLabel);
        add(Box.createVerticalStrut(SMALL_GAP));
        add(planProgressBar);
        add(Box.createVerticalStrut(SMALL_GAP));
        add(backButton);
        add(Box.createVerticalStrut(SMALL_GAP));
        add(statusLabel);
    }

    private String formatDailyProgressLabel() {
        final double todaysCalories = progressViewModel.getState().getTodaysCalories();
        final double dailyCalorieGoal = progressViewModel.getState().getDailyCalorieGoal();

        final double dailyProgressPercent = progressViewModel.getState().getDailyProgressPercent();

        String label = "";

        if (dailyProgressPercent < 1) {
            label = "Your goal is "
                    + dailyCalorieGoal
                    + " kcal. You consumed "
                    + todaysCalories
                    + " kcal. That is "
                    + Math.round((dailyProgressPercent * PERCENTAGE_FACTOR)
                    * TENTHS_ROUNDING_FACTOR / TENTHS_ROUNDING_FACTOR)
                    + "% of your daily goal.";
        }
        else {
            label = "You consumed " + todaysCalories + "kCal. You achieved your Daily Goal!";
        }

        return label;
    }

    private String formatNutrientsLabel() {
        NutrientStandards standards = new NutrientStandards();

        double protein = progressViewModel.getState().getTodaysProtein();
        double carbohydrates = progressViewModel.getState().getTodaysCarboydrates();
        double fats = progressViewModel.getState().getTodaysFats();

        return "Here is a summary of your nutrient intake for today:\nProtein: "
                + protein
                + "g / "
                + standards.getProteinInGrams()
                + "g.\nCarbohydrates: "
                + carbohydrates
                + "g / "
                + standards.getCarbohydratesInGrams()
                + "g.\nFats: "
                + fats
                + "g / "
                + standards.getFatInGrams()
                + "g.";
    }

    private String formatPlanProgressLabel() {
        final int daysSoFar = progressViewModel.getState().getDaysSoFar();
        final double totalCalories = progressViewModel.getState().getTotalCaloriesConsumed();
        final double totalCalorieGoal = progressViewModel.getState().getTotalCalorieGoal();
        final double totalProgressPercent = progressViewModel.getState().getTotalProgressPercent();

        return "Your total calorie goal is "
                + totalCalorieGoal
                + " kcal. Over the course of "
                + daysSoFar
                + " days, you consumed "
                + totalCalories
                + " kcal. This is "
                + Math.round((totalProgressPercent * PERCENTAGE_FACTOR)
                * TENTHS_ROUNDING_FACTOR / TENTHS_ROUNDING_FACTOR)
                + "% of your plan.";
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("error") && evt.getSource().equals(progressViewModel)) {
            statusLabel.setText(progressViewModel.getState().getProgressError());
        }
        else if (evt.getPropertyName().equals("data") && evt.getSource().equals(progressViewModel)) {
            updateProgressUi();
        }
    }

    private void updateProgress() {
        if (progressController == null) {
            statusLabel.setText("Progress is not configured");
        }
        else {
            final String username = loggedInViewModel.getState().getUsername();
            progressController.execute(username);
            if (progressViewModel.getState().getProgressError() == null) {
                progressViewModel.firePropertyChanged("data");
            }
        }
    }

    private void updateProgressUi() {
        final double dailyProgressPercent = progressViewModel.getState().getDailyProgressPercent();

        dailyProgressLabel.setText(formatDailyProgressLabel());
        dailyProgressLabel.setVisible(true);

        dailyProgressBar.setValue((int) (dailyProgressPercent * PERCENTAGE_FACTOR));
        dailyProgressBar.setVisible(true);

        recommendationLabel.setVisible(dailyProgressPercent < 1);
        recommendationButton.setVisible(dailyProgressPercent < 1);

        nutrientsLabel.setText(formatNutrientsLabel());
        nutrientsLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, nutrientsLabel.getPreferredSize().height));
        nutrientsLabel.setVisible(true);

        planProgressLabel.setText(formatPlanProgressLabel());
        planProgressLabel.setVisible(true);

        planProgressBar.setValue((int) (dailyProgressPercent * PERCENTAGE_FACTOR));
        planProgressBar.setVisible(true);

        if (progressViewModel.getState().getProgressError() == null) {
            statusLabel.setText("");
        }
    }

    public String getViewName() {
        return VIEW_NAME;
    }

    public void setProgressController(ProgressController progressController) {
        this.progressController = progressController;
    }
}
