package enery.view;

import java.awt.Component;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import enery.interface_adapter.ViewManagerModel;
import enery.interface_adapter.change_password.LoggedInViewModel;
import enery.interface_adapter.tracking.TrackingController;
import enery.interface_adapter.tracking.TrackingState;
import enery.interface_adapter.tracking.TrackingViewModel;

/**
 * Displays a user's nutrition and exercise summary for one day.
 */
public final class TrackingView extends JPanel
        implements PropertyChangeListener {

    public static final String VIEW_NAME = "tracking";

    private final TrackingViewModel trackingViewModel;
    private final ViewManagerModel viewManagerModel;
    private final LoggedInViewModel loggedInViewModel;

    private TrackingController trackingController;

    private final JTextField dateField =
            new JTextField(LocalDate.now().toString(), 12);

    private final JLabel dateLabel = new JLabel("-");
    private final JLabel caloriesConsumedLabel = new JLabel("0.0 kcal");
    private final JLabel caloriesBurnedLabel = new JLabel("0.0 kcal");
    private final JLabel netCaloriesLabel = new JLabel("0.0 kcal");
    private final JLabel proteinLabel = new JLabel("0.0 g");
    private final JLabel carbohydratesLabel = new JLabel("0.0 g");
    private final JLabel fatLabel = new JLabel("0.0 g");
    private final JLabel statusLabel = new JLabel();

    /**
     * Creates the daily tracking screen.
     *
     * @param trackingViewModel tracking screen state
     * @param viewManagerModel application navigation model
     * @param loggedInViewModel currently logged-in user state
     */
    public TrackingView(
            TrackingViewModel trackingViewModel,
            ViewManagerModel viewManagerModel,
            LoggedInViewModel loggedInViewModel) {

        this.trackingViewModel = trackingViewModel;
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;

        trackingViewModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(
                15,
                15,
                15,
                15
        ));

        final JLabel title =
                new JLabel("Daily Tracking Summary");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel datePanel =
                new JPanel(new GridLayout(1, 3, 5, 5));

        final JButton loadButton =
                new JButton("Load Summary");

        datePanel.add(new JLabel("Date (YYYY-MM-DD)"));
        datePanel.add(dateField);
        datePanel.add(loadButton);

        final JPanel summaryPanel =
                new JPanel(new GridLayout(0, 2, 5, 5));

        summaryPanel.add(new JLabel("Date"));
        summaryPanel.add(dateLabel);

        summaryPanel.add(new JLabel("Calories consumed"));
        summaryPanel.add(caloriesConsumedLabel);

        summaryPanel.add(new JLabel("Calories burned"));
        summaryPanel.add(caloriesBurnedLabel);

        summaryPanel.add(new JLabel("Net calories"));
        summaryPanel.add(netCaloriesLabel);

        summaryPanel.add(new JLabel("Protein"));
        summaryPanel.add(proteinLabel);

        summaryPanel.add(new JLabel("Carbohydrates"));
        summaryPanel.add(carbohydratesLabel);

        summaryPanel.add(new JLabel("Fat"));
        summaryPanel.add(fatLabel);

        final JButton todayButton =
                new JButton("Today");

        final JButton backButton =
                new JButton("Back to Home");

        loadButton.addActionListener(event ->
                loadSelectedDate());

        todayButton.addActionListener(event -> {
            dateField.setText(LocalDate.now().toString());
            loadSelectedDate();
        });

        backButton.addActionListener(event -> {
            viewManagerModel.setState(HomeView.VIEW_NAME);
            viewManagerModel.firePropertyChanged();
        });

        add(title);
        add(datePanel);
        add(summaryPanel);
        add(todayButton);
        add(backButton);
        add(statusLabel);
    }

    /**
     * Requests tracking data for the date entered by the user.
     */
    private void loadSelectedDate() {
        if (trackingController == null) {
            statusLabel.setText(
                    "Tracking controller is not configured.");
            return;
        }

        try {
            final LocalDate date =
                    LocalDate.parse(dateField.getText().trim());

            final String username =
                    loggedInViewModel
                            .getState()
                            .getUsername();

            trackingController.execute(username, date);
        }
        catch (DateTimeParseException exception) {
            statusLabel.setText(
                    "Enter the date using YYYY-MM-DD.");
        }
    }

    /**
     * Updates the displayed totals when tracking state changes.
     *
     * @param event tracking view-model event
     */
    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (!"state".equals(event.getPropertyName())) {
            return;
        }

        final TrackingState state =
                trackingViewModel.getState();

        if (!state.getErrorMessage().isBlank()) {
            statusLabel.setText(
                    state.getErrorMessage());
            return;
        }

        dateLabel.setText(state.getDate().toString());
        caloriesConsumedLabel.setText(
                state.getCaloriesConsumed() + " kcal");
        caloriesBurnedLabel.setText(
                state.getCaloriesBurned() + " kcal");
        netCaloriesLabel.setText(
                state.getNetCalories() + " kcal");
        proteinLabel.setText(
                state.getProteinInGrams() + " g");
        carbohydratesLabel.setText(
                state.getCarbohydratesInGrams() + " g");
        fatLabel.setText(
                state.getFatInGrams() + " g");

        statusLabel.setText(
                "Tracking summary loaded.");
    }

    /**
     * Returns this view's CardLayout name.
     *
     * @return tracking view name
     */
    public String getViewName() {
        return VIEW_NAME;
    }

    /**
     * Sets the tracking controller.
     *
     * @param controller tracking controller
     */
    public void setTrackingController(
            TrackingController controller) {

        this.trackingController = controller;
    }
}
