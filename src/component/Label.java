package component;

import javax.swing.*;
import java.awt.*;

public class Label extends JLabel {
    public Label(String text) {
        super(text);
        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
    }

    public Label(String text, int position) {
        super(text, position);
        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
    }
}
