package screen;

import client.GameClient;
import component.Button;
import component.Label;
import component.*;

import javax.swing.*;
import java.awt.*;

import static util.Utility.addPadding;
import static util.Utility.getLargeFont;

public class ScoreboardScreen extends AbstractScreen {
    JLabel label;
    Scoreboard scoreboard;
    NavArea navArea;

    public ScoreboardScreen() {
        setLayout(new BorderLayout());
        // LABEL
        label = new Label("Score Board", SwingConstants.CENTER);
        label.setFont(getLargeFont());

        // SCOREBOARD
        scoreboard = new Scoreboard();
        scoreboard.initTableData();

        addPadding(label);
        addPadding(scoreboard);

        navArea = new NavArea();

        add(label, BorderLayout.NORTH);
        add(scoreboard.getScrollPane(), BorderLayout.CENTER);
        add(navArea, BorderLayout.SOUTH);
    }

    private static class NavArea extends AbstractArea {
        JButton homeBtn;

        public NavArea() {
            homeBtn = new Button("메인으로");

            homeBtn.addActionListener(e -> {
                GameClient client = (GameClient) getTopLevelAncestor();
                client.switchPanel(new MainScreen());
            });

            add(homeBtn);
        }

    }

}