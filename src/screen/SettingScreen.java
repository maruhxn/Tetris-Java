package screen;

import client.GameClient;
import manager.GameKeyManager;
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

        GameSize selectedSizeSetting = GAME_SIZE;

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

            if (selectedSizeSetting == GameSize.SMALL) {
                smallBtn.setBackground(Color.YELLOW);
            } else if (selectedSizeSetting == GameSize.MEDIUM) {
                mediumBtn.setBackground(Color.YELLOW);
            } else if (selectedSizeSetting == GameSize.LARGE) {
                largeBtn.setBackground(Color.YELLOW);
            }
            btnArea.setBackground(Color.BLACK);
            btnArea.setForeground(Color.WHITE);

            smallBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedSizeSetting = GameSize.SMALL;
                    smallBtn.setBackground(Color.YELLOW);
                    mediumBtn.setBackground(Color.WHITE);
                    largeBtn.setBackground(Color.WHITE);
                }
            });

            mediumBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedSizeSetting = GameSize.MEDIUM;
                    smallBtn.setBackground(Color.WHITE);
                    mediumBtn.setBackground(Color.YELLOW);
                    largeBtn.setBackground(Color.WHITE);
                }
            });

            largeBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedSizeSetting = GameSize.LARGE;
                    smallBtn.setBackground(Color.WHITE);
                    mediumBtn.setBackground(Color.WHITE);
                    largeBtn.setBackground(Color.YELLOW);
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

                keyItemList.add(new KeyItem("블럭 회전", GameKeyManager.getRotateKey(), GameKeyManager.GameKeys.ROTATE_KEY));
                keyItemList.add(new KeyItem("게임 일시 정지", GameKeyManager.getPauseKey(), GameKeyManager.GameKeys.PAUSE_KEY));
                keyItemList.add(new KeyItem("블럭 좌측 이동", GameKeyManager.getMoveLeftKey(), GameKeyManager.GameKeys.MOVE_LEFT_KEY));
                keyItemList.add(new KeyItem("블럭 우측 이동", GameKeyManager.getMoveRightKey(), GameKeyManager.GameKeys.MOVE_RIGHT_KEY));
                keyItemList.add(new KeyItem("블럭 하강", GameKeyManager.getMoveDownKey(), GameKeyManager.GameKeys.MOVE_DOWN_KEY));
                keyItemList.add(new KeyItem("게임 종료", GameKeyManager.getGameOverKey(), GameKeyManager.GameKeys.GAME_OVER_KEY));
                keyItemList.add(new KeyItem("슈퍼 드롭", GameKeyManager.getSuperDropKey(), GameKeyManager.GameKeys.SUPER_DROP_KEY));

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
                private int keyCode;

                GameKeyManager.GameKeys matchingKey;

                public int getKeyCode() {
                    return keyCode;
                }

                public void setKeyCode(int keyCode) {
                    this.keyCode = keyCode;
                }

                public KeyItem(String labelText, int keyCode, GameKeyManager.GameKeys matchingKey) {
                    setLayout(new GridLayout(1, 2));
                    setBackground(Color.BLACK);
                    setForeground(Color.WHITE);
                    label = new Label(labelText);
                    field = new JTextField();

                    String keyString = extractKeyStringFromKeyCode(keyCode);
                    field.setText(keyString);
                    field.setEditable(false);
                    this.keyCode = keyCode;
                    this.matchingKey = matchingKey;
                    field.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyPressed(KeyEvent e) {
                            setKeyCode(e.getKeyCode());
                            String keyString = extractKeyStringFromKeyCode(e.getKeyCode());
                            field.setText(keyString);
                        }
                    });

                    add(label);
                    add(field);
                }

                private static String extractKeyStringFromKeyCode(int keyCode) {
                    String keyString;

                    switch (keyCode) {
                        case KeyEvent.VK_SHIFT:
                            keyString = "SHIFT";
                            break;
                        case KeyEvent.VK_ESCAPE:
                            keyString = "ESC";
                            break;
                        case KeyEvent.VK_UP:
                            keyString = "UP";
                            break;
                        case KeyEvent.VK_DOWN:
                            keyString = "DOWN";
                            break;
                        case KeyEvent.VK_LEFT:
                            keyString = "LEFT";
                            break;
                        case KeyEvent.VK_RIGHT:
                            keyString = "RIGHT";
                            break;
                        case KeyEvent.VK_SPACE:
                            keyString = "SPACE";
                            break;
                        default:
                            char keyChar = (char) keyCode;

                            // keyChar 값이 유효한 문자인지 확인
                            if (Character.isISOControl(keyChar)) {
                                // 유효하지 않은 문자일 경우, 빈 문자(공백)으로 대체
                                keyString = "DON\'t KNOW";
                            } else {
                                keyString = String.valueOf(keyChar);
                            }
                            break;
                    }
                    return keyString;
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
                        setGameSize(GameSize.MEDIUM);
                        sizeControlArea.smallBtn.setBackground(Color.WHITE);
                        sizeControlArea.mediumBtn.setBackground(Color.YELLOW);
                        sizeControlArea.largeBtn.setBackground(Color.WHITE);
                        sizeControlArea.resizeClient();

                        // 키는 모두 기본 키로 설정
                        List<KeyControlArea.KeySettingArea.KeyItem> keyItemList = keyControlArea.keySettingArea.getKeyItemList();
                        for (KeyControlArea.KeySettingArea.KeyItem keyItem : keyItemList) {
                            GameKeyManager.GameKeys matchingKey = keyItem.matchingKey;
                            if (matchingKey == GameKeyManager.GameKeys.ROTATE_KEY) {
                                int keyCode = KeyEvent.VK_SHIFT;
                                GameKeyManager.setRotateKey(keyCode);
                                keyItem.field.setText(KeyControlArea.KeySettingArea.KeyItem.extractKeyStringFromKeyCode(keyCode));
                            } else if (matchingKey == GameKeyManager.GameKeys.GAME_OVER_KEY) {
                                int keyCode = KeyEvent.VK_ESCAPE;
                                GameKeyManager.setRotateKey(keyCode);
                                keyItem.field.setText(KeyControlArea.KeySettingArea.KeyItem.extractKeyStringFromKeyCode(keyCode));
                            } else if (matchingKey == GameKeyManager.GameKeys.PAUSE_KEY) {
                                int keyCode = KeyEvent.VK_P;
                                GameKeyManager.setRotateKey(keyCode);
                                keyItem.field.setText(KeyControlArea.KeySettingArea.KeyItem.extractKeyStringFromKeyCode(keyCode));
                            } else if (matchingKey == GameKeyManager.GameKeys.SUPER_DROP_KEY) {
                                int keyCode = KeyEvent.VK_SPACE;
                                GameKeyManager.setRotateKey(keyCode);
                                keyItem.field.setText(KeyControlArea.KeySettingArea.KeyItem.extractKeyStringFromKeyCode(keyCode));
                            } else if (matchingKey == GameKeyManager.GameKeys.MOVE_DOWN_KEY) {
                                int keyCode = KeyEvent.VK_DOWN;
                                GameKeyManager.setRotateKey(keyCode);
                                keyItem.field.setText(KeyControlArea.KeySettingArea.KeyItem.extractKeyStringFromKeyCode(keyCode));
                            } else if (matchingKey == GameKeyManager.GameKeys.MOVE_LEFT_KEY) {
                                int keyCode = KeyEvent.VK_LEFT;
                                GameKeyManager.setRotateKey(keyCode);
                                keyItem.field.setText(KeyControlArea.KeySettingArea.KeyItem.extractKeyStringFromKeyCode(keyCode));
                            } else if (matchingKey == GameKeyManager.GameKeys.MOVE_RIGHT_KEY) {
                                int keyCode = KeyEvent.VK_RIGHT;
                                GameKeyManager.setRotateKey(keyCode);
                                keyItem.field.setText(KeyControlArea.KeySettingArea.KeyItem.extractKeyStringFromKeyCode(keyCode));
                            }
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
                    // 사이즈 설정 반영
                    setGameSize(sizeControlArea.selectedSizeSetting);
                    sizeControlArea.resizeClient();

                    // 키 설정 반영
                    List<KeyControlArea.KeySettingArea.KeyItem> keyItemList = keyControlArea.keySettingArea.getKeyItemList();
                    for (KeyControlArea.KeySettingArea.KeyItem keyItem : keyItemList) {
                        GameKeyManager.GameKeys matchingKey = keyItem.matchingKey;
                        int keyCode = keyItem.getKeyCode();
                        if (matchingKey == GameKeyManager.GameKeys.ROTATE_KEY) GameKeyManager.setRotateKey(keyCode);
                        else if (matchingKey == GameKeyManager.GameKeys.GAME_OVER_KEY)
                            GameKeyManager.setGameOverKey(keyCode);
                        else if (matchingKey == GameKeyManager.GameKeys.PAUSE_KEY) GameKeyManager.setPauseKey(keyCode);
                        else if (matchingKey == GameKeyManager.GameKeys.SUPER_DROP_KEY)
                            GameKeyManager.setSuperDropKey(keyCode);
                        else if (matchingKey == GameKeyManager.GameKeys.MOVE_DOWN_KEY)
                            GameKeyManager.setMoveDownKey(keyCode);
                        else if (matchingKey == GameKeyManager.GameKeys.MOVE_LEFT_KEY)
                            GameKeyManager.setMoveLeftKey(keyCode);
                        else if (matchingKey == GameKeyManager.GameKeys.MOVE_RIGHT_KEY)
                            GameKeyManager.setMoveRightKey(keyCode);
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
