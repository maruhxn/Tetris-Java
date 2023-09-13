package global.screen;

import global.menu.BackToMainScreen;

import javax.swing.*;
import java.awt.*;

public abstract class Screen extends JPanel {

    public Screen() {
        setBackground(Color.BLACK);
        setForeground(Color.white);
        if (!(this instanceof MainScreen)) {
            add(new BackToMainScreen());
        }
    }
}
