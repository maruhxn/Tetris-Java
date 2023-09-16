package menu;

import screen.SettingScreen;
import client.GameClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingMenu extends Menu {
    public SettingMenu(String text) {
        super(text);
    }

    @Override
    public void setActionListener() {
        super.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameClient client = (GameClient) extractClient();
                client.switchPanel(new SettingScreen());
            }
        });
    }
}
