package screen;

import menu.Button;
import menu.Label;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class SettingScreen extends Screen {
    JPanel sizeControlArea, keyControlArea, resetArea, navigateArea;

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

    private abstract static class Area extends JPanel {
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
            setLayout(new GridLayout(2, 1));
            sizeControlLabel = new Label("화면 크기 조절");
            sizeControlLabel.setFont(new Font("Courier", Font.BOLD, 18));

            btnArea = new JPanel();
            smallBtn = new Button("SMALL");
            mediumBtn = new Button("MEDIUM");
            largeBtn = new Button("LARGE");

            btnArea.setBackground(Color.BLACK);
            btnArea.setForeground(Color.WHITE);

            btnArea.add(smallBtn);
            btnArea.add(mediumBtn);
            btnArea.add(largeBtn);

            add(sizeControlLabel);
            add(btnArea);
        }
    }

    private class KeyControlArea extends Area {
        JLabel keyControlLabel;
        KeySettingArea keySettingArea;

        public KeyControlArea() {
            setLayout(new GridLayout(2, 1));
            keyControlLabel = new Label("키 설정");
            keyControlLabel.setFont(new Font("Courier", Font.BOLD, 18));

            keySettingArea = new KeySettingArea();

            add(keyControlLabel);
            add(keySettingArea);
        }

        private static class KeySettingArea extends JPanel {
            KeyItem rotateKey, pauseKey, leftKey, rightKey, exitKey, dropKey, superDropKey;

            public KeySettingArea() {
                LineBorder border = (LineBorder) BorderFactory.createLineBorder(Color.WHITE, 3);
                setBorder(border);
                setBackground(Color.BLACK);
                setForeground(Color.WHITE);

                setLayout(new GridLayout(0, 2));
                rotateKey = new KeyItem("블럭 회전");
                pauseKey = new KeyItem("게임 일시 정지");
                leftKey = new KeyItem("블럭 좌측 이동");
                rightKey = new KeyItem("블럭 우측 이동");
                dropKey = new KeyItem("블럭 하강");
                exitKey = new KeyItem("게임 종료");
                superDropKey = new KeyItem("슈퍼 드롭");

                add(rotateKey);
                add(pauseKey);
                add(leftKey);
                add(rightKey);
                add(dropKey);
                add(exitKey);
                add(superDropKey);
            }

            @Override
            public Insets getInsets() {
                return new Insets(10, 10, 10, 10);
            }

            public static class KeyItem extends JPanel {
                JLabel label;
                JTextField field;

                public KeyItem(String labelText) {
                    setBackground(Color.BLACK);
                    setForeground(Color.WHITE);
                    label = new Label(labelText);
                    field = new JTextField();

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
            settingResetBtn = new Button("설정 초기화");

            add(scoreBoardResetBtn);
            add(settingResetBtn);
        }
    }

    private class NavArea extends Area {
        JButton mainBtn, saveBtn;

        public NavArea() {
            mainBtn = new Button("메인으로");
            saveBtn = new Button("설정 저장");

            add(mainBtn);
            add(saveBtn);
        }
    }
}
