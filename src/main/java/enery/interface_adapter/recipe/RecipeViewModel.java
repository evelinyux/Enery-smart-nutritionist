package enery.interface_adapter.recipe;

import enery.interface_adapter.ViewModel;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class RecipeViewModel extends ViewModel {
    private RecipeState state = new RecipeState();
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public RecipeViewModel() {
        super("recipe view");
    }

    public RecipeState getState() {
        return state;
    }

    public void setState(RecipeState state) {
        RecipeState oldState = this.state;
        this.state = state;
        support.firePropertyChange("state", oldState, this.state);
    }

    @Override
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}