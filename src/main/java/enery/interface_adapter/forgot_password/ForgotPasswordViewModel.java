package enery.interface_adapter.forgot_password;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * View Model for the Forgot Password View.
 */
public class ForgotPasswordViewModel {

    public static final String VIEW_NAME = "forgot password";

    private ForgotPasswordState state =
            new ForgotPasswordState();

    private final PropertyChangeSupport support =
            new PropertyChangeSupport(this);

    public ForgotPasswordState getState() {
        return state;
    }

    public void setState(ForgotPasswordState state) {
        this.state = state;
    }

    /**
     * Notifies listeners that the state changed.
     */
    public void firePropertyChanged() {
        support.firePropertyChange(
                "state",
                null,
                state
        );
    }

    /**
     * Adds a property change listener.
     *
     * @param listener listener
     */
    public void addPropertyChangeListener(
            PropertyChangeListener listener) {

        support.addPropertyChangeListener(listener);
    }
}