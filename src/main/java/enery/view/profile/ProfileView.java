package enery.view.profile;

import java.awt.Component;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import enery.entity.user.ActivityLevel;
import enery.entity.user.HealthGoal;
import enery.entity.user.Sex;
import enery.interface_adapter.profile.ProfileController;
import enery.interface_adapter.profile.ProfileState;
import enery.interface_adapter.profile.ProfileViewModel;

/**
 * Displays and edits the current user's profile and weight-goal plan.
 */
public final class ProfileView extends JPanel
        implements PropertyChangeListener {

    public static final String VIEW_NAME = "profile";

    private final ProfileViewModel profileViewModel;

    private ProfileController profileController;

    private final JTextField ageField =
            new JTextField(10);

    private final JTextField heightField =
            new JTextField(10);

    private final JTextField weightField =
            new JTextField(10);

    private final JTextField targetWeightField =
            new JTextField(10);

    private final JTextField durationDaysField =
            new JTextField(10);

    private final JComboBox<Sex> sexBox =
            new JComboBox<>(Sex.values());

    private final JComboBox<ActivityLevel> activityLevelBox =
            new JComboBox<>(ActivityLevel.values());

    private final JComboBox<HealthGoal> healthGoalBox =
            new JComboBox<>(HealthGoal.values());

    private final JCheckBox vegetarianBox =
            new JCheckBox("Vegetarian");

    private final JCheckBox veganBox =
            new JCheckBox("Vegan");

    private final JCheckBox glutenFreeBox =
            new JCheckBox("Gluten Free");

    private final JCheckBox dairyFreeBox =
            new JCheckBox("Dairy Free");

    private final JCheckBox halalBox =
            new JCheckBox("Halal");

    private final JLabel startDateLabel =
            new JLabel("Not created");

    private final JLabel endDateLabel =
            new JLabel("Not created");

    private final JLabel dailyGoalLabel =
            new JLabel("Not calculated");

    private final JLabel statusLabel =
            new JLabel();

    /**
     * Creates the profile screen.
     *
     * @param profileViewModel view model for the profile screen
     */
    public ProfileView(
            ProfileViewModel profileViewModel) {

        this.profileViewModel = profileViewModel;

        profileViewModel.addPropertyChangeListener(this);

        configureNumericFields();
        configureView();
    }

    /**
     * Creates and arranges the profile screen.
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
                new JLabel("Profile and Plan Setup");

        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel profileFields =
                new JPanel(new GridLayout(0, 2, 5, 5));

        profileFields.add(new JLabel("Age"));
        profileFields.add(ageField);

        profileFields.add(new JLabel("Height (cm)"));
        profileFields.add(heightField);

        profileFields.add(new JLabel("Current Weight (kg)"));
        profileFields.add(weightField);

        profileFields.add(new JLabel("Target Weight (kg)"));
        profileFields.add(targetWeightField);

        profileFields.add(new JLabel("Plan Duration (days)"));
        profileFields.add(durationDaysField);

        profileFields.add(new JLabel("Plan Start Date"));
        profileFields.add(startDateLabel);

        profileFields.add(new JLabel("Plan End Date"));
        profileFields.add(endDateLabel);

        profileFields.add(new JLabel("Sex"));
        profileFields.add(sexBox);

        profileFields.add(new JLabel("Activity Level"));
        profileFields.add(activityLevelBox);

        profileFields.add(new JLabel("Health Goal"));
        profileFields.add(healthGoalBox);

        profileFields.add(
                new JLabel("Estimated Daily Calorie Goal"));

        profileFields.add(dailyGoalLabel);

        final JPanel dietaryPanel =
                new JPanel(new GridLayout(0, 1));

        dietaryPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Dietary Restrictions"));

        dietaryPanel.add(vegetarianBox);
        dietaryPanel.add(veganBox);
        dietaryPanel.add(glutenFreeBox);
        dietaryPanel.add(dairyFreeBox);
        dietaryPanel.add(halalBox);

        final JButton saveButton =
                new JButton("Save Profile and Plan");

        final JButton backButton =
                new JButton("Back to Home");

        saveButton.addActionListener(event ->
                saveProfile());

        backButton.addActionListener(event ->
                switchToHomeView());

        add(title);
        add(profileFields);
        add(dietaryPanel);
        add(saveButton);
        add(backButton);
        add(statusLabel);
    }

    /**
     * Restricts profile fields to valid numeric input.
     */
    private void configureNumericFields() {
        setIntegerFilter(ageField);
        setDecimalFilter(heightField);
        setDecimalFilter(weightField);
        setDecimalFilter(targetWeightField);
        setIntegerFilter(durationDaysField);
    }

    /**
     * Allows only non-negative whole numbers.
     *
     * @param textField field to restrict
     */
    private void setIntegerFilter(
            JTextField textField) {

        final AbstractDocument document =
                (AbstractDocument) textField.getDocument();

        document.setDocumentFilter(
                new NumericDocumentFilter(false));
    }

    private void setDecimalFilter(
            JTextField textField) {

        final AbstractDocument document =
                (AbstractDocument) textField.getDocument();

        document.setDocumentFilter(
                new NumericDocumentFilter(true));
    }

    /**
     * Reads the form data and executes the profile use case.
     */
    private void saveProfile() {
        if (profileController == null) {
            statusLabel.setText(
                    "Profile controller is not configured.");
            return;
        }

        try {
            final int age =
                    Integer.parseInt(
                            ageField.getText().trim());

            final double height =
                    Double.parseDouble(
                            heightField.getText().trim());

            final double weight =
                    Double.parseDouble(
                            weightField.getText().trim());

            final double targetWeight =
                    Double.parseDouble(
                            targetWeightField.getText().trim());

            final int durationDays =
                    Integer.parseInt(
                            durationDaysField.getText().trim());

            final Sex sex =
                    (Sex) sexBox.getSelectedItem();

            final ActivityLevel activityLevel =
                    (ActivityLevel)
                            activityLevelBox.getSelectedItem();

            final HealthGoal healthGoal =
                    (HealthGoal)
                            healthGoalBox.getSelectedItem();

            final List<String> dietaryRestrictions =
                    getSelectedDietaryRestrictions();

            profileController.execute(
                    age,
                    height,
                    weight,
                    targetWeight,
                    durationDays,
                    sex,
                    activityLevel,
                    healthGoal,
                    dietaryRestrictions
            );
        }
        catch (NumberFormatException exception) {
            statusLabel.setText(
                    "Age and plan duration must be whole numbers, "
                            + "and height and weights must be valid numbers.");
        }
    }

    /**
     * Returns the dietary restrictions selected by the user.
     *
     * @return selected dietary restrictions
     */
    private List<String> getSelectedDietaryRestrictions() {
        final List<String> restrictions =
                new ArrayList<>();

        if (vegetarianBox.isSelected()) {
            restrictions.add("Vegetarian");
        }

        if (veganBox.isSelected()) {
            restrictions.add("Vegan");
        }

        if (glutenFreeBox.isSelected()) {
            restrictions.add("Gluten Free");
        }

        if (dairyFreeBox.isSelected()) {
            restrictions.add("Dairy Free");
        }

        if (halalBox.isSelected()) {
            restrictions.add("Halal");
        }

        return restrictions;
    }

    /**
     * Switches from the profile screen to the home screen.
     */
    private void switchToHomeView() {
        if (profileController == null) {
            statusLabel.setText(
                    "Profile controller is not configured.");
            return;
        }

        profileController.switchToHomeView();
    }

    /**
     * Updates the screen after the Profile View Model changes.
     *
     * @param event property-change event
     */
    @Override
    public void propertyChange(
            PropertyChangeEvent event) {

        if (!"state".equals(event.getPropertyName())) {
            return;
        }

        final ProfileState state =
                profileViewModel.getState();

        if (state.getProfileError() != null
                && !state.getProfileError().isBlank()) {

            statusLabel.setText(
                    state.getProfileError());
            return;
        }

        updateProfileFields(state);

        if (state.getSuccessMessage() != null) {
            statusLabel.setText(
                    state.getSuccessMessage());
        }
    }

    /**
     * Displays the profile and plan information stored in the state.
     *
     * @param state current profile state
     */
    private void updateProfileFields(
            ProfileState state) {

        if (state.getAge() > 0) {
            ageField.setText(
                    Integer.toString(state.getAge()));
        }

        if (state.getHeightInCentimetres() > 0) {
            heightField.setText(
                    Double.toString(
                            state.getHeightInCentimetres()));
        }

        if (state.getWeightInKilograms() > 0) {
            weightField.setText(
                    Double.toString(
                            state.getWeightInKilograms()));
        }

        if (state.getTargetWeightInKilograms() > 0) {
            targetWeightField.setText(
                    Double.toString(
                            state.getTargetWeightInKilograms()));
        }

        if (state.getDurationDays() > 0) {
            durationDaysField.setText(
                    Integer.toString(
                            state.getDurationDays()));
        }

        updatePlanDateLabels(state);

        if (state.getSex() != null) {
            sexBox.setSelectedItem(
                    state.getSex());
        }

        if (state.getActivityLevel() != null) {
            activityLevelBox.setSelectedItem(
                    state.getActivityLevel());
        }

        if (state.getHealthGoal() != null) {
            healthGoalBox.setSelectedItem(
                    state.getHealthGoal());
        }

        updateDailyGoalLabel(
                state.getDailyCalorieGoal());

        updateDietaryRestrictions(
                state.getDietaryRestrictions());
    }

    /**
     * Updates the plan start and end date labels.
     *
     * @param state current profile state
     */
    private void updatePlanDateLabels(
            ProfileState state) {

        if (state.getStartDate() == null
                || state.getStartDate().isBlank()) {

            startDateLabel.setText(
                    "Not created");
        }
        else {
            startDateLabel.setText(
                    state.getStartDate());
        }

        if (state.getEndDate() == null
                || state.getEndDate().isBlank()) {

            endDateLabel.setText(
                    "Not created");
        }
        else {
            endDateLabel.setText(
                    state.getEndDate());
        }
    }

    /**
     * Updates the estimated daily calorie goal display.
     *
     * @param dailyCalorieGoal estimated calories per day
     */
    private void updateDailyGoalLabel(
            Double dailyCalorieGoal) {

        if (dailyCalorieGoal == null) {
            dailyGoalLabel.setText(
                    "Not calculated");
        }
        else {
            dailyGoalLabel.setText(
                    String.format(
                            "%.0f kcal/day (estimated)",
                            dailyCalorieGoal));
        }
    }

    /**
     * Updates the dietary restriction checkboxes.
     *
     * @param restrictions saved dietary restrictions
     */
    private void updateDietaryRestrictions(
            List<String> restrictions) {

        vegetarianBox.setSelected(
                restrictions.contains("Vegetarian"));

        veganBox.setSelected(
                restrictions.contains("Vegan"));

        glutenFreeBox.setSelected(
                restrictions.contains("Gluten Free"));

        dairyFreeBox.setSelected(
                restrictions.contains("Dairy Free"));

        halalBox.setSelected(
                restrictions.contains("Halal"));
    }

    /**
     * Returns the CardLayout name of this view.
     *
     * @return profile view name
     */
    public String getViewName() {
        return VIEW_NAME;
    }

    /**
     * Sets the controller for the Profile Use Case.
     *
     * @param controller profile controller
     */
    public void setProfileController(
            ProfileController controller) {

        this.profileController = controller;
    }

    /**
     * Restricts text input to integers or decimal numbers.
     */
    private static final class NumericDocumentFilter
            extends DocumentFilter {

        private final boolean decimalAllowed;

        private NumericDocumentFilter(
                boolean decimalAllowed) {

            this.decimalAllowed = decimalAllowed;
        }

        @Override
        public void insertString(
                FilterBypass filterBypass,
                int offset,
                String text,
                AttributeSet attributes)
                throws BadLocationException {

            if (text == null) {
                return;
            }

            replace(
                    filterBypass,
                    offset,
                    0,
                    text,
                    attributes
            );
        }

        @Override
        public void replace(
                FilterBypass filterBypass,
                int offset,
                int length,
                String text,
                AttributeSet attributes)
                throws BadLocationException {

            final String currentText =
                    filterBypass.getDocument().getText(
                            0,
                            filterBypass.getDocument().getLength()
                    );

            final String replacementText =
                    text == null ? "" : text;

            final String newText =
                    currentText.substring(0, offset)
                            + replacementText
                            + currentText.substring(
                            offset + length);

            if (isValidInput(newText)) {
                filterBypass.replace(
                        offset,
                        length,
                        replacementText,
                        attributes
                );
            }
        }

        /**
         * Checks whether the proposed text is valid.
         *
         * @param text proposed field contents
         * @return whether the text is valid
         */
        private boolean isValidInput(
                String text) {

            if (text.isEmpty()) {
                return true;
            }

            if (decimalAllowed) {
                return text.matches("\\d*(\\.\\d*)?");
            }

            return text.matches("\\d*");
        }
    }
}