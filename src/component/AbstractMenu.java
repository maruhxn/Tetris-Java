package component;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractMenu extends JButton {

    public AbstractMenu(String text) {
        super(text);
        setBackground(Color.BLACK);
        setForeground(Color.white);
        setFont(new Font("Courier", Font.BOLD, 12));
        setActionListener();
    }

    public abstract void setActionListener();

    protected JFrame extractClient() {
        return (JFrame) this.getTopLevelAncestor();
    }
}
