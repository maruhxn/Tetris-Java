package manager;

import java.awt.event.KeyEvent;

public class GameKeyManager {
    public enum GameKeys {
        ROTATE_KEY, PAUSE_KEY, MOVE_LEFT_KEY, MOVE_RIGHT_KEY, MOVE_DOWN_KEY, SUPER_DROP_KEY, GAME_OVER_KEY
    }

    private static int ROTATE_KEY = KeyEvent.VK_SHIFT;
    private static int PAUSE_KEY = KeyEvent.VK_P;
    private static int MOVE_LEFT_KEY = KeyEvent.VK_LEFT;
    private static int MOVE_RIGHT_KEY = KeyEvent.VK_RIGHT;
    private static int MOVE_DOWN_KEY = KeyEvent.VK_DOWN;
    private static int SUPER_DROP_KEY = KeyEvent.VK_SPACE;
    private static int GAME_OVER_KEY = KeyEvent.VK_ESCAPE;

    public static int getRotateKey() {
        return ROTATE_KEY;
    }

    public static void setRotateKey(int rotateKey) {
        ROTATE_KEY = rotateKey;
    }

    public static int getPauseKey() {
        return PAUSE_KEY;
    }

    public static void setPauseKey(int pauseKey) {
        PAUSE_KEY = pauseKey;
    }

    public static int getMoveLeftKey() {
        return MOVE_LEFT_KEY;
    }

    public static void setMoveLeftKey(int moveLeftKey) {
        MOVE_LEFT_KEY = moveLeftKey;
    }

    public static int getMoveRightKey() {
        return MOVE_RIGHT_KEY;
    }

    public static void setMoveRightKey(int moveRightKey) {
        MOVE_RIGHT_KEY = moveRightKey;
    }

    public static int getMoveDownKey() {
        return MOVE_DOWN_KEY;
    }

    public static void setMoveDownKey(int moveDownKey) {
        MOVE_DOWN_KEY = moveDownKey;
    }

    public static int getSuperDropKey() {
        return SUPER_DROP_KEY;
    }

    public static void setSuperDropKey(int superDropKey) {
        SUPER_DROP_KEY = superDropKey;
    }

    public static int getGameOverKey() {
        return GAME_OVER_KEY;
    }

    public static void setGameOverKey(int gameOverKey) {
        GAME_OVER_KEY = gameOverKey;
    }
}
