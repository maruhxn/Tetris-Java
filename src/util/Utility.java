package util;

import manager.ConfigManager;
import manager.GameKeyManager;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;

public final class Utility {
    public static void addPadding(JComponent component) {
        Border border = component.getBorder();
        Border margin = new EmptyBorder(10, 10, 10, 10);
        component.setBorder(new CompoundBorder(border, margin));
    }

    public static Font getLargeFont() {
        return new Font("Courier", Font.BOLD, 25);
    }

    public static Font getMediumFont() {
        return new Font("Courier", Font.BOLD, 18);
    }

    public static Font getSmallFont() {
        return new Font("Courier", Font.BOLD, 12);
    }

    public static String extractKeyStringFromKeyCode(int keyCode) {
        String keyString;

        switch (keyCode) {
            case KeyEvent.VK_SHIFT:
                keyString = "SHIFT";
                break;
            case KeyEvent.VK_ESCAPE:
                keyString = "ESC";
                break;
            case KeyEvent.VK_UP:
                keyString = "UP";
                break;
            case KeyEvent.VK_DOWN:
                keyString = "DOWN";
                break;
            case KeyEvent.VK_LEFT:
                keyString = "LEFT";
                break;
            case KeyEvent.VK_RIGHT:
                keyString = "RIGHT";
                break;
            case KeyEvent.VK_SPACE:
                keyString = "SPACE";
                break;
            default:
                char keyChar = (char) keyCode;

                // keyChar 값이 유효한 문자인지 확인
                if (Character.isISOControl(keyChar)) {
                    // 유효하지 않은 문자일 경우, 빈 문자(공백)으로 대체
                    keyString = "DON't KNOW";
                } else {
                    keyString = String.valueOf(keyChar);
                }
                break;
        }
        return keyString;
    }

    public static void setKey(GameKeyManager.GameKeys matchingKey, int keyCode) {
        if (matchingKey == GameKeyManager.GameKeys.ROTATE_KEY) {
            GameKeyManager.setRotateKey(keyCode);
            ConfigManager.setConfigProperty("ROTATE_KEY", String.valueOf(keyCode));
        } else if (matchingKey == GameKeyManager.GameKeys.GAME_OVER_KEY) {
            GameKeyManager.setGameOverKey(keyCode);
            ConfigManager.setConfigProperty("GAME_OVER_KEY", String.valueOf(keyCode));
        } else if (matchingKey == GameKeyManager.GameKeys.PAUSE_KEY) {
            GameKeyManager.setPauseKey(keyCode);
            ConfigManager.setConfigProperty("PAUSE_KEY", String.valueOf(keyCode));
        } else if (matchingKey == GameKeyManager.GameKeys.SUPER_DROP_KEY) {
            GameKeyManager.setSuperDropKey(keyCode);
            ConfigManager.setConfigProperty("SUPER_DROP_KEY", String.valueOf(keyCode));
        } else if (matchingKey == GameKeyManager.GameKeys.MOVE_DOWN_KEY) {
            GameKeyManager.setMoveDownKey(keyCode);
            ConfigManager.setConfigProperty("MOVE_DOWN_KEY", String.valueOf(keyCode));
        } else if (matchingKey == GameKeyManager.GameKeys.MOVE_LEFT_KEY) {
            GameKeyManager.setMoveLeftKey(keyCode);
            ConfigManager.setConfigProperty("MOVE_LEFT_KEY", String.valueOf(keyCode));
        } else if (matchingKey == GameKeyManager.GameKeys.MOVE_RIGHT_KEY) {
            GameKeyManager.setMoveRightKey(keyCode);
            ConfigManager.setConfigProperty("MOVE_RIGHT_KEY", String.valueOf(keyCode));
        }
    }
}
