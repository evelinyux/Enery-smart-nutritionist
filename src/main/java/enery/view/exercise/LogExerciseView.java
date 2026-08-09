package enery.view.exercise;

import java.awt.Component;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import enery.entity.exercise.ExerciseSearchResult;
import enery.interface_adapter.shared.ViewManagerModel;
import enery.interface_adapter.change_password.LoggedInViewModel;
import enery.interface_adapter.log_exercise.LogExerciseController;
import enery.interface_adapter.log_exercise.LogExerciseState;
import enery.interface_adapter.log_exercise.LogExerciseViewModel;
import enery.interface_adapter.search_exercise.SearchExerciseController;
import enery.interface_adapter.search_exercise.SearchExerciseState;
import enery.interface_adapter.search_exercise.SearchExerciseViewModel;
import enery.view.home.HomeView;

/**
 * Swing view for searching and logging exercise activities.
 */
public final class LogExerciseView extends JPanel
        implements PropertyChangeListener {

    public static final String VIEW_NAME =
            "log exercise";

    private final SearchExerciseViewModel
            searchViewModel;

    private final ViewManagerModel
            viewManagerModel;

    private final LoggedInViewModel
            loggedInViewModel;

    private final LogExerciseViewModel
            logExerciseViewModel;

    private SearchExerciseController
            searchController;

    private LogExerciseController
            logController;

    private final JTextField activityField =
            new JTextField(15);

    private final JTextField durationField =
            new JTextField("30", 15);

    private final JComboBox<ExerciseSearchResult>
            resultsBox =
            new JComboBox<>();

    private final JLabel caloriesLabel =
            new JLabel("Calories burned: ");

    private final JLabel statusLabel =
            new JLabel();

    public LogExerciseView(
            SearchExerciseViewModel searchViewModel,
            LogExerciseViewModel logExerciseViewModel,
            ViewManagerModel viewManagerModel,
            LoggedInViewModel loggedInViewModel) {

        this.searchViewModel = searchViewModel;
        this.logExerciseViewModel =
                logExerciseViewModel;

        this.viewManagerModel =
                viewManagerModel;

        this.loggedInViewModel =
                loggedInViewModel;

        searchViewModel.addPropertyChangeListener(this);
        logExerciseViewModel
                .addPropertyChangeListener(this);

        setLayout(
                new BoxLayout(
                        this,
                        BoxLayout.Y_AXIS
                )
        );

        setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        15,
                        15,
                        15
                )
        );

        final JLabel title =
                new JLabel(
                        "Search and Log Exercise"
                );

        title.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        final JPanel searchPanel =
                new JPanel(
                        new GridLayout(
                                0,
                                2,
                                5,
                                5
                        )
                );

        searchPanel.add(
                new JLabel("Exercise")
        );

        searchPanel.add(activityField);

        searchPanel.add(
                new JLabel(
                        "Duration (minutes)"
                )
        );

        searchPanel.add(durationField);

        searchPanel.add(
                new JLabel("Matches")
        );

        searchPanel.add(resultsBox);

        final JButton searchButton =
                new JButton("Search");

        final JButton logButton =
                new JButton("Log Exercise");

        final JButton backButton =
                new JButton("Back to Home");

        searchButton.addActionListener(
                event -> searchExercises()
        );

        logButton.addActionListener(
                event -> logSelectedExercise()
        );

        backButton.addActionListener(event -> {
            viewManagerModel.setState(
                    HomeView.VIEW_NAME
            );

            viewManagerModel.firePropertyChanged();
        });

        resultsBox.addActionListener(
                event -> updateSelectedResult()
        );

        add(title);
        add(searchPanel);
        add(caloriesLabel);
        add(searchButton);
        add(logButton);
        add(backButton);
        add(statusLabel);
    }

    private void searchExercises() {
        if (searchController == null) {
            statusLabel.setText(
                    "Exercise search is not configured."
            );
            return;
        }

        try {
            final int duration =
                    Integer.parseInt(
                            durationField.getText()
                    );

            final String username =
                    loggedInViewModel
                            .getState()
                            .getUsername();

            searchController.execute(
                    username,
                    activityField.getText(),
                    duration
            );
        }
        catch (NumberFormatException exception) {
            statusLabel.setText(
                    "Duration must be a number."
            );
        }
    }

    private void logSelectedExercise() {
        if (logController == null) {
            statusLabel.setText(
                    "Exercise logging is not configured."
            );
            return;
        }

        final ExerciseSearchResult selected =
                (ExerciseSearchResult)
                        resultsBox
                                .getSelectedItem();

        if (selected == null) {
            statusLabel.setText(
                    "Select an exercise result first."
            );
            return;
        }

        final String username =
                loggedInViewModel
                        .getState()
                        .getUsername();

        logController.execute(
                username,
                selected.getExerciseName(),
                selected.getDurationInMinutes(),
                selected.getTotalCalories(),
                LocalDate.now()
        );
    }

    private void updateSelectedResult() {
        final ExerciseSearchResult selected =
                (ExerciseSearchResult)
                        resultsBox
                                .getSelectedItem();

        if (selected == null) {
            caloriesLabel.setText(
                    "Calories burned: "
            );
        }
        else {
            caloriesLabel.setText(
                    "Calories burned: "
                            + selected
                            .getTotalCalories()
            );
        }
    }

    @Override
    public void propertyChange(
            PropertyChangeEvent event) {

        if (!"state".equals(
                event.getPropertyName())) {

            return;
        }

        if (event.getSource()
                == searchViewModel) {

            final SearchExerciseState state =
                    searchViewModel.getState();

            resultsBox.removeAllItems();

            for (ExerciseSearchResult result
                    : state.getResults()) {

                resultsBox.addItem(result);
            }

            if (!state.getErrorMessage()
                    .isBlank()) {

                statusLabel.setText(
                        state.getErrorMessage()
                );
            }
            else {
                statusLabel.setText(
                        state.getResults().size()
                                + " exercise matches found."
                );
            }

            updateSelectedResult();
        }
        else if (event.getSource()
                == logExerciseViewModel) {

            final LogExerciseState state =
                    logExerciseViewModel
                            .getState();

            if (!state.getErrorMessage()
                    .isBlank()) {

                statusLabel.setText(
                        state.getErrorMessage()
                );
            }
            else {
                statusLabel.setText(
                        state.getSuccessMessage()
                );
            }
        }
    }

    public String getViewName() {
        return VIEW_NAME;
    }

    public void setSearchController(
            SearchExerciseController controller) {

        this.searchController = controller;
    }

    public void setLogController(
            LogExerciseController controller) {

        this.logController = controller;
    }
}
