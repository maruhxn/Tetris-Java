import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.util.HashMap;
import java.util.Map;

public class KeyAdapterManager {
    Map<JPanel, KeyAdapter> keyAdapterMap = new HashMap<>();
    private JPanel currentScreen;

    KeyAdapterManager(JPanel initiaScreen) {
        this.currentScreen = initiaScreen;
    }

    public void addScreen(JPanel panel, KeyAdapter keyAdapter) {
        keyAdapterMap.put(panel, keyAdapter);
    }

    public void switchScreen(JPanel newPanel) {
        currentScreen.removeKeyListener(keyAdapterMap.get(currentScreen)); // Remove current panel's KeyAdapter
        currentScreen = newPanel;
        newPanel.addKeyListener(keyAdapterMap.get(currentScreen)); // Add new panel's KeyAdapter
    }

    public void removeScreen(JPanel panel) {
        keyAdapterMap.remove(panel);
    }
}
