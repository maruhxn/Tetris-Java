package menu;

import constant.Constants;
import screen.GameScreen;
import client.GameClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;

public class StartMenu extends Menu {
    public StartMenu(String text) {
        super(text);
    }

    @Override
    public void setActionListener() {
        super.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameClient client = (GameClient) extractClient();
                client.switchPanel(new GameScreen());
            }
        });
    }
}
