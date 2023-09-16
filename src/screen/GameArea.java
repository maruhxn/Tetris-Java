package screen;

import screen.GameScreen;
import unit.block.Block;
import unit.block.OBlock;

import javax.swing.*;
import java.awt.*;

import static constant.Constants.*;

public class GameArea extends JPanel {
    private Block currBlock;

    public GameArea() {
        setPreferredSize(new Dimension(GAME_AREA_WIDTH, GAME_AREA_HEIGHT));
        setBackground(Color.BLACK);
        this.currBlock = new OBlock();

        SwingUtilities.invokeLater(() -> {
            this.currBlock = ((GameScreen) getParent()).getCurrBlock();
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        drawUI(g2d);
    }

    private void drawUI(Graphics2D g2d) {
        int[][] blockShape = currBlock.getShape();
        int blockWidth = currBlock.getWidth();
        int blockHeight = currBlock.getHeight();

        g2d.setColor(currBlock.getColor());
        for (int i = 0; i < blockHeight; ++i) {
            for (int j = 0; j < blockWidth; ++j) {
                if (blockShape[i][j] == 1)
                    g2d.fillRect(currBlock.getX() + j * BLOCK_CELL_WIDTH, currBlock.getY() + i * BLOCK_CELL_HEIGHT, BLOCK_CELL_HEIGHT, BLOCK_CELL_WIDTH);
            }
        }
    }
}
