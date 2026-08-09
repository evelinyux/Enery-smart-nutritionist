package enery.view.tracking;

import java.awt.Component;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import enery.interface_adapter.shared.ViewManagerModel;
import enery.interface_adapter.change_password.LoggedInViewModel;
import enery.interface_adapter.tracking.TrackingController;
import enery.interface_adapter.tracking.TrackingState;
import enery.interface_adapter.tracking.TrackingViewModel;
import enery.use_case.tracking.DailyTrackingSummary;
import enery.use_case.tracking.TrackingPeriod;
import enery.view.home.HomeView;

/**
 * Displays daily or weekly nutrition and exercise summaries.
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

    private final JComboBox<TrackingPeriod> periodBox =
            new JComboBox<>(TrackingPeriod.values());

    private final JLabel periodLabel = new JLabel("-");
    private final JLabel caloriesConsumedLabel = new JLabel("-");
    private final JLabel caloriesBurnedLabel = new JLabel("-");
    private final JLabel netCaloriesLabel = new JLabel("-");
    private final JLabel proteinLabel = new JLabel("-");
    private final JLabel carbohydratesLabel = new JLabel("-");
    private final JLabel fatLabel = new JLabel("-");
    private final JLabel fiberLabel = new JLabel("-");
    private final JLabel sugarLabel = new JLabel("-");
    private final JLabel sodiumLabel = new JLabel("-");

    private final JLabel averageConsumedLabel = new JLabel("-");
    private final JLabel averageBurnedLabel = new JLabel("-");
    private final JLabel averageNetLabel = new JLabel("-");

    private final JLabel statusLabel = new JLabel();

    private final DefaultTableModel tableModel =
            new DefaultTableModel(
                    new Object[] {
                            "Date",
                            "Consumed",
                            "Burned",
                            "Net"
                    },
                    0
            ) {
                @Override
                public boolean isCellEditable(
                        int row,
                        int column) {

                    return false;
                }
            };

    private final JTable dailyTable =
            new JTable(tableModel);

    public TrackingView(
            TrackingViewModel trackingViewModel,
            ViewManagerModel viewManagerModel,
            LoggedInViewModel loggedInViewModel) {

        this.trackingViewModel = trackingViewModel;
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;

        trackingViewModel.addPropertyChangeListener(this);

        configureView();
    }

    /**
     * Creates and arranges the tracking screen.
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
                new JLabel("Nutrition and Exercise Tracking");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel controls =
                new JPanel(new GridLayout(0, 2, 5, 5));

        controls.add(new JLabel("Date (YYYY-MM-DD)"));
        controls.add(dateField);

        controls.add(new JLabel("Summary period"));
        controls.add(periodBox);

        final JPanel totals =
                new JPanel(new GridLayout(0, 2, 5, 5));

        totals.add(new JLabel("Displayed period"));
        totals.add(periodLabel);

        totals.add(new JLabel("Calories consumed"));
        totals.add(caloriesConsumedLabel);

        totals.add(new JLabel("Calories burned"));
        totals.add(caloriesBurnedLabel);

        totals.add(new JLabel("Net calories"));
        totals.add(netCaloriesLabel);

        totals.add(new JLabel("Protein"));
        totals.add(proteinLabel);

        totals.add(new JLabel("Carbohydrates"));
        totals.add(carbohydratesLabel);

        totals.add(new JLabel("Fat"));
        totals.add(fatLabel);

        totals.add(new JLabel("Fiber"));
        totals.add(fiberLabel);

        totals.add(new JLabel("Sugar"));
        totals.add(sugarLabel);

        totals.add(new JLabel("Sodium"));
        totals.add(sodiumLabel);

        totals.add(new JLabel("Daily average consumed"));
        totals.add(averageConsumedLabel);

        totals.add(new JLabel("Daily average burned"));
        totals.add(averageBurnedLabel);

        totals.add(new JLabel("Daily average net"));
        totals.add(averageNetLabel);

        final JButton loadButton =
                new JButton("Load Summary");

        final JButton todayButton =
                new JButton("Today");

        final JButton backButton =
                new JButton("Back to Home");

        loadButton.addActionListener(event ->
                loadSelectedSummary());

        todayButton.addActionListener(event -> {
            dateField.setText(LocalDate.now().toString());
            loadSelectedSummary();
        });

        backButton.addActionListener(event -> {
            viewManagerModel.setState(HomeView.VIEW_NAME);
            viewManagerModel.firePropertyChanged();
        });

        add(title);
        add(controls);
        add(loadButton);
        add(todayButton);
        add(totals);
        add(new JScrollPane(dailyTable));
        add(backButton);
        add(statusLabel);
    }

    /**
     * Requests either a daily or weekly tracking summary.
     */
    private void loadSelectedSummary() {
        if (trackingController == null) {
            statusLabel.setText(
                    "Tracking controller is not configured.");
            return;
        }

        try {
            final LocalDate date =
                    LocalDate.parse(
                            dateField.getText().trim());

            final TrackingPeriod period =
                    (TrackingPeriod) periodBox.getSelectedItem();

            final String username =
                    loggedInViewModel
                            .getState()
                            .getUsername();

            trackingController.execute(
                    username,
                    date,
                    period
            );
        }
        catch (DateTimeParseException exception) {
            statusLabel.setText(
                    "Enter the date using YYYY-MM-DD.");
        }
    }

    @Override
    public void propertyChange(
            PropertyChangeEvent event) {

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

        updateSummaryLabels(state);
        updateDailyTable(state);

        statusLabel.setText(
                "Tracking summary loaded.");
    }

    /**
     * Updates summary totals and averages.
     */
    private void updateSummaryLabels(
            TrackingState state) {

        periodLabel.setText(
                state.getStartDate()
                        + " to "
                        + state.getEndDate());

        caloriesConsumedLabel.setText(
                formatNumber(
                        state.getCaloriesConsumed(),
                        "kcal"));

        caloriesBurnedLabel.setText(
                formatNumber(
                        state.getCaloriesBurned(),
                        "kcal"));

        netCaloriesLabel.setText(
                formatNumber(
                        state.getNetCalories(),
                        "kcal"));

        proteinLabel.setText(
                formatNumber(
                        state.getProteinInGrams(),
                        "g"));

        carbohydratesLabel.setText(
                formatNumber(
                        state.getCarbohydratesInGrams(),
                        "g"));

        fatLabel.setText(
                formatNumber(
                        state.getFatInGrams(),
                        "g"));

        fiberLabel.setText(
                formatNumber(
                        state.getFiberInGrams(),
                        "g"));

        sugarLabel.setText(
                formatNumber(
                        state.getSugarInGrams(),
                        "g"));

        sodiumLabel.setText(
                formatNumber(
                        state.getSodiumInMilligrams(),
                        "mg"));

        averageConsumedLabel.setText(
                formatNumber(
                        state.getAverageCaloriesConsumed(),
                        "kcal"));

        averageBurnedLabel.setText(
                formatNumber(
                        state.getAverageCaloriesBurned(),
                        "kcal"));

        averageNetLabel.setText(
                formatNumber(
                        state.getAverageNetCalories(),
                        "kcal"));
    }

    /**
     * Displays one table row for every day in the selected period.
     */
    private void updateDailyTable(
            TrackingState state) {

        tableModel.setRowCount(0);

        for (DailyTrackingSummary summary
                : state.getDailySummaries()) {

            tableModel.addRow(
                    new Object[] {
                            summary.getDate(),
                            formatNumber(
                                    summary.getCaloriesConsumed(),
                                    "kcal"),
                            formatNumber(
                                    summary.getCaloriesBurned(),
                                    "kcal"),
                            formatNumber(
                                    summary.getNetCalories(),
                                    "kcal")
                    }
            );
        }
    }

    private static String formatNumber(
            double value,
            String unit) {

        return String.format(
                Locale.US,
                "%.1f %s",
                value,
                unit
        );
    }

    public String getViewName() {
        return VIEW_NAME;
    }

    public void setTrackingController(
            TrackingController controller) {

        this.trackingController = controller;
    }
}
