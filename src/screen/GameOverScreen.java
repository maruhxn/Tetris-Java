package screen;

import client.GameClient;
import component.Button;
import component.Label;
import component.*;
import manager.GameManager;
import score.Score;
import score.ScoreDao;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GameOverScreen extends AbstractScreen {
    JLabel label;
    Scoreboard scoreboard;
    JPanel cardPanel;
    CardLayout cardLayout;

    public GameOverScreen() {
        setLayout(new BorderLayout());
        // LABEL
        label = new JLabel("GAME OVER!!!", SwingConstants.CENTER);
        label.setFont(new Font("Courier", Font.BOLD, 25));
        label.setForeground(Color.white);

        // SCORE TABLE
        scoreboard = new Scoreboard();
        scoreboard.initTableData();

        cardPanel = new JPanel(new CardLayout());
        cardPanel.add(new InputArea(), "input");
        cardPanel.add(new NavArea(), "nav");

        // 기본으로 보여줄 패널 설정
        cardLayout = (CardLayout) cardPanel.getLayout();
        cardLayout.show(cardPanel, "input");

        addPadding(label);
        addPadding(scoreboard);

        add(label, BorderLayout.NORTH);
        add(scoreboard.getScrollPane(), BorderLayout.CENTER);
        add(cardPanel, BorderLayout.SOUTH);
    }

    private void addPadding(JComponent component) {
        Border border = component.getBorder();
        Border margin = new EmptyBorder(10, 10, 10, 10);
        component.setBorder(new CompoundBorder(border, margin));
    }

    private static class NavArea extends AbstractArea {
        JButton homeBtn;
        JButton exitBtn;

        public NavArea() {
            homeBtn = new Button("메인으로");
            exitBtn = new Button("게임 종료");

            homeBtn.addActionListener(e -> {
                GameClient client = (GameClient) getTopLevelAncestor();
                client.switchPanel(new MainScreen());
            });
            exitBtn.addActionListener(e -> System.exit(0));

            add(homeBtn);
            add(exitBtn);
        }

    }

    private class InputArea extends AbstractArea {
        JLabel nameLabel;
        JTextField nameInput;
        JButton okBtn;
        ActionListener actionListener;

        public InputArea() {
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

            this.actionListener = new ScoreBoardActionListener();

            nameLabel = new Label("NAME : ", SwingConstants.LEFT);
            nameLabel.setFont(new Font("Courier", Font.BOLD, 15));

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
        static class DocumentSizeFilter extends DocumentFilter {
            private final int maxCharacters;

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

                ScoreDao scoreDao = new ScoreDao();
                scoreDao.addResult(score);

                scoreboard.initTableData();

                List<Score> scoreList = scoreboard.getScoreList();
                for (int i = 0; i < scoreList.size(); ++i) {
                    Score s = scoreList.get(i);
                    if (s.getScore() == score.getScore() && s.getName().equals(score.getName())) {
                        highlightTableRow(scoreboard, i + 1);
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