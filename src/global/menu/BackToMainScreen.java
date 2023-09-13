package global.menu;

import global.screen.MainScreen;
import global.screen.Screen;

import javax.swing.*;
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
                JFrame client = extractClient();

                Screen mainScreen = MainScreen.getInstance();
                client.setContentPane(mainScreen);
                client.validate();
            }
        });
    }
}
