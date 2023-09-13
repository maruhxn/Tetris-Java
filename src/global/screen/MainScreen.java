package global.screen;

import global.menu.ExitMenu;
import global.menu.ScoreboardMenu;
import global.menu.SettingMenu;
import global.menu.StartMenu;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainScreen extends Screen {
    private static MainScreen instance;
    private List<JButton> menus = new ArrayList<>();

    public MainScreen() {
        setLayout(new GridLayout(5, 1));

        JLabel gameTitle = new JLabel("TETRIS", SwingConstants.CENTER);
        add(gameTitle);
        gameTitle.setFont(new Font("Courier", Font.BOLD, 25));
        gameTitle.setForeground(Color.white);

        menus.add(new StartMenu("GAME START"));
        menus.add(new SettingMenu("SETTING"));
        menus.add(new ScoreboardMenu("SCORE BOARD"));
        menus.add(new ExitMenu("EXIT"));

        for (JButton menu : this.menus) {
            add(menu);
        }
    }

    public static MainScreen getInstance() {
        if (instance == null) {
            synchronized (MainScreen.class) {
                instance = new MainScreen();
            }
        }

        return instance;
    }
}
