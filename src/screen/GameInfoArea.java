package screen;

import score.ScoreManager;
import unit.block.Block;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

import static constant.Constants.*;

public class GameInfoArea extends JPanel {
    private Block nextBlock;

    public GameInfoArea() {
        super();
        add(new NextBlockArea());
        add(new ScoreArea());

        SwingUtilities.invokeLater(() -> {
            this.nextBlock = ((GameScreen) getParent()).getNextBlock();
        });
    }

    public class NextBlockArea extends JPanel {
        public NextBlockArea() {
            LineBorder border = (LineBorder) BorderFactory.createLineBorder(Color.DARK_GRAY, 5);
            setBorder(border);
            setPreferredSize(new Dimension(NEXT_BLOCK_AREA_WIDTH, NEXT_BLOCK_AREA_HEIGHT));
            setBackground(Color.GRAY);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Component gameInfoArea = ((GameInfoArea) getParent());
            nextBlock = ((GameScreen) gameInfoArea.getParent()).getNextBlock();
            Graphics2D g2d = (Graphics2D) g;

            if(nextBlock != null) {
                int[][] blockShape = nextBlock.getShape();
                int blockWidth = nextBlock.getWidth();
                int blockHeight = nextBlock.getHeight();
                g2d.setColor(nextBlock.getColor());

                int middleX = NEXT_BLOCK_AREA_WIDTH / 2 - (blockWidth - 1) * BLOCK_CELL_WIDTH;
                int middleY = NEXT_BLOCK_AREA_HEIGHT / 2 - (blockHeight - 1) * BLOCK_CELL_HEIGHT;
                for (int i = 0; i < blockHeight; ++i) {
                    for (int j = 0; j < blockWidth; ++j) {
                        if (blockShape[i][j] == 1)
                            g2d.fillRect(middleX + j * BLOCK_CELL_WIDTH, middleY + i * BLOCK_CELL_HEIGHT, BLOCK_CELL_HEIGHT, BLOCK_CELL_WIDTH);
                    }
                }
            }

        }
    }

    public class ScoreArea extends JPanel {
        private JLabel scoreLabel;
        private JLabel levelLable;
        public ScoreArea() {
            LineBorder border = (LineBorder) BorderFactory.createLineBorder(Color.DARK_GRAY, 5);
            setBorder(border);
            setPreferredSize(new Dimension(NEXT_BLOCK_AREA_WIDTH, NEXT_BLOCK_AREA_HEIGHT));
            setBackground(Color.GRAY);

            scoreLabel = new JLabel("0");
            levelLable = new JLabel();

            add(scoreLabel);
            add(levelLable);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            scoreLabel.setText(String.valueOf(ScoreManager.getScore()));
            levelLable.setText("LEVEL : " + String.valueOf(ScoreManager.getLevel()));
        }
    }
}
