package component;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractScreen extends JPanel {
    public AbstractScreen() {
        setFocusable(true);
        setBackground(Color.BLACK);
        setForeground(Color.white);
    }
}
