package global.menu;

import global.screen.GameScreen;
import global.screen.Screen;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartMenu extends Menu {
    public StartMenu(String text) {
        super(text);
    }

    @Override
    public void setActionListener() {
        super.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame client = extractClient();
                Screen gameScreen = GameScreen.getInstance();

                client.setContentPane(gameScreen);
                client.validate();
            }
        });
    }
}
