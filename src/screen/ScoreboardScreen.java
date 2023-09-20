package screen;

import client.GameClient;
import score.Score;
import score.ScoreDao;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ScoreboardScreen extends Screen {
    JLabel label;
    JTable scoreTable;
    NavArea navArea;
    DefaultTableModel tableModel;
    ScoreDao scoreDao;
    List<Score> scoreList;

    public ScoreboardScreen() {
        setLayout(new BorderLayout());
        // LABEL
        label = new JLabel("Score Board", SwingConstants.CENTER);
        label.setFont(new Font("Courier", Font.BOLD, 25));
        label.setForeground(Color.white);
        Border border = label.getBorder();
        Border margin = new EmptyBorder(10, 10, 10, 10);
        label.setBorder(new CompoundBorder(border, margin));

        // SCORE TABLE
        String[] columnNames = {"순위", "이름", "점수"};
        scoreDao = new ScoreDao();
        tableModel = new DefaultTableModel(columnNames, 0);
        scoreTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(scoreTable);
        JViewport viewport = scrollPane.getViewport();
        scoreTable.setShowGrid(true);
        viewport.setForeground(Color.WHITE);
        viewport.setBackground(Color.BLACK);
        scrollPane.setBorder(null);
        scoreTable.setForeground(Color.WHITE);
        scoreTable.setBackground(Color.BLACK);
        Border tableBorder = scoreTable.getBorder();
        Border tableMargin = new EmptyBorder(10, 10, 10, 10);
        scoreTable.setBorder(new CompoundBorder(tableBorder, tableMargin));

        initTableData();

        navArea = new NavArea();

        add(label, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(navArea, BorderLayout.SOUTH);
    }

    private void initTableData() {
        tableModel.setRowCount(0);
        scoreList = scoreDao.getTop10();
        for (int i = 0; i < scoreList.size(); ++i) {
            Score score = scoreList.get(i);
            Object record[] = new Object[3];
            record[0] = i + 1; // 순위
            record[1] = score.getName();
            record[2] = score.getScore();
            tableModel.addRow(record);
        }
    }

    private class NavArea extends JPanel {
        JButton homeBtn;
        JButton exitBtn;

        public NavArea() {
            setBackground(Color.BLACK);

            homeBtn = new JButton("메인으로");
            exitBtn = new JButton("게임 종료");

            homeBtn.setBackground(Color.WHITE);
            exitBtn.setBackground(Color.WHITE);
            homeBtn.setForeground(Color.BLACK);
            exitBtn.setForeground(Color.BLACK);

            homeBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GameClient client = (GameClient) getTopLevelAncestor();
                    client.switchPanel(new MainScreen());
                }
            });

            exitBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });

            add(homeBtn);
            add(exitBtn);
        }

    }

}