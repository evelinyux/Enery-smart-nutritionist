package enery.view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;

import enery.interface_adapter.ViewManagerModel;
import enery.interface_adapter.change_password.LoggedInViewModel;
import enery.interface_adapter.progress.ProgressController;
import enery.interface_adapter.progress.ProgressViewModel;

/**
 * Swing view for searching for food and logging a selected meal.
 *
 * <p>The selected USDA result provides nutrition values per reference serving,
 * usually 100 grams. The user enters the amount they actually consumed in
 * grams, and this view scales the nutrient totals before logging the meal.</p>
 */
public final class ProgressView extends JPanel implements PropertyChangeListener {
    public static final String VIEW_NAME = "progress";

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
    private final JButton recalculateButton = new JButton("Recalculate Daily Goal");
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
        setBorder(BorderFactory.createEmptyBorder(
                15,
                15,
                15,
                15
        ));

        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        dailyProgressLabel.setText(formatDailyProgressLabel(0.0));

        final JPanel contents = new JPanel(new GridLayout(0, 1, 5, 5));



        dailyProgressLabel.setVisible(false);

        dailyProgressBar.setValue(0);
        dailyProgressBar.setStringPainted(true);
        dailyProgressBar.setVisible(false);

        recommendationLabel.setVisible(false);

        recommendationButton.setVisible(false);

        contents.add(updateProgressButton);
        contents.add(dailyProgressLabel);
        contents.add(dailyProgressBar);
        contents.add(recommendationLabel);
        contents.add(recommendationButton);
        contents.add(backButton);
        contents.add(statusLabel);

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
        add(contents);
    }

    private String formatDailyProgressLabel(Double dailyProgressPercent) {
        double todaysCalories = progressViewModel.getState().getTodaysCalories();
        double dailyCalorieGoal = progressViewModel.getState().getDailyCalorieGoal();

        if (dailyProgressPercent < 1) {
            return "Your goal is " + dailyCalorieGoal + " kcal. You consumed " + todaysCalories + " kcal. That is "
                    + Math.round((dailyProgressPercent * 1000.0)) / 10.0 + "% of your daily goal.";
        }
        else {
            return "You consumed " + todaysCalories + "kCal. You achieved your Daily Goal!";
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("error") && evt.getSource().equals(progressViewModel)) {
            statusLabel.setText(progressViewModel.getState().getProgressError());
        }
        else if (evt.getPropertyName().equals("data") && evt.getSource().equals(progressViewModel)) {
            final double dailyProgressPercent = progressViewModel.getState().getDailyProgressPercent();
            updateProgressUI(dailyProgressPercent);
        }
    }

    private void updateProgress() {
        if (progressController == null) {
            statusLabel.setText("Progress is not configured");
            return;
        }

        final String username = loggedInViewModel.getState().getUsername();
        progressController.execute(username);
        if (progressViewModel.getState().getProgressError() == null) {
            progressViewModel.firePropertyChanged("data");
        }
    }

    private void updateProgressUI(double dailyProgressPercent) {
        dailyProgressLabel.setText(formatDailyProgressLabel(dailyProgressPercent));
        dailyProgressLabel.setVisible(true);

        dailyProgressBar.setValue((int) (dailyProgressPercent * 100));
        dailyProgressBar.setVisible(true);

        recommendationLabel.setVisible(dailyProgressPercent < 1);
        recommendationButton.setVisible(dailyProgressPercent < 1);
    }

    public String getViewName() {
        return this.VIEW_NAME;
    }

    public void setProgressController(ProgressController progressController) {
        this.progressController = progressController;
    }
}