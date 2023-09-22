package screen;

import client.GameClient;
import manager.GameManager;
import score.Score;
import score.ScoreDao;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GameOverScreen extends Screen {
    JLabel label;
    JTable scoreTable;
    JPanel cardPanel;
    CardLayout cardLayout;
    DefaultTableModel tableModel;
    ScoreDao scoreDao;
    List<Score> scoreList;

    public GameOverScreen() {
        setLayout(new BorderLayout());
        // LABEL
        label = new JLabel("GAME OVER!!!", SwingConstants.CENTER);
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

        cardPanel = new JPanel(new CardLayout());
        // CardLayout에 패널 추가
        cardPanel.add(new InputArea(), "input");
        cardPanel.add(new NavArea(), "nav");

        // 기본으로 보여줄 패널 설정
        cardLayout = (CardLayout) cardPanel.getLayout();
        cardLayout.show(cardPanel, "input");

        add(label, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(cardPanel, BorderLayout.SOUTH);
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

    private class InputArea extends JPanel {
        JLabel nameLabel;
        JTextField nameInput;
        JButton okBtn;
        ActionListener actionListener;

        public InputArea() {
            setBackground(Color.BLACK);
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            this.actionListener = new ScoreBoardActionListener();

            nameLabel = new JLabel("NAME : ", SwingConstants.LEFT);
            nameLabel.setFont(new Font("Courier", Font.BOLD, 15));
            nameLabel.setForeground(Color.white);

            nameInput = new JTextField(20);
            nameInput.setFont(new Font("Courier", Font.PLAIN, 15));
            ((AbstractDocument) nameInput.getDocument()).setDocumentFilter(new DocumentSizeFilter(20)); // 입력 길이 제한
            nameInput.setForeground(Color.YELLOW);
            nameInput.setBackground(Color.BLACK);

            okBtn = new JButton("입력");
            okBtn.setForeground(Color.white);
            okBtn.setBackground(Color.BLACK);

            add(nameLabel);
            add(nameInput);
            add(okBtn);

            okBtn.addActionListener(actionListener);
            nameInput.addActionListener(actionListener);
        }

        // 입력 길이를 제한하는 DocumentFilter
        class DocumentSizeFilter extends DocumentFilter {
            private int maxCharacters;

            public DocumentSizeFilter(int maxChars) {
                maxCharacters = maxChars;
            }

            @Override
            public void insertString(FilterBypass fb, int offs, String str, AttributeSet a) throws BadLocationException {
                if ((fb.getDocument().getLength() + str.length()) <= maxCharacters) {
                    super.insertString(fb, offs, str, a);
                } else {
                    Toolkit.getDefaultToolkit().beep();
                }
            }

            @Override
            public void replace(FilterBypass fb, int offs, int length, String str, AttributeSet a) throws BadLocationException {
                if ((fb.getDocument().getLength() + str.length() - length) <= maxCharacters) {
                    super.replace(fb, offs, length, str, a);
                } else {
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        }

        private class ScoreBoardActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = nameInput.getText();
                Score score = new Score();
                score.setName(inputText);
                score.setScore(GameManager.getScore());
                scoreDao.addResult(score);

                initTableData();

                for (int i = 0; i < scoreList.size(); ++i) {
                    Score s = scoreList.get(i);
                    if (s.getScore() == score.getScore() && s.getName() == score.getName()) {
                        highlightTableRow(scoreTable, i + 1);
                    }
                }

                cardLayout.show(cardPanel, "nav");
                GameManager.resetScore();
            }

            private void highlightTableRow(JTable scoreTable, Integer rank) {
                scoreTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                        if (row + 1 == rank) {
                            c.setBackground(Color.YELLOW); // 원하는 색상으로 변경
                        } else {
                            c.setBackground(table.getBackground());
                        }

                        return c;
                    }
                });

                scoreTable.repaint();
            }
        }

    }


}