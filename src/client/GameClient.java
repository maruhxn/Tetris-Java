package client;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;

import static constant.Constants.CLIENT_HEIGHT;
import static constant.Constants.CLIENT_WIDTH;

public class GameClient extends JFrame {
//    private KeyListener clientKeyListener;

    public GameClient() {
        // Init KeyListener
//        this.clientKeyListener = new ClientKeyListener();

        // JFrame Setting
        super("TETRIS");
        setSize(CLIENT_WIDTH, CLIENT_HEIGHT);
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

//    private class ClientKeyListener implements KeyListener {
//        @Override
//        public void keyTyped(KeyEvent e) {
//
//        }
//
//        @Override
//        public void keyPressed(KeyEvent e) {
//            switch(e.getKeyCode()) {
//                case KeyEvent.VK_DOWN:
//                    break;
//                case KeyEvent.VK_RIGHT:
//                    break;
//                case KeyEvent.VK_LEFT:
//                    break;
//                case KeyEvent.VK_UP:
//                    break;
//            }
//        }
//
//        @Override
//        public void keyReleased(KeyEvent e) {
//
//        }
//    }

    public void switchPanel(JPanel newScreen) {
        setContentPane(newScreen);
        revalidate();
        requestFocus();
    }
}
