//package enery.interface_adapter.recommendation;
//
//import java.beans.PropertyChangeListener;
//import java.beans.PropertyChangeSupport;
//
///**
// * ViewModel for the Recommendation use case.
// * Manages the RecommendationState and notifies observers (Views) when state updates occur.
// */
//public class RecommendationViewModel {
//
//    public static final String TITLE_LABEL = "Daily Recommendations View";
//    public static final String GET_RECOMMENDATIONS_BUTTON_LABEL = "Get Recommendations";
//
//    private RecommendationState state = new RecommendationState();
//    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
//
//    public RecommendationViewModel() {
//    }
//
//    public RecommendationState getState() {
//        return state;
//    }
//
//    public void setState(RecommendationState state) {
//        RecommendationState oldState = this.state;
//        this.state = state;
//        support.firePropertyChange("state", oldState, this.state);
//    }
//
//    /**
//     * Notifies all registered listeners (e.g., Swing Views) that the state has changed.
//     */
//    public void firePropertyChanged() {
//        support.firePropertyChange("state", null, this.state);
//    }
//
//    /**
//     * Adds a listener to observe changes in this ViewModel.
//     *
//     * @param listener The PropertyChangeListener (usually your View class)
//     */
//    public void addPropertyChangeListener(PropertyChangeListener listener) {
//        support.addPropertyChangeListener(listener);
//    }
//
//    /**
//     * Removes a listener.
//     */
//    public void removePropertyChangeListener(PropertyChangeListener listener) {
//        support.removePropertyChangeListener(listener);
//    }
//}
package enery.interface_adapter.recommendation;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * ViewModel for the Recommendation use case.
 * Manages the RecommendationState and notifies observers (Views) when state updates occur.
 */
public class RecommendationViewModel {

    public static final String VIEW_NAME = "recommendation";
    public static final String TITLE_LABEL = "Daily Recommendations View";
    public static final String GET_RECOMMENDATIONS_BUTTON_LABEL = "Get Recommendations";

    private RecommendationState state = new RecommendationState();
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public RecommendationViewModel() {
    }

    public RecommendationState getState() {
        return state;
    }

    public void setState(RecommendationState state) {
        RecommendationState oldState = this.state;
        this.state = state;
        support.firePropertyChange("state", oldState, this.state);
    }

    /**
     * Notifies all registered listeners (e.g., Swing Views) that the state has changed.
     */
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    /**
     * Adds a listener to observe changes in this ViewModel.
     *
     * @param listener The PropertyChangeListener (usually your View class)
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * Removes a listener.
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}