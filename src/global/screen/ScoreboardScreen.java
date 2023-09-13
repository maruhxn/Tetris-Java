package global.screen;

import javax.swing.*;

public class ScoreboardScreen extends Screen {
    private static ScoreboardScreen instance;

    public ScoreboardScreen() {
        JLabel test = new JLabel("Scoreboard Screen", SwingConstants.CENTER);
        add(test);
    }

    public static ScoreboardScreen getInstance() {
        if (instance == null) {
            synchronized (ScoreboardScreen.class) {
                instance = new ScoreboardScreen();
            }
        }

        return instance;
    }
}
