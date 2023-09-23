package component;

import client.GameClient;
import screen.GameScreen;

public class StartMenu extends AbstractMenu {
    public StartMenu(String text) {
        super(text);
    }

    @Override
    public void setActionListener() {
        super.addActionListener(e -> {
            GameClient client = (GameClient) extractClient();
            client.switchPanel(new GameScreen());
        });
    }
}
