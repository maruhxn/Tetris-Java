import client.GameClient;
import manager.ConfigManager;
import score.ScoreDao;

public class Main {
    public static void main(String[] args) {
        ConfigManager.loadConfig();
        ScoreDao scoreDao = new ScoreDao();
        scoreDao.createTable();

        new GameClient();
    }
}