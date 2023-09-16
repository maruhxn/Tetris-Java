import screen.MainScreen;
import client.GameClient;

public class Main {
    public static void main(String[] args) {
        GameClient mainClient = new GameClient();
        mainClient.setContentPane(new MainScreen());
        mainClient.setVisible(true);
    }
}