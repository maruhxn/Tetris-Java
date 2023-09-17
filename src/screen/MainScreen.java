package screen;

import menu.ExitMenu;
import menu.ScoreboardMenu;
import menu.SettingMenu;
import menu.StartMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class MainScreen extends Screen {
    private final List<JButton> menus = new ArrayList<>();

    private int selectedIndex = 0;

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

        setKeyListener();

        setMenuColor(Color.WHITE, Color.BLACK);
    }

    private void setKeyListener() {
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                setMenuColor(Color.BLACK, Color.WHITE);

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_DOWN:
                        selectedIndex = Math.min(menus.size() - 1, selectedIndex + 1);
                        break;
                    case KeyEvent.VK_UP:
                        selectedIndex = Math.max(0, selectedIndex - 1);
                        break;
                    case KeyEvent.VK_ENTER:
                        menus.get(selectedIndex).doClick();
                        removeKeyListener(this);
                        break;
                }

                setMenuColor(Color.WHITE, Color.BLACK);

            }
        });
    }

    private void setMenuColor(Color bgColor, Color textColor) {
        menus.get(selectedIndex).setBackground(bgColor);
        menus.get(selectedIndex).setForeground(textColor);
    }
}
