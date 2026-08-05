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

    private final JLabel dailyProgressLabel = new JLabel();
    private final JProgressBar dailyProgressBar = new JProgressBar(0, 100);


    private final JButton recommendationButton = new JButton("Recommend a meal");
    private final JButton recalculateButton = new JButton("Recalculate Daily Goal");
    private JButton backButton = new JButton("Home");
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
        dailyProgressBar.setValue(0);
        dailyProgressBar.setStringPainted(true);
        recommendationButton.setVisible(false);
        contents.add(dailyProgressLabel);
        contents.add(dailyProgressBar);
        contents.add(recommendationButton);
        contents.add(backButton);

        backButton.addActionListener(event -> {
            viewManagerModel.setState(HomeView.VIEW_NAME);
            viewManagerModel.firePropertyChanged();});

        add(title);
        add(contents);
    }

    private String formatDailyProgressLabel(Double dailyProgressPercent) {
        Double todaysCalories = progressViewModel.getState().getTodaysCalories();
        Double dailyCalorieGoal = progressViewModel.getState().getDailyCalorieGoal();

        if (dailyProgressPercent < 1) {
            return "Your goal is " +  dailyCalorieGoal.toString() + " kcal. You consumed " + todaysCalories.toString() +
                    " kcal. That is " + dailyProgressPercent.toString() + "% of your daily goal. We can recommend you a"
                    + " meal to get you closer to your goal.";
        }
        else {
            return "You consumed " + todaysCalories.toString() + "kCal. You achieved your Daily Goal!";
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state") && VIEW_NAME.equals(evt.getNewValue())) {
            String username = loggedInViewModel.getState().getUsername();
            if (progressController != null) {
                progressController.execute(username);
            }
        }
        else if (evt.getPropertyName().equals("state") && evt.getSource() == progressViewModel) {
            final double dailyProgressPercent = progressViewModel.getState().getDailyProgressPercent();
            updateProgressUI(dailyProgressPercent);
        }
    }

    private void updateProgressUI(double dailyProgressPercent) {
        dailyProgressLabel.setText(formatDailyProgressLabel(dailyProgressPercent));
        dailyProgressBar.setValue((int) (dailyProgressPercent * 100));
        recommendationButton.setVisible(dailyProgressPercent < 1);
    }

    public String getViewName() {
        return this.VIEW_NAME;
    }

    public void setProgressController(ProgressController progressController) {
        this.progressController = progressController;
    }
}