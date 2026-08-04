package enery.view.HealthAssessment;

import enery.interface_adapter.health_assessment.HealthAssessmentController;
import enery.interface_adapter.health_assessment.HealthAssessmentState;
import enery.interface_adapter.health_assessment.HealthAssessmentViewModel;
import enery.use_case.tracking.TrackingOutputData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class HealthAssessmentView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = "health assessment";

    private final HealthAssessmentViewModel viewModel;
    private final HealthAssessmentController controller;

    private final JLabel overallScoreLabel = new JLabel("Overall Score: -");
    private final JLabel severityLabel = new JLabel("Severity: -");
    private final JLabel dietScoreLabel = new JLabel("Diet Score: -");
    // private final JLabel exerciseScoreLabel = new JLabel("Diet Score: -");


    private final JTextArea dietInsightsArea = new JTextArea(5, 30);
    private final JTextArea dietActionableItemsArea = new JTextArea(5, 30);

    private final JButton generateButton = new JButton("Generate Assessment");

    public HealthAssessmentView(HealthAssessmentViewModel viewModel, HealthAssessmentController controller) {
        this.viewModel = viewModel;
        this.controller = controller;

        this.viewModel.addPropertyChangeListener(this);

        setupUI();
    }

    private void setupUI() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        dietInsightsArea.setEditable(false);
        dietActionableItemsArea.setEditable(false);

        JLabel title = new JLabel("Health Assessment Report");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(title);

        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new GridLayout(3, 1));
        resultPanel.add(overallScoreLabel);
        resultPanel.add(severityLabel);
        resultPanel.add(dietScoreLabel);
        this.add(resultPanel);

        this.add(new JLabel("Diet Insights:"));
        this.add(new JScrollPane(dietInsightsArea));
        this.add(new JLabel("Actionable Items:"));
        this.add(new JScrollPane(dietActionableItemsArea));

        JPanel buttons = new JPanel();
        buttons.add(generateButton);
        this.add(buttons);

        generateButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(generateButton)) {
                            String assessmentId = viewModel.getState().getAssessmentId();
                            TrackingOutputData trackingData = viewModel.getState().getTrackingData();
                            controller.execute(assessmentId, trackingData);
                        }
                    }
                }
        );
    }


    @Override
    public void actionPerformed(ActionEvent e) {
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        HealthAssessmentState state = (HealthAssessmentState) evt.getNewValue();

        if (state.getAssessmentError() != null) {
            JOptionPane.showMessageDialog(this, state.getAssessmentError());
            return;
        }

        overallScoreLabel.setText("Overall Score: " + state.getOverallScore());
        severityLabel.setText("Severity: " + state.getSeverity());
        dietScoreLabel.setText("Diet Score: " + state.getDietScore());

        if (state.getDietInsights() != null) {
            dietInsightsArea.setText(String.join("\n- ", state.getDietInsights()));
        }

        if (state.getDietActionableItems() != null) {
            dietActionableItemsArea.setText(String.join("\n- ", state.getDietActionableItems()));
        }
    }
}