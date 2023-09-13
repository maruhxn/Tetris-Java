package global.menu;

import global.screen.ScoreboardScreen;
import global.screen.Screen;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScoreboardMenu extends Menu {
    public ScoreboardMenu(String text) {
        super(text);
    }

    @Override
    public void setActionListener() {
        super.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame client = extractClient();

                Screen scoreboardScreen = ScoreboardScreen.getInstance();
                client.setContentPane(scoreboardScreen);
                client.validate();
            }
        });
    }
}
