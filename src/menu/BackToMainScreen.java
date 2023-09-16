package menu;

import screen.MainScreen;
import client.GameClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BackToMainScreen extends Menu {
    public BackToMainScreen() {
        super("메인으로");
    }

    @Override
    public void setActionListener() {
        super.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameClient client = (GameClient) extractClient();
                client.switchPanel(new MainScreen());
            }
        });
    }
}
