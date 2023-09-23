package component;

import client.GameClient;
import screen.SettingScreen;

public class SettingMenu extends AbstractMenu {
    public SettingMenu(String text) {
        super(text);
    }

    @Override
    public void setActionListener() {
        super.addActionListener(e -> {
            GameClient client = (GameClient) extractClient();
            client.switchPanel(new SettingScreen());
        });
    }
}
