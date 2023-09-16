package menu;

import javax.swing.*;
import java.awt.*;

public abstract class Menu extends JButton {

    public Menu(String text) {
        super(text);
        setBackground(Color.BLACK);
        setFont(new Font("Courier", Font.BOLD, 12));
        setForeground(Color.white);
        setActionListener();
    }

    public abstract void setActionListener();

    protected JFrame extractClient() {
        return (JFrame) this.getTopLevelAncestor();
    }
}
