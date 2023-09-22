import client.GameClient;
import manager.ConfigManager;
import score.ScoreDao;
import screen.MainScreen;

public class Main {
    public static void main(String[] args) {
        ConfigManager.loadConfig();
        ScoreDao scoreDao = new ScoreDao();
        scoreDao.createTable();
        GameClient mainClient = new GameClient();
        mainClient.setContentPane(new MainScreen());
        mainClient.setVisible(true);
    }
}