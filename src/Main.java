import score.ScoreDao;
import screen.MainScreen;
import client.GameClient;

public class Main {
    public static void main(String[] args) {
        ScoreDao scoreDao = new ScoreDao();
        scoreDao.createTable();
        GameClient mainClient = new GameClient();
        mainClient.setContentPane(new MainScreen());
        mainClient.setVisible(true);
    }
}