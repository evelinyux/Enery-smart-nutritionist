package enery.view.shared;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * A panel containing a label and a text field.
 */
public class LabelTextPanel extends JPanel {

    /**
     * Creates a panel containing a label and text field.
     *
     * @param label the label
     * @param textField the text field
     */
    public LabelTextPanel(
            JLabel label,
            JTextField textField) {

        this.add(label);
        this.add(textField);
    }
}
