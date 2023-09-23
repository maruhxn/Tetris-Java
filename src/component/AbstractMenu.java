package component;

import javax.swing.*;
import java.awt.*;

import static util.Utility.getSmallFont;

public abstract class AbstractMenu extends JButton {

    public AbstractMenu(String text) {
        super(text);
        setBackground(Color.BLACK);
        setForeground(Color.white);
        setFont(getSmallFont());
        setActionListener();
    }

    public abstract void setActionListener();

    protected JFrame extractClient() {
        return (JFrame) this.getTopLevelAncestor();
    }
}
