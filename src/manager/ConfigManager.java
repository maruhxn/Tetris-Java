package manager;

import java.io.*;
import java.util.Properties;

public class ConfigManager {
    private static final String CONFIG_FILE = "config.properties";
    private static Properties configProperties;

    public static void loadConfig() {
        configProperties = new Properties();
        try (InputStream inputStream = new FileInputStream(System.getProperty("user.dir") + "\\" + CONFIG_FILE)) {
            configProperties.load(inputStream);
            // SIZE Setting
            String gameSize = configProperties.getProperty("GAME_SIZE");
            switch (gameSize) {
                case "SMALL":
                    GameSizeManager.GAME_SIZE = GameSizeManager.GameSize.SMALL;
                    break;
                case "MEDIUM":
                    GameSizeManager.GAME_SIZE = GameSizeManager.GameSize.MEDIUM;
                    break;
                case "LARGE":
                    GameSizeManager.GAME_SIZE = GameSizeManager.GameSize.LARGE;
                    break;
                default:
                    GameSizeManager.GAME_SIZE = GameSizeManager.GameSize.MEDIUM;
                    break;
            }

            // Key Setting
            int rotateKey = Integer.parseInt(configProperties.getProperty("ROTATE_KEY"));
            int pauseKey = Integer.parseInt(configProperties.getProperty("PAUSE_KEY"));
            int moveLeftKey = Integer.parseInt(configProperties.getProperty("MOVE_LEFT_KEY"));
            int moveRightKey = Integer.parseInt(configProperties.getProperty("MOVE_RIGHT_KEY"));
            int moveDownKey = Integer.parseInt(configProperties.getProperty("MOVE_DOWN_KEY"));
            int superDropKey = Integer.parseInt(configProperties.getProperty("SUPER_DROP_KEY"));
            int gameOverKey = Integer.parseInt(configProperties.getProperty("GAME_OVER_KEY"));
            GameKeyManager.setRotateKey(rotateKey);
            GameKeyManager.setPauseKey(pauseKey);
            GameKeyManager.setMoveLeftKey(moveLeftKey);
            GameKeyManager.setMoveRightKey(moveRightKey);
            GameKeyManager.setMoveDownKey(moveDownKey);
            GameKeyManager.setSuperDropKey(superDropKey);
            GameKeyManager.setGameOverKey(gameOverKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveConfig() {
        try (OutputStream outputStream = new FileOutputStream(System.getProperty("user.dir") + "\\" + CONFIG_FILE)) {
            configProperties.store(outputStream, "Application Configuration");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setConfigProperty(String key, String value) {
        configProperties.setProperty(key, value);
    }

    public static String getConfigProperty(String key) {
        return configProperties.getProperty(key);
    }
}
