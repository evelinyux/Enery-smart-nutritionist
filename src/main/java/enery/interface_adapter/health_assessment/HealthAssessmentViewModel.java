package enery.interface_adapter.health_assessment;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class HealthAssessmentViewModel {
    public static final String viewName = "health assessment";

    private HealthAssessmentState state = new HealthAssessmentState();
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void setState(HealthAssessmentState state) {
        this.state = state;
    }

    public HealthAssessmentState getState() {
        return state;
    }

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
