package enery.view.shared;

import javax.swing.JLabel;
import javax.swing.JTextField;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class LabelTextPanelTest {

    @Test
    void constructorAddsLabelAndTextField() {
        final JLabel label = new JLabel("Username");
        final JTextField textField = new JTextField();

        final LabelTextPanel panel =
                new LabelTextPanel(label, textField);

        assertEquals(2, panel.getComponentCount());
        assertSame(label, panel.getComponent(0));
        assertSame(textField, panel.getComponent(1));
    }
}
