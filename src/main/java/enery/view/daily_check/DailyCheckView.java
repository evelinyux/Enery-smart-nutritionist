package enery.view.daily_check;

import java.awt.Component;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import enery.interface_adapter.change_password.LoggedInState;
import enery.interface_adapter.change_password.LoggedInViewModel;
import enery.interface_adapter.daily_check.DailyCheckController;
import enery.interface_adapter.daily_check.DailyCheckState;
import enery.interface_adapter.daily_check.DailyCheckViewModel;
import enery.interface_adapter.shared.ViewManagerModel;

/**
 * The View for the Daily Check feature.
 * Displays whether the user has logged their meals and exercises for today.
 */
public class DailyCheckView extends JPanel implements PropertyChangeListener {

    public final String viewName = DailyCheckViewModel.VIEW_NAME;

    private final DailyCheckViewModel dailyCheckViewModel;
    private final ViewManagerModel viewManagerModel;
    private final LoggedInViewModel loggedInViewModel;

    private DailyCheckController dailyCheckController;

    private final JCheckBox breakfastCheck;
    private final JCheckBox lunchCheck;
    private final JCheckBox dinnerCheck;
    private final JCheckBox exerciseCheck;

    private final JButton checkStatusButton;
    private final JButton backButton;

    public DailyCheckView(
            DailyCheckViewModel dailyCheckViewModel,
            ViewManagerModel viewManagerModel,
            LoggedInViewModel loggedInViewModel) {

        this.dailyCheckViewModel = dailyCheckViewModel;
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;

        this.dailyCheckViewModel.addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        final JLabel title = new JLabel("Today's Daily Check");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        breakfastCheck = new JCheckBox("Breakfast Logged");
        breakfastCheck.setEnabled(false);
        breakfastCheck.setAlignmentX(Component.CENTER_ALIGNMENT);

        lunchCheck = new JCheckBox("Lunch Logged");
        lunchCheck.setEnabled(false);
        lunchCheck.setAlignmentX(Component.CENTER_ALIGNMENT);

        dinnerCheck = new JCheckBox("Dinner Logged");
        dinnerCheck.setEnabled(false);
        dinnerCheck.setAlignmentX(Component.CENTER_ALIGNMENT);

        exerciseCheck = new JCheckBox("Exercise Logged");
        exerciseCheck.setEnabled(false);
        exerciseCheck.setAlignmentX(Component.CENTER_ALIGNMENT);

        checkStatusButton = new JButton("Check Today's Status");
        checkStatusButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        configureListeners();

        this.add(Box.createVerticalStrut(20));
        this.add(title);
        this.add(Box.createVerticalStrut(20));
        this.add(breakfastCheck);
        this.add(Box.createVerticalStrut(10));
        this.add(lunchCheck);
        this.add(Box.createVerticalStrut(10));
        this.add(dinnerCheck);
        this.add(Box.createVerticalStrut(10));
        this.add(exerciseCheck);
        this.add(Box.createVerticalStrut(30));
        this.add(checkStatusButton);
        this.add(Box.createVerticalStrut(10));
        this.add(backButton);
    }

    private void configureListeners() {
        checkStatusButton.addActionListener(event -> {
            if (dailyCheckController != null) {
                LoggedInState loggedInState = loggedInViewModel.getState();
                String currentUsername = loggedInState.getUsername();

                dailyCheckController.execute(currentUsername);
            }
        });

        backButton.addActionListener(event -> {
            viewManagerModel.setState("home");
            viewManagerModel.firePropertyChanged();
        });
    }

    public void setDailyCheckController(DailyCheckController controller) {
        this.dailyCheckController = controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            DailyCheckState state = (DailyCheckState) evt.getNewValue();

            if (state.getDailyCheckError() != null) {
                JOptionPane.showMessageDialog(this, state.getDailyCheckError(), "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                breakfastCheck.setSelected(state.isBreakFastLogged());
                lunchCheck.setSelected(state.isLunchLogged());
                dinnerCheck.setSelected(state.isDinnerLogged());
                exerciseCheck.setSelected(state.isExerciseLogged());

                if (state.getSuccessMessage() != null && !state.getSuccessMessage().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, state.getSuccessMessage(), "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    public String getViewName() {
        return viewName;
    }
}