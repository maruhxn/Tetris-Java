package client;

import javax.swing.*;
import java.awt.*;

import static manager.GameSizeManager.*;


public class GameClient extends JFrame {
    public GameClient() {
        // JFrame Setting
        super("TETRIS");
        setSize(GAME_SIZE.getWidth() + WINDOW_BORDER, GAME_SIZE.getHeight() + WINDOW_MANAGER_HEIGHT);
        setBackground(Color.BLACK);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public void switchPanel(JPanel newScreen) {
        setContentPane(newScreen);
        revalidate();
        repaint();
        requestFocus();
    }
}
