package client;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;

import static constant.Constants.*;

public class GameClient extends JFrame {
    public GameClient() {

        // JFrame Setting
        super("TETRIS");
        setSize(CLIENT_WIDTH + WINDOW_BORDER, CLIENT_HEIGHT + WINDOW_MANAGER_HEIGHT);
        setBackground(Color.BLACK);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        //Document default style.
        SimpleAttributeSet styleSet = new SimpleAttributeSet();
        StyleConstants.setFontSize(styleSet, 18);
        StyleConstants.setFontFamily(styleSet, "Courier");
        StyleConstants.setBold(styleSet, true);
        StyleConstants.setForeground(styleSet, Color.WHITE);
        StyleConstants.setAlignment(styleSet, StyleConstants.ALIGN_CENTER);
    }

    public void switchPanel(JPanel newScreen) {
        setContentPane(newScreen);
        revalidate();
        repaint();
        requestFocus();
    }
}
