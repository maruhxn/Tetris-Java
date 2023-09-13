import global.screen.MainScreen;
import view.GameClient;

public class Main {
    public static void main(String[] args) {
        GameClient mainClient = new GameClient();
        mainClient.setContentPane(MainScreen.getInstance());
        mainClient.setVisible(true);
    }
}