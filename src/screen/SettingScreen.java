package screen;

import client.GameClient;
import manager.GameKeyManager;
import manager.GameKeys;
import manager.GameSizeManager;
import menu.Button;
import menu.Label;
import score.ScoreDao;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import static manager.GameSizeManager.*;

public class SettingScreen extends Screen {
    SizeControlArea sizeControlArea;
    KeyControlArea keyControlArea;
    ResetArea resetArea;
    NavArea navigateArea;

    @Override
    public Insets getInsets() {
        return new Insets(20, 20, 20, 20);
    }

    public SettingScreen() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        sizeControlArea = new SizeControlArea();
        keyControlArea = new KeyControlArea();
        resetArea = new ResetArea();
        navigateArea = new NavArea();

        add(sizeControlArea);
        add(keyControlArea);
        add(resetArea);
        add(navigateArea);
    }

    private abstract class Area extends JPanel {
        public Area() {
            setBackground(Color.BLACK);
            setForeground(Color.WHITE);
        }

    }

    private class SizeControlArea extends Area {
        JLabel sizeControlLabel;
        JPanel btnArea;
        JButton smallBtn, mediumBtn, largeBtn;

        public SizeControlArea() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            sizeControlLabel = new Label("화면 크기 조절");
            sizeControlLabel.setFont(new Font("Courier", Font.BOLD, 18));
            sizeControlLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            btnArea = new JPanel();
            btnArea.setLayout(new GridLayout(1, 3));
            smallBtn = new Button("SMALL");
            mediumBtn = new Button("MEDIUM");
            largeBtn = new Button("LARGE");

            if (GAME_SIZE == GameSize.SMALL) {
                smallBtn.setBackground(Color.YELLOW);
            } else if (GAME_SIZE == GameSize.MEDIUM) {
                mediumBtn.setBackground(Color.YELLOW);
            } else if (GAME_SIZE == GameSize.LARGE) {
                largeBtn.setBackground(Color.YELLOW);
            }
            btnArea.setBackground(Color.BLACK);
            btnArea.setForeground(Color.WHITE);

            smallBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GameSizeManager.setClientSmall();
                    smallBtn.setBackground(Color.YELLOW);
                    mediumBtn.setBackground(Color.WHITE);
                    largeBtn.setBackground(Color.WHITE);
                    resizeClient();
                }
            });

            mediumBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GameSizeManager.setClientMedium();
                    smallBtn.setBackground(Color.WHITE);
                    mediumBtn.setBackground(Color.YELLOW);
                    largeBtn.setBackground(Color.WHITE);
                    resizeClient();
                }
            });

            largeBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GameSizeManager.setClientLarge();
                    smallBtn.setBackground(Color.WHITE);
                    mediumBtn.setBackground(Color.WHITE);
                    largeBtn.setBackground(Color.YELLOW);
                    resizeClient();
                }
            });

            btnArea.add(smallBtn);
            btnArea.add(mediumBtn);
            btnArea.add(largeBtn);

            add(sizeControlLabel);
            add(Box.createRigidArea(new Dimension(0, 10)));
            add(btnArea);
        }

        private void resizeClient() {
            GameClient client = (GameClient) getTopLevelAncestor();
            client.setSize(GAME_SIZE.getWidth() + WINDOW_BORDER, GAME_SIZE.getHeight() + WINDOW_MANAGER_HEIGHT);
            client.revalidate();
        }
    }

    private class KeyControlArea extends Area {
        JLabel keyControlLabel;
        KeySettingArea keySettingArea;

        public KeyControlArea() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            keyControlLabel = new Label("키 설정");
            keyControlLabel.setFont(new Font("Courier", Font.BOLD, 18));

            keySettingArea = new KeySettingArea();

            keyControlLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            add(keyControlLabel);
            add(Box.createRigidArea(new Dimension(0, 10)));
            add(keySettingArea);
        }

        private class KeySettingArea extends JPanel {
            KeyItem rotateKey, pauseKey, leftKey, rightKey, exitKey, dropKey, superDropKey;
            List<KeyItem> keyItemList = new ArrayList<>();

            public List<KeyItem> getKeyItemList() {
                return keyItemList;
            }

            public KeySettingArea() {
                LineBorder border = (LineBorder) BorderFactory.createLineBorder(Color.WHITE, 3);
                setBorder(border);
                setBackground(Color.BLACK);
                setForeground(Color.WHITE);

                setLayout(new GridLayout(0, 2));

                keyItemList.add(new KeyItem("블럭 회전", GameKeyManager.getRotateKey(), GameKeys.ROTATE_KEY));
                keyItemList.add(new KeyItem("게임 일시 정지", GameKeyManager.getPauseKey(), GameKeys.PAUSE_KEY));
                keyItemList.add(new KeyItem("블럭 좌측 이동", GameKeyManager.getMoveLeftKey(), GameKeys.MOVE_LEFT_KEY));
                keyItemList.add(new KeyItem("블럭 우측 이동", GameKeyManager.getMoveRightKey(), GameKeys.MOVE_RIGHT_KEY));
                keyItemList.add(new KeyItem("블럭 하강", GameKeyManager.getMoveDownKey(), GameKeys.MOVE_DOWN_KEY));
                keyItemList.add(new KeyItem("게임 종료", GameKeyManager.getGameOverKey(), GameKeys.GAME_OVER_KEY));
                keyItemList.add(new KeyItem("슈퍼 드롭", GameKeyManager.getSuperDropKey(), GameKeys.SUPER_DROP_KEY));

                for (KeyItem k : keyItemList) {
                    add(k);
                }
            }

            @Override
            public Insets getInsets() {
                return new Insets(10, 10, 10, 10);
            }

            public class KeyItem extends JPanel {
                JLabel label;
                JTextField field;
                int keyCode;

                GameKeys matchingKey;

                public int getKeyCode() {
                    return keyCode;
                }

                public void setKeyCode(int keyCode) {
                    this.keyCode = keyCode;
                }

                public KeyItem(String labelText, int keyCode, GameKeys matchingKey) {
                    setLayout(new GridLayout(1, 2));
                    setBackground(Color.BLACK);
                    setForeground(Color.WHITE);
                    label = new Label(labelText);
                    field = new JTextField();
                    field.setEditable(false);
                    this.keyCode = keyCode;
                    this.matchingKey = matchingKey;
                    field.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyPressed(KeyEvent e) {
                            setKeyCode(e.getKeyCode());
                            field.setText(String.valueOf((char) keyCode));
                        }
                    });

                    add(label);
                    add(field);
                }
            }

        }

    }

    private class ResetArea extends Area {
        JButton scoreBoardResetBtn, settingResetBtn;

        public ResetArea() {
            setLayout(new GridLayout(2, 1));
            scoreBoardResetBtn = new Button("스코어보드 초기화");
            scoreBoardResetBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int result = JOptionPane.showConfirmDialog(resetArea, "점수를 초기화 하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);

                    if (result == JOptionPane.YES_OPTION) {
                        ScoreDao scoreDao = new ScoreDao();
                        scoreDao.deleteAllScores();
                        JOptionPane.showMessageDialog(resetArea, "점수 초기화 완료");
                    }

                }
            });
            settingResetBtn = new Button("설정 초기화");
            settingResetBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int result = JOptionPane.showConfirmDialog(resetArea, "설정을 초기화 하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);

                    if (result == JOptionPane.YES_OPTION) {
                        // 사이즈 small로 설정
                        GameSizeManager.setClientMedium();
                        sizeControlArea.smallBtn.setBackground(Color.WHITE);
                        sizeControlArea.mediumBtn.setBackground(Color.YELLOW);
                        sizeControlArea.largeBtn.setBackground(Color.WHITE);
                        sizeControlArea.resizeClient();

                        // 키는 모두 기본 키로 설정
                        List<KeyControlArea.KeySettingArea.KeyItem> keyItemList = keyControlArea.keySettingArea.getKeyItemList();
                        for (KeyControlArea.KeySettingArea.KeyItem keyItem : keyItemList) {
                            GameKeys matchingKey = keyItem.matchingKey;
                            if (matchingKey == GameKeys.ROTATE_KEY) GameKeyManager.setRotateKey(KeyEvent.VK_SHIFT);
                            else if (matchingKey == GameKeys.GAME_OVER_KEY)
                                GameKeyManager.setGameOverKey(KeyEvent.VK_ESCAPE);
                            else if (matchingKey == GameKeys.PAUSE_KEY) GameKeyManager.setPauseKey(KeyEvent.VK_P);
                            else if (matchingKey == GameKeys.SUPER_DROP_KEY)
                                GameKeyManager.setSuperDropKey(KeyEvent.VK_SPACE);
                            else if (matchingKey == GameKeys.MOVE_DOWN_KEY)
                                GameKeyManager.setMoveDownKey(KeyEvent.VK_DOWN);
                            else if (matchingKey == GameKeys.MOVE_LEFT_KEY)
                                GameKeyManager.setMoveLeftKey(KeyEvent.VK_LEFT);
                            else if (matchingKey == GameKeys.MOVE_RIGHT_KEY)
                                GameKeyManager.setMoveRightKey(KeyEvent.VK_RIGHT);
                        }
                        JOptionPane.showMessageDialog(resetArea, "설정 초기화 완료");
                    }

                }
            });

            add(scoreBoardResetBtn);
            add(settingResetBtn);
        }
    }

    private class NavArea extends Area {
        JButton mainBtn, saveBtn;

        public NavArea() {
            mainBtn = new Button("메인으로");
            saveBtn = new Button("설정 저장");

            saveBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    List<KeyControlArea.KeySettingArea.KeyItem> keyItemList = keyControlArea.keySettingArea.getKeyItemList();
                    for (KeyControlArea.KeySettingArea.KeyItem keyItem : keyItemList) {
                        GameKeys matchingKey = keyItem.matchingKey;
                        int keyCode = keyItem.keyCode;
                        if (matchingKey == GameKeys.ROTATE_KEY) GameKeyManager.setRotateKey(keyCode);
                        else if (matchingKey == GameKeys.GAME_OVER_KEY) GameKeyManager.setGameOverKey(keyCode);
                        else if (matchingKey == GameKeys.PAUSE_KEY) GameKeyManager.setPauseKey(keyCode);
                        else if (matchingKey == GameKeys.SUPER_DROP_KEY) GameKeyManager.setSuperDropKey(keyCode);
                        else if (matchingKey == GameKeys.MOVE_DOWN_KEY) GameKeyManager.setMoveDownKey(keyCode);
                        else if (matchingKey == GameKeys.MOVE_LEFT_KEY) GameKeyManager.setMoveLeftKey(keyCode);
                        else if (matchingKey == GameKeys.MOVE_RIGHT_KEY) GameKeyManager.setMoveRightKey(keyCode);
                    }
                }
            });

            mainBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GameClient client = (GameClient) getTopLevelAncestor();
                    client.switchPanel(new MainScreen());
                }
            });

            add(mainBtn);
            add(saveBtn);
        }
    }
}
