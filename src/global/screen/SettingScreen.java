package global.screen;

import javax.swing.*;

public class SettingScreen extends Screen {
    private static SettingScreen instance;

    public SettingScreen() {
        JLabel test = new JLabel("Setting Screen", SwingConstants.CENTER);
        add(test);
    }

    public static SettingScreen getInstance() {
        if (instance == null) {
            synchronized (SettingScreen.class) {
                instance = new SettingScreen();
            }
        }

        return instance;
    }
}
