package screen;

import component.AbstractArea;
import manager.GameManager;
import unit.block.Block;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

import static manager.GameSizeManager.GAME_SIZE;

public class GameInfoArea extends AbstractArea {
    private Block nextBlock;

    public GameInfoArea() {
        setPreferredSize(new Dimension(GAME_SIZE.getInfoAreaWidth(), GAME_SIZE.getHeight()));
        setLayout(new GridLayout(0, 1));
        add(new NextBlockArea());
        add(new ScoreArea());
        SwingUtilities.invokeLater(() -> this.nextBlock = ((GameScreen) getParent()).getNextBlock());
    }

    public class NextBlockArea extends AbstractArea {
        public NextBlockArea() {
            LineBorder border = (LineBorder) BorderFactory.createLineBorder(Color.DARK_GRAY, 5);
            setBorder(border);
            setBackground(Color.GRAY);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Component gameInfoArea = getParent();
            nextBlock = ((GameScreen) gameInfoArea.getParent()).getNextBlock();
            Graphics2D g2d = (Graphics2D) g;

            if (nextBlock != null) {
                int[][] blockShape = nextBlock.getShape();
                int blockWidth = nextBlock.getWidth();
                int blockHeight = nextBlock.getHeight();
                g2d.setColor(nextBlock.getColor());

                int middleX = getWidth() / 2 - (blockWidth - 2) * GAME_SIZE.getBlockCellSize();
                int middleY = GAME_SIZE.getInfoAreaWidth() / 2 - (blockHeight - 1) * GAME_SIZE.getBlockCellSize();
                for (int i = 0; i < blockHeight; ++i) {
                    for (int j = 0; j < blockWidth; ++j) {
                        if (blockShape[i][j] == 1)
                            g2d.fillRect(middleX + j * GAME_SIZE.getBlockCellSize(), middleY + i * GAME_SIZE.getBlockCellSize(), GAME_SIZE.getBlockCellSize(), GAME_SIZE.getBlockCellSize());
                    }
                }
            }

        }
    }

    public static class ScoreArea extends AbstractArea {
        private final JLabel scoreLabel;
        private final JLabel levelLabel;

        public ScoreArea() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            LineBorder border = (LineBorder) BorderFactory.createLineBorder(Color.DARK_GRAY, 5);
            setBorder(border);
            setBackground(Color.GRAY);

            levelLabel = new JLabel();
            scoreLabel = new JLabel("0");

            add(levelLabel);
            add(scoreLabel);

            levelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            levelLabel.setText("LEVEL : " + GameManager.getLevel());
            scoreLabel.setText(String.valueOf(GameManager.getScore()));
        }
    }
}
