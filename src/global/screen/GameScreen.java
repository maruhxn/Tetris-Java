package global.screen;

import javax.swing.*;

public class GameScreen extends Screen {
    private static GameScreen instance;

    public GameScreen() {
        JLabel test = new JLabel("Game Screen", SwingConstants.CENTER);
        add(test);
    }

    public static GameScreen getInstance() {
        if (instance == null) {
            synchronized (GameScreen.class) {
                instance = new GameScreen();
            }
        }

        return instance;
    }
}
