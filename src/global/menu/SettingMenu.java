package global.menu;

import global.screen.Screen;
import global.screen.SettingScreen;

import javax.swing.*;
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
                JFrame client = extractClient();

                Screen settingScreen = SettingScreen.getInstance();
                client.setContentPane(settingScreen);
                client.validate();
            }
        });
    }
}
