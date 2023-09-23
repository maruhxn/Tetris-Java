package component;

import score.Score;
import score.ScoreDao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class Scoreboard extends JTable {
    String[] columnNames = {"순위", "이름", "점수"};
    ScoreDao scoreDao;
    DefaultTableModel tableModel;
    List<Score> scoreList;
    JScrollPane scrollPane;
    JViewport viewport;

    public Scoreboard() {
        tableModel = new DefaultTableModel(columnNames, 0);
        setForeground(Color.WHITE);
        setBackground(Color.BLACK);
        setShowGrid(true);
        setModel(tableModel);

        scoreDao = new ScoreDao();

        scrollPane = new JScrollPane(this);
        scrollPane.setBorder(null);

        viewport = scrollPane.getViewport();
        viewport.setForeground(Color.WHITE);
        viewport.setBackground(Color.BLACK);
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public void initTableData() {
        tableModel.setRowCount(0);
        scoreList = scoreDao.getTop10();
        for (int i = 0; i < scoreList.size(); ++i) {
            Score score = scoreList.get(i);
            Object[] record = new Object[3];
            record[0] = i + 1; // 순위
            record[1] = score.getName();
            record[2] = score.getScore();
            tableModel.addRow(record);
        }
    }

    public List<Score> getScoreList() {
        return scoreList;
    }

    public void setScoreList(List<Score> scoreList) {
        this.scoreList = scoreList;
    }
}

