package component;

import client.GameClient;
import screen.ScoreboardScreen;

public class ScoreboardMenu extends AbstractMenu {
    public ScoreboardMenu(String text) {
        super(text);
    }

    @Override
    public void setActionListener() {
        super.addActionListener(e -> {
            GameClient client = (GameClient) extractClient();
            client.switchPanel(new ScoreboardScreen());
        });
    }
}
