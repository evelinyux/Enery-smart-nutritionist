package enery.view.shared;

import java.awt.CardLayout;
import java.beans.PropertyChangeEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

import enery.interface_adapter.shared.ViewManagerModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ViewManagerTest {

    @Test
    void statePropertyChangeSwitchesCard() {
        final CardLayout cardLayout = new CardLayout();
        final JPanel views = new JPanel(cardLayout);

        final JPanel firstView = new JPanel();
        final JPanel secondView = new JPanel();

        firstView.add(new JLabel("FIRST"));
        secondView.add(new JLabel("SECOND"));

        views.add(firstView, "first");
        views.add(secondView, "second");

        final ViewManagerModel model =
                new ViewManagerModel();

        final ViewManager manager =
                new ViewManager(
                        views,
                        cardLayout,
                        model
                );

        manager.propertyChange(
                new PropertyChangeEvent(
                        model,
                        "state",
                        "first",
                        "second"
                )
        );

        assertTrue(secondView.isVisible());
    }

    @Test
    void unrelatedPropertyDoesNotSwitchCard() {
        final CardLayout cardLayout = new CardLayout();
        final JPanel views = new JPanel(cardLayout);

        final JPanel firstView = new JPanel();
        final JPanel secondView = new JPanel();

        views.add(firstView, "first");
        views.add(secondView, "second");

        cardLayout.show(views, "first");

        final ViewManagerModel model =
                new ViewManagerModel();

        final ViewManager manager =
                new ViewManager(
                        views,
                        cardLayout,
                        model
                );

        manager.propertyChange(
                new PropertyChangeEvent(
                        model,
                        "somethingElse",
                        "first",
                        "second"
                )
        );

        assertTrue(firstView.isVisible());
    }
}
