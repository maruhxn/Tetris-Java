package screen;

import client.GameClient;
import component.Button;
import component.Label;
import component.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ScoreboardScreen extends AbstractScreen {
    JLabel label;
    Scoreboard scoreboard;
    NavArea navArea;

    public ScoreboardScreen() {
        setLayout(new BorderLayout());
        // LABEL
        label = new Label("Score Board", SwingConstants.CENTER);
        label.setFont(new Font("Courier", Font.BOLD, 25));

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

    // TODO: Move to Util class
    private void addPadding(JComponent component) {
        Border border = component.getBorder();
        Border margin = new EmptyBorder(10, 10, 10, 10);
        component.setBorder(new CompoundBorder(border, margin));
    }

    private static class NavArea extends AbstractArea {
        JButton homeBtn;

        public NavArea() {
            homeBtn = new Button("메인으로");

            homeBtn.addActionListener(e -> {
                // TODO: Move to Util Class
                GameClient client = (GameClient) getTopLevelAncestor();
                client.switchPanel(new MainScreen());
            });

            add(homeBtn);
        }

    }

}